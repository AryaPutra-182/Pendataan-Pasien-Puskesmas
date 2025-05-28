/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab9;

/**
 *
 * @author reignite
 */
public class DCPasien {
    public static void main(String[] args) {
        // Membuat objek pasien pertama
        System.out.println("--- Data Pasien 1 ---");
        pasien pasien1 = new pasien("Budi Santoso", 45, "Jakarta", 500000);
        pasien1.tampilkanData(); // Menampilkan data dasar pasien
        pasien1.status_pasien(); // Menampilkan status pasien
        pasien1.tampilkan_riwayat_penyakit(); // Menampilkan riwayat penyakit

        // Menggunakan method overloading untuk input_diagnosa
        System.out.println("\n--- Diagnosa Pasien 1 ---");
        pasien1.input_diagnosa(); // Memanggil method input_diagnosa tanpa parameter
        pasien1.input_diagnosa("Influenza Musiman"); // Memanggil overloading dengan satu parameter
        pasien1.input_diagnosa("Demam Berdarah", "Dehidrasi Ringan"); // Memanggil overloading dengan dua parameter

        // Menggunakan method overloading untuk hitung_tagihan
        System.out.println("\n--- Tagihan Pasien 1 ---");
        double tagihanAsli = pasien1.hitung_tagihan(600000); // Hitung tagihan dengan parameter baru
        System.out.println("Tagihan asli (dengan parameter): Rp " + tagihanAsli);

        double tagihanDiskon = pasien1.hitung_tagihan(700000, 50000); // Hitung tagihan dengan diskon
        System.out.println("Tagihan dengan diskon: Rp " + tagihanDiskon);

        double tagihanDariAtribut = pasien1.hitung_tagihan(); // Hitung tagihan menggunakan atribut 'biaya' dari objek
        System.out.println("Tagihan dari atribut biaya pasien: Rp " + tagihanDariAtribut);


        // Membuat objek pasien kedua
        System.out.println("\n--- Data Pasien 2 ---");
        pasien pasien2 = new pasien("Siti Aminah", 28, "Surabaya", 750000);
        pasien2.tampilkanData();
        pasien2.status_pasien();
        pasien2.tampilkan_riwayat_penyakit();

        System.out.println("\n--- Diagnosa Pasien 2 ---");
        pasien2.input_diagnosa("Asma Kronis");

        System.out.println("\n--- Tagihan Pasien 2 ---");
        double tagihanPasien2 = pasien2.hitung_tagihan(900000, 75000);
        System.out.println("Total tagihan pasien 2: Rp " + tagihanPasien2);
    }
}
