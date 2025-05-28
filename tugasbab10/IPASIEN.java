/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tugasbab10;

/**
 *
 * @author reignite
 */
public interface IPASIEN {

   
    void status_pasien();
    void tampilkan_riwayat_penyakit();


    void input_diagnosa();
    void input_diagnosa(String kondisi);
    void input_diagnosa(String kondisi1, String kondisi2);

    
    double hitung_tagihan(double biaya);
    double hitung_tagihan(double biaya, double diskon);
    double hitung_tagihan(); 

    
    void tampilkanData();
}
