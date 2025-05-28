/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab7;


/**
 *
 * @author reignite
 */
public class main {
    public static void main(String[] args) {
        // Array bertipe pegawai tapi berisi objek dokter dan perawat
        pegawai[] daftarPegawai = new pegawai[3];

        daftarPegawai[0] = new dokter("Dr. Rina", 40, "Tetap", "Anak");
        daftarPegawai[1] = new perawat("Budi", 30, "Tetap", "3 tahun");
        daftarPegawai[2] = new dokter("Dr. Lita", "Gigi"); // dokter kontrak

        // Polimorfisme: method tampilkanInfo() dipanggil dari objek pegawai
        for (pegawai p : daftarPegawai) {
            p.tampilkanInfo(); // Memanggil versi sesuai objek aslinya (dokter/perawat)
            System.out.println();
        }
    }
}




