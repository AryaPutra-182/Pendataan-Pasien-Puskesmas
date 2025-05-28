/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tugasbab10;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author reignite
 */
public class GUI_PERAWAT extends javax.swing.JFrame {

   private String idPerawatDipilih;
    // Variabel untuk menyimpan ID pegawai yang terkait dengan nama yang dipilih dari ComboBox (untuk INSERT)
    // atau dari tabel (untuk UPDATE/DELETE)
    private int idPegawaiTerkait;


    public GUI_PERAWAT() {
        initComponents();
        setLocationRelativeTo(null);
        loadComboBoxNama();     // Muat nama pegawai ke ComboBox
        tampil_data();          // Tampilkan data perawat yang sudah ada
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
            Logger.getLogger(GUI_PERAWAT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            Logger.getLogger(GUI_PERAWAT.class.getName()).log(Level.SEVERE, null, e);
             JOptionPane.showMessageDialog(this, "Koneksi ke database Gagal: " + e.getMessage(), "Error Koneksi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception es) {
            Logger.getLogger(GUI_PERAWAT.class.getName()).log(Level.SEVERE, null, es);
        }
    }
public void loadComboBoxNama() {
        cmbNama.removeAllItems();
        cmbNama.addItem("-- Pilih Nama Pegawai --");
        try {
            koneksi();
            // Mengambil pegawai berjenis 'Perawat' yang belum ada di tabel perawat
            String sql = "SELECT id_pegawai, nama FROM pegawai WHERE jenis_pekerjaan = 'Perawat' AND id_pegawai NOT IN (SELECT id_pegawai FROM perawat)";
            Statement stt = conn.createStatement();
            ResultSet res = stt.executeQuery(sql);
            while (res.next()) {
                // Menyimpan NAMA sebagai String (seperti contoh GUI_Nilai)
                // Untuk mendapatkan ID saat simpan, kita perlu cara lain jika tidak pakai PegawaiItem
                cmbNama.addItem(res.getString("nama"));
            }
            res.close();
            stt.close();
        } catch (Exception e) {
            System.out.println("Gagal memuat nama pegawai: " + e.getMessage());
        }
    }

public void tampil_data() {
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("ID Perawat");
        tabelhead.addColumn("ID Pegawai"); // Untuk referensi
        tabelhead.addColumn("Nama");
        tabelhead.addColumn("Umur");
        tabelhead.addColumn("Status");
        tabelhead.addColumn("Area Kerja");
        try {
            koneksi();
            String sql = "SELECT pr.id_perawat, pr.id_pegawai, pg.nama, pr.umur, pr.status, pr.area_tugas "
                       + "FROM perawat pr JOIN pegawai pg ON pr.id_pegawai = pg.id_pegawai";
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                tabelhead.addRow(new Object[]{
                    res.getInt("id_perawat"),
                    res.getInt("id_pegawai"),
                    res.getString("nama"),
                    res.getString("umur"), // Bisa juga res.getInt("umur") jika tipe di DB adalah INT
                    res.getString("status"),
                    res.getString("area_tugas")
                });
            }
            jTable1.setModel(tabelhead);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "BELUM TERKONEKSI atau Gagal Tampil Data Perawat: " + e.getMessage());
            e.printStackTrace();
        }
    }

 
 public void kosongkan_form() { // Mirip dengan batal()
        cmbNama.setSelectedIndex(0);
        txtUmur.setText("");
        txtStatus.setText("");
        txtAreaKerja.setText("");
        idPerawatDipilih = null;
        idPegawaiTerkait = 0;
        cmbNama.setEnabled(true); // Aktifkan kembali ComboBox
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
        String areaKerja = txtAreaKerja.getText();
        int idPegawaiUntukInsert = 0;

        try {
            koneksi();
            // Dapatkan id_pegawai berdasarkan nama yang dipilih
            String sqlGetId = "SELECT id_pegawai FROM pegawai WHERE nama = '" + namaPegawaiDipilih + "' AND jenis_pekerjaan = 'Perawat'";
            Statement stGetId = conn.createStatement();
            ResultSet rsGetId = stGetId.executeQuery(sqlGetId);
            if (rsGetId.next()) {
                idPegawaiUntukInsert = rsGetId.getInt("id_pegawai");
            } else {
                JOptionPane.showMessageDialog(null, "ID Pegawai tidak ditemukan untuk nama: " + namaPegawaiDipilih);
                return;
            }
            rsGetId.close();
            stGetId.close();

            if(idPegawaiUntukInsert == 0) {
                JOptionPane.showMessageDialog(null, "Gagal mendapatkan ID Pegawai. Operasi dibatalkan.");
                return;
            }

            // Lanjutkan INSERT ke tabel perawat
            Statement statement = conn.createStatement();
            // PERINGATAN: RENTAN SQL INJECTION!
            String sqlInsert = "INSERT INTO perawat (id_pegawai, umur, status, area_tugas) VALUES("
                               + idPegawaiUntukInsert + ",'"
                               + umur + "','"
                               + status + "','"
                               + areaKerja + "')";
            statement.executeUpdate(sqlInsert);
            statement.close();
            JOptionPane.showMessageDialog(null, "Berhasil Memasukan Data Perawat!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Input Data Perawat: " + e.getMessage());
            e.printStackTrace();
        }
        loadComboBoxNama(); // Muat ulang combobox
        tampil_data();
        kosongkan_form();
    }

