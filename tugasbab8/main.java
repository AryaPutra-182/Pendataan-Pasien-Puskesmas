/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasbab8;



/**
 *
 * @author reignite
 */
import java.util.Scanner;

    public class main {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            pegawai[] daftarPegawai = new pegawai[3];

            System.out.println("=== INPUT DATA PEGAWAI (DOKTER / PERAWAT) ===");

            for (int i = 0; i < daftarPegawai.length; i++) {
                System.out.println("\nPegawai ke-" + (i + 1));
                System.out.print("Jenis pegawai (dokter/perawat): ");
                String jenis = scanner.nextLine().trim().toLowerCase();

                try {
                    System.out.print("Nama: ");
                    String nama = scanner.nextLine();

                    if (jenis.equals("dokter")) {
                        System.out.print("Umur (ketik '-' jika dokter kontrak): ");
                        String umurInput = scanner.nextLine();

                        if (umurInput.equals("-")) {
                            System.out.print("Spesialisasi: ");
                            String spesialis = scanner.nextLine();
                            daftarPegawai[i] = new dokter(nama, spesialis); // kontrak
                        } else {
                            int umur = Integer.parseInt(umurInput);
                            System.out.print("Status: ");
                            String status = scanner.nextLine();
                            System.out.print("Spesialisasi: ");
                            String spesialis = scanner.nextLine();
                            daftarPegawai[i] = new dokter(nama, umur, status, spesialis);
                        }

                    } else if (jenis.equals("perawat")) {
                        System.out.print("Umur: ");
                        int umur = Integer.parseInt(scanner.nextLine());
                        System.out.print("Status: ");
                        String status = scanner.nextLine();
                        System.out.print("Pengalaman kerja: ");
                        String pengalaman = scanner.nextLine();
                        daftarPegawai[i] = new perawat(nama, umur, status, pengalaman);

                    } else {
                        System.out.println("Jenis pegawai tidak dikenal.");
                        i--;
                    }

                } catch (Exception e) {
                    System.out.println("Terjadi kesalahan input: " + e.getMessage());
                    i--;
                }
            }

            scanner.close();

            System.out.println("\n=== DATA PEGAWAI ===");
            for (pegawai p : daftarPegawai) {
                p.tampilkanInfo();
                System.out.println();
            }
        }
    }





