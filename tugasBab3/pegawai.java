/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasBab3;

/**
 *
 * @author reignite
 */
public abstract class pegawai {  
    // Variabel yang dapat diakses oleh subclass (protected)
    protected String nama;  
    protected int umur;  
    protected String status;  
    
    // Konstruktor untuk menginisialisasi objek Pegawai dengan nilai awal
    public pegawai(String nama, int umur, String status) {  
        this.nama = nama;  // Mengisi variabel nama dengan nilai dari parameter
        this.umur = umur;  // Mengisi variabel umur dengan nilai dari parameter
        this.status = status;  // Mengisi variabel status dengan nilai dari parameter
    }  
    
    // Method getter untuk mendapatkan nilai nama
    public String getNama() {  
        return nama;  
    }  
    
    // Method getter untuk mendapatkan nilai umur
    public int getUmur() {  
        return umur;  
    }  
    
    // Method getter untuk mendapatkan nilai status
    public String getStatus() {  
        return status;  
    }  

    // Method setter untuk mengubah nilai nama
    public void setNama(String nama) {  
        this.nama = nama;  
    }  
    
    // Method setter untuk mengubah nilai umur
    public void setUmur(int umur) {  
        this.umur = umur;  
    }  
    
    // Method setter untuk mengubah nilai status
    public void setStatus(String status) {  
        this.status = status;  
    }  
   
    //membuat abstract tampilkanInfo untuk diterapkan di class turunan
    public abstract void tampilkanInfo();
}
