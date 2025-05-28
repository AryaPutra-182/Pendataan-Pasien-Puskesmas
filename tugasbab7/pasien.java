/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab7;



/**
 *
 * @author reignite
 */


public class pasien {
    String nama;
    String asal;
    int umur;
    double biaya;

    public pasien(String namaPasien, int umurPasien, String kotaAsal, double BiayaPasien) {
        this.nama = namaPasien;
        this.asal = kotaAsal;
        this.umur = umurPasien;
        this.biaya = BiayaPasien;
    }

    public void status_pasien() {
        System.out.println("Rawat Jalan");
    }

    public void tampilkan_riwayat_penyakit() {
        System.out.println("Hipertensi, Diabetes");
    }

    // Method asli
    public void input_diagnosa() {
        System.out.println("Pasien mengalami tekanan darah tinggi.");
    }

    // Overloading: menerima string tambahan (misalnya kondisi spesifik)
    public void input_diagnosa(String kondisi) {
        System.out.println("Diagnosa tambahan: " + kondisi);
    }

    // Overloading: menerima dua kondisi
    public void input_diagnosa(String kondisi1, String kondisi2) {
        System.out.println("Diagnosa: " + kondisi1 + " dan " + kondisi2);
    }

    // Method asli
    public double hitung_tagihan(double biaya) {
        return biaya * 1.1;
    }

    // Overloading: hitung tagihan dengan diskon
    public double hitung_tagihan(double biaya, double diskon) {
        double total = biaya - diskon;
        return total * 1.1;
    }

    // Overloading: hitung tagihan tanpa parameter, pakai atribut biaya
    public double hitung_tagihan() {
        return this.biaya * 1.1;
    }

    public void tampilkanData() {
        System.out.println("Nama: " + this.nama);
        System.out.println("Asal: " + this.asal);
        System.out.println("Umur: " + this.umur);
        System.out.println("Biaya: " + this.hitung_tagihan());
    }
}

