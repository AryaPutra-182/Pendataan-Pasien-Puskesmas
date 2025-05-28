/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab10;

/**
 *
 * @author reignite
 */


/**
 * Class ini berfungsi sebagai "pembungkus" untuk item di JComboBox.
 * Tujuannya agar kita bisa menampilkan NAMA pegawai, tetapi menyimpan ID-nya di belakang layar.
 */
public class PegawaiItem {
    // Variabel untuk menyimpan data
    private int id;
    private String nama;

    // INI ADALAH CONSTRUCTOR YANG HILANG DAN DICARI OLEH NETBEANS
    // 'Resep' ini menerima sebuah angka (int) dan sebuah tulisan (String).
    public PegawaiItem(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    // Method untuk mengambil ID
    public int getId() {
        return id;
    }

    // Method untuk mengambil Nama
    public String getNama() {
        return nama;
    }

    // Method yang akan digunakan JComboBox untuk menampilkan teks
    @Override
    public String toString() {
        return nama;
    }
}