/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab6;

/**
 *
 * @author reignite
 */
public class main {
    public static void main(String[] args) {
        // Membuat array pegawai yang berisi dokter & perawat
        pegawai[] daftarPegawai = new pegawai[2];

        // Mengisi array dengan objek Dokter & Perawat
        daftarPegawai[0] = new dokter("Dr. Andi", 45, "Aktif", "Bedah");
        daftarPegawai[1] = new perawat("Suster Rina", 30, "Aktif", "Malam");

        // Polimorfisme: memanggil method tampilkanInfo() secara dinamis
        for (pegawai p : daftarPegawai) {
            p.tampilkanInfo();  // Ini akan otomatis memanggil method yang sesuai (dokter/perawat)
            System.out.println();
        }
    }
}

