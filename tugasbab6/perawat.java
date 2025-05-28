/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab6;



/**
 *
 * @author reignite
 */
public class perawat extends pegawai {
    private String pengalamanKerja;

    public perawat(String nama, int umur, String status, String pengalamanKerja) {
        super(nama, umur, status);
        this.pengalamanKerja = pengalamanKerja;
    }

    public perawat(String nama, int umur, String status) {
        super(nama, umur, status);
        this.pengalamanKerja = "Belum ada pengalaman";
    }

    public perawat(String nama, String pengalamanKerja) {
        super(nama, 0, "Magang");
        this.pengalamanKerja = pengalamanKerja;
    }

    public String getPengalaman() {
        return pengalamanKerja;
    }

    public void setPengalaman(String pengalamanKerja) {
        this.pengalamanKerja = pengalamanKerja;
    }

    public void setPengalaman(String pengalamanKerja, int tahun) {
        this.pengalamanKerja = pengalamanKerja + " (" + tahun + " tahun)";
    }

    // OVERRIDING getStatus()
    @Override
    public String getStatus() {
        return "Perawat - " + super.getStatus();
    }

    @Override // overide tampilkanInfo dari class abstract 
    public void tampilkanInfo() {
       System.out.println("=== Data Perawat ===");
        System.out.println("Nama: " + nama);
        System.out.println("Umur: " + umur);
        System.out.println("Status: " + status);
        
    }
}


