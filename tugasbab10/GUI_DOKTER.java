/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tugasbab10;


import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList; // Meskipun tidak ada ArrayList global, ini bisa berguna jika Anda kembangkan
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author reignite
 */
public class GUI_DOKTER extends javax.swing.JFrame {

    // Variabel untuk menyimpan ID dokter yang dipilih di tabel untuk operasi Update/Delete
    private String idDokterDipilih;
    // Variabel ini bisa digunakan untuk menyimpan ID pegawai jika diperlukan dari tabel klik,
    // namun untuk insert, kita akan mengambilnya berdasarkan nama.
    private int idPegawaiTerkaitSaatKlik;

    public GUI_DOKTER() {
        initComponents();
        setLocationRelativeTo(null);
        loadComboBoxNama();     // Muat nama pegawai ke ComboBox
        tampil_data();          // Tampilkan data dokter yang sudah ada
        kosongkan_form();       // Siapkan form dalam keadaan bersih
    }
public Connection conn;

    public void koneksi() throws SQLException {
        try {
            conn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_puskesmas?serverTimezone=UTC", // SESUAIKAN NAMA DB
                    "root",
                    "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI_DOKTER.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            Logger.getLogger(GUI_DOKTER.class.getName()).log(Level.SEVERE, null, e);
             JOptionPane.showMessageDialog(this, "Koneksi ke database Gagal: " + e.getMessage(), "Error Koneksi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception es) {
            Logger.getLogger(GUI_DOKTER.class.getName()).log(Level.SEVERE, null, es);
        }
    }
    public void loadComboBoxNama() {
        cmbNama.removeAllItems();
        cmbNama.addItem("-- Pilih Nama Pegawai --");
        try {
            koneksi();
            // Mengambil pegawai berjenis 'Dokter' yang belum ada di tabel dokter
            String sql = "SELECT nama FROM pegawai WHERE jenis_pekerjaan = 'Dokter' AND id_pegawai NOT IN (SELECT id_pegawai FROM dokter)";
            Statement stt = conn.createStatement();
            ResultSet res = stt.executeQuery(sql);
            while (res.next()) {
                cmbNama.addItem(res.getString("nama"));
            }
            res.close();
            stt.close();
        } catch (Exception e) {
            System.out.println("Gagal memuat nama pegawai (dokter): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public void tampil_data() {
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("ID Dokter");
        tabelhead.addColumn("ID Pegawai"); // Untuk referensi saat klik tabel
        tabelhead.addColumn("Nama");
        tabelhead.addColumn("Umur");
        tabelhead.addColumn("Status");
        tabelhead.addColumn("Spesialis");
        try {
            koneksi();
            String sql = "SELECT d.id_dokter, d.id_pegawai, pg.nama, d.umur, d.status, d.spesialis "
                       + "FROM dokter d JOIN pegawai pg ON d.id_pegawai = pg.id_pegawai";
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                tabelhead.addRow(new Object[]{
                    res.getInt("id_dokter"),
                    res.getInt("id_pegawai"),
                    res.getString("nama"),
                    res.getString("umur"), // Bisa juga res.getInt("umur")
                    res.getString("status"),
                    res.getString("spesialis")
                });
            }
            jTable1.setModel(tabelhead); // Pastikan nama tabel Anda adalah jTable1
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "BELUM TERKONEKSI atau Gagal Tampil Data Dokter: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public void kosongkan_form() {
        cmbNama.setSelectedIndex(0);
        txtUmur.setText("");
        txtStatus.setText("");
        txtSpesialis.setText("");
        idDokterDipilih = null;
        idPegawaiTerkaitSaatKlik = 0;
        cmbNama.setEnabled(true);
        jTable1.clearSelection();
    }

    public void insert_data() {
        if (cmbNama.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Pilih Nama Pegawai terlebih dahulu!");
            return;
        }
        String namaPegawaiDipilih = cmbNama.getSelectedItem().toString();
        String umur = txtUmur.getText();
        String status = txtStatus.getText();
        String spesialis = txtSpesialis.getText();
        int idPegawaiUntukInsert = 0;

        try {
            koneksi();
            // Dapatkan id_pegawai berdasarkan nama yang dipilih
            // PENTING: Gunakan PreparedStatement di sini untuk keamanan meskipun sisanya tidak
            String sqlGetId = "SELECT id_pegawai FROM pegawai WHERE nama = ? AND jenis_pekerjaan = 'Dokter'";
            PreparedStatement pstGetId = conn.prepareStatement(sqlGetId);
            pstGetId.setString(1, namaPegawaiDipilih);
            ResultSet rsGetId = pstGetId.executeQuery();

            if (rsGetId.next()) {
                idPegawaiUntukInsert = rsGetId.getInt("id_pegawai");
            } else {
                JOptionPane.showMessageDialog(null, "ID Pegawai tidak ditemukan untuk nama: " + namaPegawaiDipilih);
                rsGetId.close();
                pstGetId.close();
                return;
            }
            rsGetId.close();
            pstGetId.close();

            if(idPegawaiUntukInsert == 0) {
                JOptionPane.showMessageDialog(null, "Gagal mendapatkan ID Pegawai. Operasi dibatalkan.");
                return;
            }

            // Lanjutkan INSERT ke tabel dokter
            Statement statement = conn.createStatement();
            // PERINGATAN: RENTAN SQL INJECTION!
            String sqlInsert = "INSERT INTO dokter (id_pegawai, umur, status, spesialis) VALUES("
                               + idPegawaiUntukInsert + ",'"
                               + umur + "','"
                               + status + "','"
                               + spesialis + "')";
            statement.executeUpdate(sqlInsert);
            statement.close();
            JOptionPane.showMessageDialog(null, "Berhasil Memasukan Data Dokter!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Input Data Dokter: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        loadComboBoxNama();
        tampil_data();
        kosongkan_form();
    }

    public void update_data() {
        if (idDokterDipilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih data dokter dari tabel yang akan diupdate!");
            return;
        }
        String umur = txtUmur.getText();
        String status = txtStatus.getText();
        String spesialis = txtSpesialis.getText();

        try {
            koneksi();
            Statement statement = conn.createStatement();
            // PERINGATAN: RENTAN SQL INJECTION!
            String sql = "UPDATE dokter SET "
                       + "umur = '" + umur + "', "
                       + "status = '" + status + "', "
                       + "spesialis = '" + spesialis + "' "
                       + "WHERE id_dokter = " + idDokterDipilih;
            statement.executeUpdate(sql);
            statement.close();
            JOptionPane.showMessageDialog(null, "Update Data Dokter Berhasil!");
        } catch (Exception e) {
            System.out.println("Error update dokter : " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        tampil_data();
        kosongkan_form();
    }

    public void delete_data() {
        if (idDokterDipilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih data dokter dari tabel yang akan dihapus!");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin akan menghapus data dokter ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            try {
                koneksi();
                String sql = "DELETE FROM dokter WHERE id_dokter = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, idDokterDipilih);
                stmt.executeUpdate();
                stmt.close();
                JOptionPane.showMessageDialog(null, "Data Dokter Berhasil dihapus");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data Dokter gagal dihapus: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        loadComboBoxNama();
        tampil_data();
        kosongkan_form();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnClose = new javax.swing.JButton();
        txtUmur = new javax.swing.JTextField();
        txtSpesialis = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        cmbNama = new javax.swing.JComboBox<>();
        txtUpdate = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnBatal = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnClose.setText("close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        txtSpesialis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSpesialisActionPerformed(evt);
            }
        });

        cmbNama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nama" }));
        cmbNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNamaActionPerformed(evt);
            }
        });

        txtUpdate.setText("Update");
        txtUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUpdateActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama", "Umur", "Status", "Spesialis"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("DOKTER PUSKESMAS");

        jLabel2.setText("Nama");

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jLabel3.setText("Umur");

        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setText("Status");

        btnBatal.setText("batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel5.setText("Spesialis");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel1)
                .addContainerGap(639, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(txtSpesialis, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(61, 61, 61)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUmur, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                            .addComponent(cmbNama, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnSimpan)
                        .addGap(30, 30, 30)
                        .addComponent(jButton3)
                        .addGap(37, 37, 37)
                        .addComponent(btnBatal)
                        .addGap(45, 45, 45)
                        .addComponent(btnClose)
                        .addGap(18, 18, 18)
                        .addComponent(txtUpdate)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cmbNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtUmur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtSpesialis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSimpan)
                            .addComponent(jButton3)
                            .addComponent(btnBatal)
                            .addComponent(btnClose)
                            .addComponent(txtUpdate))))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSpesialisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSpesialisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSpesialisActionPerformed

    private void cmbNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbNamaActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
           insert_data();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUpdateActionPerformed
        // TODO add your handling code here:
          update_data();
    }//GEN-LAST:event_txtUpdateActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
           delete_data();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        kosongkan_form();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
         this.dispose(); 
    }//GEN-LAST:event_btnCloseActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int baris = jTable1.getSelectedRow();
    if (baris == -1) { // Jika tidak ada baris yang terpilih (misal: klik header)
        return;
    }

    // Kolom pertama (indeks 0) di tabel adalah ID Dokter (sesuai method tampil_data)
    idDokterDipilih = jTable1.getValueAt(baris, 0).toString();
    
    // Kolom kedua (indeks 1) adalah ID Pegawai, bisa disimpan jika perlu di masa depan
    // idPegawaiTerkaitSaatKlik = Integer.parseInt(jTable1.getValueAt(baris, 1).toString());

    // Kolom ketiga (indeks 2) adalah Nama Pegawai
    cmbNama.setSelectedItem(jTable1.getValueAt(baris, 2).toString());
    cmbNama.setEnabled(false); // Nonaktifkan ComboBox saat mode update/delete

    // Kolom keempat (indeks 3) adalah Umur
    txtUmur.setText(String.valueOf(jTable1.getValueAt(baris, 3))); // Gunakan String.valueOf untuk amannya
    
    // Kolom kelima (indeks 4) adalah Status
    txtStatus.setText(jTable1.getValueAt(baris, 4).toString());
    
    // Kolom keenam (indeks 5) adalah Spesialis
    txtSpesialis.setText(jTable1.getValueAt(baris, 5).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_DOKTER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_DOKTER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_DOKTER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_DOKTER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_DOKTER().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cmbNama;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtSpesialis;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtUmur;
    private javax.swing.JButton txtUpdate;
    // End of variables declaration//GEN-END:variables
}
