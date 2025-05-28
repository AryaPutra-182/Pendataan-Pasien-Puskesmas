/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab9;



/**
 *
 * @author reignite
 */


public abstract class pegawai implements IPegawai {
    protected String nama;
    protected String NIP; // <-- Tambahkan NIP
    protected int umur;
    protected String status;

    public pegawai(String nama, String NIP, int umur, String status) { 
        this.nama = nama;
        this.NIP = NIP; // <-- Inisialisasi NIP
        this.umur = umur;
        this.status = status;
    }

    @Override
    public String getNama() {
        return nama;
    }

    @Override
    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public String getNIP() { // <-- Implementasi getNIP
        return NIP;
    }

    @Override
    public void setNIP(String NIP) { // <-- Implementasi setNIP
        this.NIP = NIP;
    }

    @Override
    public int getUmur() {
        return umur;
    }

    @Override
    public void setUmur(int umur) {
        this.umur = umur;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Nama: " + this.getNama());
        System.out.println("NIP: " + this.getNIP()); // Tampilkan NIP
        System.out.println("Umur: " + this.getUmur() + " tahun");
        System.out.println("Status: " + this.getStatus());
    }

    @Override
    public abstract double hitungGaji();
}

