/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab9;



/**
 *
 * @author reignite
 */


public class pasien implements IPASIEN {

    public pasien(String siti_Aminah, int par, String surabaya, int par1) {
    }
    String nama;
    String asal;
    int umur;
    double biaya;

    // ... (rest of your existing constructor and method implementations) ...

    @Override
    public void status_pasien() {
        System.out.println("Rawat Jalan");
    }

    @Override
    public void tampilkan_riwayat_penyakit() {
        System.out.println("Hipertensi, Diabetes");
    }

    @Override
    public void input_diagnosa() {
        System.out.println("Pasien mengalami tekanan darah tinggi.");
    }

    @Override
    public void input_diagnosa(String kondisi) {
        System.out.println("Diagnosa tambahan: " + kondisi);
    }

    @Override
    public void input_diagnosa(String kondisi1, String kondisi2) {
        System.out.println("Diagnosa: " + kondisi1 + " dan " + kondisi2);
    }

    @Override
    public double hitung_tagihan(double biaya) {
        return biaya * 1.1;
    }

    @Override
    public double hitung_tagihan(double biaya, double diskon) {
        double total = biaya - diskon;
        return total * 1.1;
    }

    @Override
    public double hitung_tagihan() {
        return this.biaya * 1.1;
    }

    @Override
    public void tampilkanData() {
        System.out.println("Nama: " + this.nama);
        System.out.println("Asal: " + this.asal);
        System.out.println("Umur: " + this.umur);
        System.out.println("Biaya: " + this.hitung_tagihan());
    }
}

