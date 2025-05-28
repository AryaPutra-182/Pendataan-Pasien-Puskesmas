/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tugasbab10;

/**
 *
 * @author reignite
 */
public interface IPegawai {
    String getNama();
    void setNama(String nama);
    String getNIP(); // <-- Tambahkan ini
    void setNIP(String NIP); // <-- Tambahkan ini
    int getUmur();
    void setUmur(int umur);
    String getStatus();
    void setStatus(String status);
    void tampilkanInfo();
    double hitungGaji();
}
