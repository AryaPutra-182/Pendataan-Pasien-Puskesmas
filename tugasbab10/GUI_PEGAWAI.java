package tugasbab10;









/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author reignite
 */
// Variabel untuk menyimpan ID pegawai yang dipilih di tabel
  



public class GUI_PEGAWAI extends javax.swing.JFrame {

    // Variabel koneksi dan untuk menyimpan ID yang dipilih
    public Connection conn;
    private String idPegawaiDipilih;

    /**
     * Constructor: Dijalankan saat form pertama kali dibuat
     */
    public GUI_PEGAWAI() {
        initComponents();
        tampil_data();
        kosongkan_form();
        setLocationRelativeTo(null);
    }

    // METHOD-METHOD LOGIKA

    /**
     * Method untuk membuat koneksi ke database
     */
    public void koneksi() {
        try {
            // Selalu set conn ke null sebelum membuat koneksi baru
            conn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_puskesmas?serverTimezone=UTC", // SESUAIKAN NAMA DATABASE ANDA
                    "root",
                    "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Koneksi Gagal: " + e.getMessage());
            // Keluar dari program jika koneksi gagal total, karena tidak ada yang bisa dilakukan
            System.exit(0);
        }
    }

    /**
     * Method untuk menampilkan data dari database ke tabel
     */
    public void tampil_data() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Pendidikan");
        model.addColumn("Keterangan");
        model.addColumn("Jenis");

        try {
            koneksi(); // Panggil koneksi
            String sql = "SELECT * FROM pegawai"; // SESUAIKAN NAMA TABEL ANDA
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                model.addRow(new Object[]{
                    res.getInt(1), res.getString(2), res.getString(3),
                    res.getString(4), res.getString(5)
                });
            }
            tablePegawai.setModel(model); // Pastikan nama JTable Anda adalah jTable1
        } catch (Exception e) {
            System.err.println("Gagal menampilkan data: " + e.getMessage());
        }
    }
    
    /**
     * Method untuk membersihkan form input
     */
    public void kosongkan_form() {
        txt_nama.setText("");
        txt_pendidikan.setText("");
        txt_keterangan.setText("");
        cmb_pilih.setSelectedIndex(0);
        idPegawaiDipilih = null;
        tablePegawai.clearSelection();
    }

    /**
     * Method untuk memasukkan data baru (INSERT)
     */
    public void insert_data() {
        try {
            koneksi();
            // PENTING: Saya tetap menggunakan PreparedStatement untuk keamanan dari SQL Injection
            String sql = "INSERT INTO pegawai (nama, pendidikan, keterangan, jenis_pekerjaan) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setString(1, txt_nama.getText());
            pst.setString(2, txt_pendidikan.getText());
            pst.setString(3, txt_keterangan.getText());
            pst.setString(4, cmb_pilih.getSelectedItem().toString());
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Input: " + e.getMessage());
        }
        tampil_data();
        kosongkan_form();
    }

    /**
     * Method untuk memperbarui data (UPDATE)
     */
    public void update_data() {
        if (idPegawaiDipilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu!");
            return;
        }
        try {
            koneksi();
            String sql = "UPDATE pegawai SET nama=?, pendidikan=?, keterangan=?, jenis_pekerjaan=? WHERE id_pegawai=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setString(1, txt_nama.getText());
            pst.setString(2, txt_pendidikan.getText());
            pst.setString(3, txt_keterangan.getText());
            pst.setString(4, cmb_pilih.getSelectedItem().toString());
            pst.setString(5, idPegawaiDipilih);
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Update Data Berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Update Data: " + e.getMessage());
        }
        tampil_data();
        kosongkan_form();
    }

    /**
     * Method untuk menghapus data (DELETE)
     */
    public void delete_data() {
        if (idPegawaiDipilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu!");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            try {
                koneksi();
                String sql = "DELETE FROM pegawai WHERE id_pegawai=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                
                pst.setString(1, idPegawaiDipilih);
                
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal Menghapus Data: " + e.getMessage());
            }
        }
        tampil_data();
        kosongkan_form();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        btnClose = new javax.swing.JButton();
        txt_nama = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmb_pilih = new javax.swing.JComboBox<>();
        txt_pendidikan = new javax.swing.JTextField();
        txtUpdate = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txt_keterangan = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePegawai = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnBatal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnClose.setText("close");

        jLabel5.setText("Jenis ");

        jLabel3.setText("Pendidikan");

        cmb_pilih.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dokter", "Perawat" }));

        txtUpdate.setText("Update");
        txtUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUpdateActionPerformed(evt);
            }
        });

        jLabel4.setText("Keterangan");

        tablePegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama", "Pendidikan", "Keterangan", "Jenis"
            }
        ));
        tablePegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePegawaiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablePegawai);

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("PEGAWAI PUSKESMAS");

        btnBatal.setText("batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel2.setText("Nama");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_keterangan, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(txt_pendidikan, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(txt_nama, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(cmb_pilih, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(62, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(280, 280, 280))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_pendidikan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cmb_pilih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(jButton3)
                    .addComponent(btnBatal)
                    .addComponent(btnClose)
                    .addComponent(txtUpdate))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void tablePegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePegawaiMouseClicked
        // TODO add your handling code here:
         // 1. Dapatkan nomor baris yang diklik oleh pengguna
    int baris = tablePegawai.getSelectedRow();

    // 2. Jaring pengaman: jika tidak ada baris yang dipilih (misal: klik header), hentikan method
    if (baris == -1) {
        return;
    }

    // 3. Ambil ID dari kolom pertama (indeks 0) dan simpan ke variabel class
    // Ini adalah langkah paling penting untuk fungsi Update dan Delete
    idPegawaiDipilih = tablePegawai.getValueAt(baris, 0).toString();

    // 4. Ambil data dari setiap kolom di baris yang dipilih dan tampilkan di form
    // Pastikan urutan indeks (0, 1, 2, 3, 4) sesuai dengan urutan kolom di method tampil_data() Anda
    
    // Kolom indeks 1 adalah Nama
    txt_nama.setText(tablePegawai.getValueAt(baris, 1).toString());
    
    // Kolom indeks 2 adalah Pendidikan
    txt_pendidikan.setText(tablePegawai.getValueAt(baris, 2).toString());
    
    // Kolom indeks 3 adalah Keterangan
    txt_keterangan.setText(tablePegawai.getValueAt(baris, 3).toString());
    
    // Kolom indeks 4 adalah Jenis Pekerjaan
    cmb_pilih.setSelectedItem(tablePegawai.getValueAt(baris, 4).toString());
    }//GEN-LAST:event_tablePegawaiMouseClicked

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
            java.util.logging.Logger.getLogger(GUI_PEGAWAI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_PEGAWAI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_PEGAWAI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_PEGAWAI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_PEGAWAI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cmb_pilih;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tablePegawai;
    private javax.swing.JButton txtUpdate;
    private javax.swing.JTextField txt_keterangan;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_pendidikan;
    // End of variables declaration//GEN-END:variables
}
