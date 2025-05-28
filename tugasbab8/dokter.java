/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab8;



/**
 *
 * @author reignite
 */
// Kelas Dokter merupakan subclass dari Pegawai
public class dokter extends pegawai {
    private String spesialis;

    // Constructor lengkap
    public dokter(String nama, int umur, String status, String spesialis) {
        super(nama, umur, status);
        this.spesialis = spesialis;
    }

    // Constructor default spesialis "Umum"
    public dokter(String nama, int umur, String status) {
        super(nama, umur, status);
        this.spesialis = "Umum";
    }

    // Constructor untuk dokter kontrak dengan spesialis tertentu
    public dokter(String nama, String spesialis) {
        super(nama, 0, "Kontrak");
        this.spesialis = spesialis;
    }

    // Getter untuk spesialis
    public String getSpesialis() {
        return spesialis;
    }

    // Setter biasa
    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }

    // Setter overload dengan kode spesialis
    public void setSpesialis(String spesialis, String kode) {
        this.spesialis = spesialis + " (" + kode + ")";
    }

    // OVERRIDING getStatus()
    @Override
    public String getStatus() {
        return "Dokter - " + super.getStatus();
    }

    // OVERRIDING tampilkanInfo dari class abstract
    @Override
    public void tampilkanInfo() {
        System.out.println("=== Data Dokter ===");
        System.out.println("Nama: " + nama);
        System.out.println("Umur: " + umur);
        System.out.println("Status: " + status);
        System.out.println("Spesialis: " + spesialis);
    }
}



