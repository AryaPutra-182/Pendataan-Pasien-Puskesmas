/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab2;

/**
 *
 * @author reignite
 */
public class pasien {
    // Atribut untuk menyimpan data pasien
    String nama;  // Nama pasien
    String asal;  // Kota asal pasien
    int umur;     // Umur pasien
    double biaya; // Biaya perawatan
    
    //contructor untuk menginisialisasi object
    public pasien(String namaPasien, String kotaAsal, int umurPasien, double BiayaPasien) {
        this.nama = namaPasien;  // Mendefinisakan nama = namaPasien
        this.asal = kotaAsal;    // Mendefinisikan asal = kotaAsal
        this.umur = umurPasien;  // Mendefinisikan umur = umurPasien
        this.biaya = BiayaPasien; // Mendefinisikan biaya = BiayaPasien
    }

    // Metode untuk menampilkan status pasien
    public void status_pasien() {
        System.out.println("Rawat Jalan"); // Mencetak status pasien
    }

    // Metode untuk menampilkan riwayat penyakit pasien
    public void tampilkan_riwayat_penyakit() {
        System.out.println("Hipertensi, Diabetes"); // Mencetak daftar riwayat penyakit
    }

    // Metode untuk memasukkan diagnosa pasien
    public void input_diagnosa() {
        System.out.println("Pasien mengalami tekanan darah tinggi."); // Mencetak hasil diagnosa
    }

    // Metode untuk menghitung tagihan dengan menambahkan pajak 10%
    public double hitung_tagihan(double biaya) {
        return biaya * 1.1; // Mengembalikan total biaya setelah ditambah pajak 10%
    }
     public void tampilkanData() { // function untuk menampilkan data data pasien
        System.out.println("Nama: " + this.nama); // untuk menampilkan nama
        System.out.println("Asal: " + this.asal); // untuk menampilkan asal
        System.out.println("Umur: " + this.umur);// untuk menampilkan umur
        System.out.println("Biaya: " + this.hitung_tagihan(biaya));// untuk menampilkan biaya dengan memanggil fuction hitung_tagihan
    }
}
