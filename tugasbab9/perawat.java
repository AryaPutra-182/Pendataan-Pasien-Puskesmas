/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab9;



/**
 *
 * @author reignite
 */
public class perawat extends pegawai {
    private String pengalamanKerja;
    private double insentifPengalaman; // Tambahkan atribut untuk perhitungan gaji

    // Constructor lengkap: nama, NIP, umur, status, pengalamanKerja, insentifPengalaman
    public perawat(String nama, int umur, String NIP, String status) {
        super(nama, NIP, umur, status); // Memanggil konstruktor kelas Pegawai
        this.pengalamanKerja = pengalamanKerja;
        this.insentifPengalaman = insentifPengalaman;
    }

    // Constructor tanpa pengalaman kerja (default "Belum ada pengalaman" dan NIP, insentif default)
    public perawat(String nama, String NIP, int umur, String status) {
        super(nama, NIP, umur, status);
        this.pengalamanKerja = "Belum ada pengalaman";
        this.insentifPengalaman = 500000; // Contoh insentif default
    }

    // Constructor untuk perawat magang (NIP, umur, status default)
    public perawat(String nama, String pengalamanKerja, double insentifPengalaman) {
        super(nama, "NIP_Magang_" + nama.replaceAll(" ", "_"), 0, "Magang"); // NIP dibuat otomatis, umur 0 placeholder
        this.pengalamanKerja = pengalamanKerja;
        this.insentifPengalaman = insentifPengalaman;
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

    // Getter dan Setter untuk insentifPengalaman
    public double getInsentifPengalaman() {
        return insentifPengalaman;
    }

    public void setInsentifPengalaman(double insentifPengalaman) {
        this.insentifPengalaman = insentifPengalaman;
    }

    // OVERRIDING getStatus()
    @Override // <--- Pastikan ada @Override di sini
    public String getStatus() {
        return "Perawat - " + super.getStatus();
    }

    // OVERRIDING tampilkanInfo dari class abstract dan interface
    @Override
    public void tampilkanInfo() {
        System.out.println("=== Data Perawat ===");
        super.tampilkanInfo(); // Memanggil method tampilkanInfo dari Pegawai
        System.out.println("Pengalaman Kerja: " + this.pengalamanKerja);
        System.out.println("Insentif Pengalaman: Rp" + String.format("%,.2f", this.insentifPengalaman));
        System.out.println("Gaji Total: Rp" + String.format("%,.2f", hitungGaji())); // Tampilkan gaji
    }

    // OVERRIDING hitungGaji dari interface dan class abstract
    @Override
    public double hitungGaji() {
        // Contoh perhitungan gaji perawat: gaji pokok dasar + insentif pengalaman
        double gajiPokokDasar = 4500000; // Gaji pokok minimal untuk perawat
        return gajiPokokDasar + this.insentifPengalaman;
    }
}




