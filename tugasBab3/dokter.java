/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasBab3;

/**
 *
 * @author reignite
 */
public class dokter extends pegawai {  
    // Variabel khusus untuk menyimpan spesialisasi dokter
    private String spesialis;  
    
    // Konstruktor untuk menginisialisasi objek Dokter
    // Menggunakan super() untuk memanggil konstruktor Pegawai
    public dokter(String nama, int umur, String status, String spesialis) {  
        super(nama, umur, status);  // Memanggil konstruktor Pegawai (superclass)
        this.spesialis = spesialis;  // Mengisi variabel spesialis dengan nilai dari parameter
    }  

    // Method getter untuk mendapatkan nilai spesialis
    public String getSpesialis() {  
        return spesialis;  
    }  
    
    // Method setter untuk mengubah nilai spesialis
    public void setSpesialis(String spesialis) {  
        this.spesialis = spesialis;  
    }  

    @Override
    public void tampilkanInfo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

