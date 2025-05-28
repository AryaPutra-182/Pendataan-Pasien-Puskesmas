/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package tugasbab10; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class KoneksiDatabase {

    private static Connection koneksi; // Variabel koneksi statis

    // Method statis untuk menyediakan koneksi
    public static Connection getKoneksi() {
        // Cek apakah koneksi sudah pernah dibuat
        if (koneksi == null) {
            try {
                String driver = "com.mysql.cj.jdbc.Driver";
                String url = "jdbc:mysql://localhost:3306/db_puskesmas"; // Ganti jika perlu
                String user = "root";
                String password = "";
                
                Class.forName(driver); // Memuat driver
                koneksi = DriverManager.getConnection(url, user, password); // Membuat koneksi
                
                System.out.println("Koneksi ke database berhasil dibuat.");

            } catch (ClassNotFoundException | SQLException e) {
                // Tampilkan pesan error jika gagal
                JOptionPane.showMessageDialog(null, "Gagal terhubung ke database: " + e.getMessage());
                System.exit(0); // Hentikan program jika koneksi gagal total
            }
        }
        return koneksi; // Kembalikan koneksi yang sudah dibuat
    }
}
