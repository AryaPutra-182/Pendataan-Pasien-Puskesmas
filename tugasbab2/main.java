/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab2;

/**
 *
 * @author reignite
 */
public class main {
    // Metode utama yang dieksekusi pertama kali saat program dijalankan
    public static void main(String[] args) {  
        // Membuat objek pasien dari kelas Pasien
        pasien pasien1 = new pasien("Sugiantoro" , "Malang", 55, 1000000); // membuat data pasien baru
        pasien1.tampilkanData();
        
        // Menampilkan status pasien
        System.out.print("Status = ");  
        pasien1.status_pasien();  // Memanggil metode untuk mencetak status pasien

        // Menampilkan riwayat penyakit pasien
        System.out.print("Riwayat = ");  
        pasien1.tampilkan_riwayat_penyakit();  // Memanggil metode untuk mencetak riwayat penyakit pasien
        
        // Menampilkan diagnosa pasien
        System.out.print("Diagnosa = ");  
        pasien1.input_diagnosa();  // Memanggil metode untuk mencetak diagnosa pasien
        
    }

}
