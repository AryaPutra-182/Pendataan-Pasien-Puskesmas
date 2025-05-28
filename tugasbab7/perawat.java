/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab7;



/**
 *
 * @author reignite
 */
public class perawat extends pegawai {
    private String pengalamanKerja;

    // Constructor lengkap
    public perawat(String nama, int umur, String status, String pengalamanKerja) {
        super(nama, umur, status);
        this.pengalamanKerja = pengalamanKerja;
    }

    // Constructor tanpa pengalaman kerja (default "Belum ada pengalaman")
    public perawat(String nama, int umur, String status) {
        super(nama, umur, status);
        this.pengalamanKerja = "Belum ada pengalaman";
    }

    // Constructor untuk perawat magang
    public perawat(String nama, String pengalamanKerja) {
        super(nama, 0, "Magang");
        this.pengalamanKerja = pengalamanKerja;
    }

    // Getter untuk pengalaman kerja
    public String getPengalaman() {
        return pengalamanKerja;
    }

    // Setter biasa
    public void setPengalaman(String pengalamanKerja) {
        this.pengalamanKerja = pengalamanKerja;
    }

    // Setter overload dengan tahun
    public void setPengalaman(String pengalamanKerja, int tahun) {
        this.pengalamanKerja = pengalamanKerja + " (" + tahun + " tahun)";
    }

    // OVERRIDING getStatus()
    @Override
    public String getStatus() {
        return "Perawat - " + super.getStatus();
    }

    // OVERRIDING tampilkanInfo dari class abstract
    @Override
    public void tampilkanInfo() {
        System.out.println("=== Data Perawat ===");
        System.out.println("Nama: " + nama);
        System.out.println("Umur: " + umur);
        System.out.println("Status: " + status);
        System.out.println("Pengalaman Kerja: " + pengalamanKerja);
    }
}




