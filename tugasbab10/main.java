/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab10;



/**
 *
 * @author reignite
 */
import java.util.Scanner;

import java.util.Scanner; // Jangan lupa import Scanner

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Menggunakan array bertipe IKaryawan agar bisa menampung Dokter dan Perawat secara polimorfis
        IPegawai[] daftarPegawai = new IPegawai[3];

        System.out.println("=== INPUT DATA PEGAWAI (DOKTER / PERAWAT) ===");

        for (int i = 0; i < daftarPegawai.length; i++) {
            System.out.println("\n--- Pegawai ke-" + (i + 1) + " ---");
            System.out.print("Jenis pegawai (dokter/perawat): ");
            String jenis = scanner.nextLine().trim().toLowerCase();

            try {
                System.out.print("Nama: ");
                String nama = scanner.nextLine();
                System.out.print("NIP (Nomor Induk Pegawai): "); // Minta NIP untuk semua
                String NIP = scanner.nextLine();

                if (jenis.equals("dokter")) {
                    System.out.print("Umur (ketik '0' jika dokter kontrak tanpa umur spesifik): ");
                    int umur = Integer.parseInt(scanner.nextLine());
                    System.out.print("Status: ");
                    String status = scanner.nextLine();
                    System.out.print("Spesialisasi: ");
                    String spesialisasi = scanner.nextLine();
                    System.out.print("Tunjangan Spesialisasi (contoh: 3500000): ");
                    double tunjanganSpesialisasi = Double.parseDouble(scanner.nextLine());

                    // Gunakan konstruktor Dokter yang paling sesuai
                    daftarPegawai[i] = new dokter(nama, umur, NIP, status);

                } else if (jenis.equals("perawat")) {
                    System.out.print("Umur: ");
                    int umur = Integer.parseInt(scanner.nextLine());
                    System.out.print("Status: ");
                    String status = scanner.nextLine();
                    System.out.print("Pengalaman kerja (contoh: '5 tahun' atau 'Belum ada'): ");
                    String pengalamanKerja = scanner.nextLine();
                    System.out.print("Insentif Pengalaman (contoh: 750000): ");
                    double insentifPengalaman = Double.parseDouble(scanner.nextLine());

                    // Gunakan konstruktor Perawat yang paling sesuai
                    daftarPegawai[i] = new perawat(nama, umur, NIP, status);

                } else {
                    System.out.println("Jenis pegawai tidak dikenal. Silakan coba lagi.");
                    i--; // Ulangi iterasi ini jika input tidak valid
                }

            } catch (NumberFormatException e) {
                System.out.println("Input angka tidak valid. Pastikan memasukkan angka untuk umur/tunjangan/insentif. " + e.getMessage());
                i--; // Ulangi iterasi ini jika input tidak valid
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
                i--; // Ulangi iterasi ini jika ada error lain
            }
        }

        scanner.close(); // Penting untuk menutup scanner setelah selesai

        System.out.println("\n=== DATA SELURUH PEGAWAI ===");
        for (IPegawai p : daftarPegawai) { // Iterasi menggunakan IPegawai
            if (p != null) { // Pastikan objek tidak null jika ada input yang gagal
                p.tampilkanInfo(); // Memanggil tampilkanInfo secara polimorfis
                System.out.println("------------------------------------");
            }
        }
    }
}





