/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasBab4;

/**
 *
 * @author reignite
 */
// Kelas Dokter merupakan subclass dari Pegawai
public class dokter extends pegawai {
    private String spesialis;

    public dokter(String nama, int umur, String status, String spesialis) {
        super(nama, umur, status);
        this.spesialis = spesialis;
    }

    public dokter(String nama, int umur, String status) {
        super(nama, umur, status);
        this.spesialis = "Umum";
    }

    public dokter(String nama, String spesialis) {
        super(nama, 0, "Kontrak");
        this.spesialis = spesialis;
    }

    public String getSpesialis() {
        return spesialis;
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }

    public void setSpesialis(String spesialis, String kode) {
        this.spesialis = spesialis + " (" + kode + ")";
    }

    // OVERRIDING getStatus()
    @Override
    public String getStatus() {
        return "Dokter - " + super.getStatus();
    }
}