public void update_data() {
        if (idPerawatDipilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih data perawat dari tabel yang akan diupdate!");
            return;
        }
        // Untuk GUI Perawat, NAMA tidak diupdate dari sini karena NAMA milik tabel PEGAWAI
        // Kita hanya update data spesifik PERAWAT (umur, status, area_kerja)
        String umur = txtUmur.getText();
        String status = txtStatus.getText();
        String areaKerja = txtAreaKerja.getText();

        try {
            koneksi();
            Statement statement = conn.createStatement();
            // PERINGATAN: RENTAN SQL INJECTION!
            String sql = "UPDATE perawat SET "
                       + "umur = '" + umur + "', "
                       + "status = '" + status + "', "
                       + "area_tugas = '" + areaKerja + "' "
                       + "WHERE id_perawat = " + idPerawatDipilih;
            statement.executeUpdate(sql);
            statement.close();
            conn.close(); // Hati-hati menutup koneksi di sini jika method lain juga butuh 'conn'
            JOptionPane.showMessageDialog(null, "Update Data Perawat Berhasil!");
        } catch (Exception e) {
            System.out.println("Error update perawat : " + e.getMessage());
            e.printStackTrace();
        }
        tampil_data();
        kosongkan_form();
    }

  public void delete_data() {
        if (idPerawatDipilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih data perawat dari tabel yang akan dihapus!");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin akan menghapus data perawat ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            try {
                koneksi();
                // Menggunakan PreparedStatement seperti contoh GUI_Nilai untuk delete
                String sql = "DELETE FROM perawat WHERE id_perawat = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, idPerawatDipilih);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Perawat Berhasil dihapus");
                kosongkan_form(); // Panggil setelah berhasil
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data Perawat gagal dihapus: " + e.getMessage());
                e.printStackTrace();
            }
        }
        loadComboBoxNama(); // Muat ulang combobox
        tampil_data(); // Refresh tabel
        // kosongkan_form() dipanggil di dalam try jika berhasil
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUmur = new javax.swing.JTextField();
        cmbNama = new javax.swing.JComboBox<>();
        btnSimpan = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnBatal = new javax.swing.JButton();
        txtAreaKerja = new javax.swing.JTextField();
        btnClose = new javax.swing.JButton();
        txtStatus = new javax.swing.JTextField();
        txtUpdate = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Nama");

        jLabel3.setText("Umur");

        jLabel4.setText("Status");

        jLabel5.setText("Area Kerja");

        cmbNama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "nama" }));

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("PERAWAT PUSKESMAS");

        btnBatal.setText("batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        txtAreaKerja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAreaKerjaActionPerformed(evt);
            }
        });

        btnClose.setText("close");

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
                "Nama", "Umur", "Status", "Area Kerja"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAreaKerja, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(61, 61, 61)
                                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtUmur, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                        .addComponent(cmbNama, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addComponent(jLabel1))
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
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(cmbNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUmur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtAreaKerja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(255, 255, 255))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(jButton3)
                    .addComponent(btnBatal)
                    .addComponent(btnClose)
                    .addComponent(txtUpdate))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAreaKerjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAreaKerjaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAreaKerjaActionPerformed

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

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int baris = jTable1.getSelectedRow();
    if (baris == -1) { // Jika tidak ada baris yang terpilih (misal: klik header)
        return;
    }

    // Kolom pertama (indeks 0) di tabel adalah ID Perawat
    idPerawatDipilih = jTable1.getValueAt(baris, 0).toString();
    
    // Kolom kedua (indeks 1) adalah ID Pegawai, simpan untuk referensi
    idPegawaiTerkait = Integer.parseInt(jTable1.getValueAt(baris, 1).toString());

    // Kolom ketiga (indeks 2) adalah Nama Pegawai
    // Set ComboBox agar menampilkan nama yang sesuai dan nonaktifkan
    cmbNama.setSelectedItem(jTable1.getValueAt(baris, 2).toString());
    cmbNama.setEnabled(false); 

    // Kolom keempat (indeks 3) adalah Umur
    txtUmur.setText(String.valueOf(jTable1.getValueAt(baris, 3)));
    
    // Kolom kelima (indeks 4) adalah Status
    txtStatus.setText(jTable1.getValueAt(baris, 4).toString());
    
    // Kolom keenam (indeks 5) adalah Area Kerja
    txtAreaKerja.setText(jTable1.getValueAt(baris, 5).toString());
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
            java.util.logging.Logger.getLogger(GUI_PERAWAT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_PERAWAT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_PERAWAT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_PERAWAT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_PERAWAT().setVisible(true);
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
    private javax.swing.JTextField txtAreaKerja;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtUmur;
    private javax.swing.JButton txtUpdate;
    // End of variables declaration//GEN-END:variables
}
