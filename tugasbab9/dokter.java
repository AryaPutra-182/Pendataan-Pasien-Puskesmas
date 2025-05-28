/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab9;



/**
 *
 * @author reignite
 */
// Kelas Dokter merupakan subclass dari Pegawai
public class dokter extends pegawai {
    private String spesialis;
    private double tunjanganSpesialisasi; // Tambahkan atribut untuk perhitungan gaji

    // Constructor lengkap: nama, NIP, umur, status, spesialis, tunjanganSpesialisasi
    public dokter(String nama, int umur, String NIP, String status) {
        super(nama, NIP, umur, status); // Memanggil konstruktor kelas Pegawai
        this.spesialis = spesialis;
        this.tunjanganSpesialisasi = tunjanganSpesialisasi;
    }

    // Constructor default spesialis "Umum" (dan NIP, tunjangan default)
    public dokter(String nama, String NIP, int umur, String status) {
        super(nama, NIP, umur, status);
        this.spesialis = "Umum";
        this.tunjanganSpesialisasi = 2000000; // Contoh tunjangan default
    }

    // Constructor untuk dokter kontrak dengan spesialis tertentu (NIP, umur, status default)
   
    public dokter(String nama, String spesialis, double tunjanganSpesialisasi) {
        super(nama, "NIP_Kontrak_" + nama.replaceAll(" ", "_"), 0, "Kontrak"); // NIP dibuat otomatis, umur 0 placeholder
        this.spesialis = spesialis;
        this.tunjanganSpesialisasi = tunjanganSpesialisasi;
    }

    // Getter untuk spesialis
    public String getSpesialis() {
        return spesialis;
    }

    // Setter biasa
    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }

    // Setter overload dengan kode spesialis (jika perlu)
    public void setSpesialis(String spesialis, String kode) {
        this.spesialis = spesialis + " (" + kode + ")";
    }

    // Getter dan Setter untuk tunjanganSpesialisasi
    public double getTunjanganSpesialisasi() {
        return tunjanganSpesialisasi;
    }

    public void setTunjanganSpesialisasi(double tunjanganSpesialisasi) {
        this.tunjanganSpesialisasi = tunjanganSpesialisasi;
    }

    // OVERRIDING getStatus()
    @Override
    public String getStatus() {
        return "Dokter - " + super.getStatus();
    }

    // OVERRIDING tampilkanInfo dari class abstract dan interface
    @Override
    public void tampilkanInfo() {
        System.out.println("=== Data Dokter ===");
        super.tampilkanInfo(); // Memanggil method tampilkanInfo dari Pegawai
        System.out.println("Spesialis: " + this.spesialis);
        System.out.println("Tunjangan Spesialisasi: Rp" + String.format("%,.2f", this.tunjanganSpesialisasi));
        System.out.println("Gaji Total: Rp" + String.format("%,.2f", hitungGaji())); // Tampilkan gaji
    }

    // OVERRIDING hitungGaji dari interface dan class abstract
    @Override
    public double hitungGaji() {
        // Contoh perhitungan gaji dokter: gaji pokok dasar + tunjangan spesialisasi
        double gajiPokokDasar = 8000000; // Gaji pokok minimal untuk dokter
        return gajiPokokDasar + this.tunjanganSpesialisasi;
    }

}



