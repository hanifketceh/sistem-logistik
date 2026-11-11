package com.mycompany.logistik;

import com.mycompany.logistik.model.Barang;
import com.mycompany.logistik.model.Kurir;
import com.mycompany.logistik.model.Pelanggan;
import com.mycompany.logistik.model.Pengiriman;
import com.mycompany.logistik.service.ManajemenPengiriman;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final ManajemenPengiriman manajemen =
            new ManajemenPengiriman("data_pengiriman.ser");

    public static void main(String[] args) {
        manajemen.muatData();

        boolean berjalan = true;
        while (berjalan) {
            tampilkanMenu();
            System.out.print("Pilih menu: ");
            String pilihan = input.nextLine();

            switch (pilihan) {
                case "1" -> tambahPengiriman();
                case "2" -> tampilkanPengiriman();
                case "3" -> ubahStatus();
                case "4" -> {
                    manajemen.simpanData();
                    System.out.println("Data berhasil disimpan. Program selesai.");
                    berjalan = false;
                }
                default -> System.out.println("Pilihan tidak tersedia.");
            }
            System.out.println();
        }
    }

    private static void tampilkanMenu() {
        System.out.println("==============================================");
        System.out.println(" SISTEM INFORMASI MANAJEMEN LOGISTIK");
        System.out.println("==============================================");
        System.out.println("1. Tambah pengiriman");
        System.out.println("2. Lihat seluruh pengiriman");
        System.out.println("3. Ubah status pengiriman");
        System.out.println("4. Simpan dan keluar");
        System.out.println("==============================================");
    }

    private static void tambahPengiriman() {
        System.out.println("\n--- INPUT DATA PENGIRIMAN ---");
        System.out.print("ID Pengiriman: ");
        String id = input.nextLine().trim();

        if (id.isEmpty() || manajemen.cariPengiriman(id) != null) {
            System.out.println("ID kosong atau sudah digunakan.");
            return;
        }

        System.out.print("Nama pelanggan: ");
        String namaPelanggan = input.nextLine().trim();
        System.out.print("Alamat pelanggan: ");
        String alamat = input.nextLine().trim();
        System.out.print("Nomor telepon pelanggan: ");
        String telepon = input.nextLine().trim();

        System.out.print("Nama kurir: ");
        String namaKurir = input.nextLine().trim();
        System.out.print("ID kurir: ");
        String idKurir = input.nextLine().trim();

        System.out.print("Nama barang: ");
        String namaBarang = input.nextLine().trim();
        System.out.print("Berat barang (kg): ");
        double berat;
        try {
            berat = Double.parseDouble(input.nextLine());
            if (berat <= 0) {
                System.out.println("Berat harus lebih dari 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Berat harus berupa angka.");
            return;
        }

        Pelanggan pelanggan = new Pelanggan(namaPelanggan, alamat, telepon);
        Kurir kurir = new Kurir(namaKurir, idKurir);
        Barang barang = new Barang(namaBarang, berat);
        Pengiriman pengiriman = new Pengiriman(id, pelanggan, kurir, barang);

        manajemen.tambahPengiriman(pengiriman);
        System.out.println("Data pengiriman berhasil ditambahkan.");
    }

    private static void tampilkanPengiriman() {
        System.out.println("\n--- DAFTAR PENGIRIMAN ---");
        List<Pengiriman> daftar = manajemen.getDaftarPengiriman();

        if (daftar.isEmpty()) {
            System.out.println("Belum ada data pengiriman.");
            return;
        }

        for (Pengiriman p : daftar) {
            System.out.println(p);
            System.out.println("----------------------------------------------");
        }
    }

    private static void ubahStatus() {
        System.out.println("\n--- UBAH STATUS PENGIRIMAN ---");
        System.out.print("Masukkan ID pengiriman: ");
        String id = input.nextLine().trim();

        Pengiriman pengiriman = manajemen.cariPengiriman(id);
        if (pengiriman == null) {
            System.out.println("Data pengiriman tidak ditemukan.");
            return;
        }

        System.out.println("Status saat ini: " + pengiriman.getStatus());
        System.out.println("1. Diproses");
        System.out.println("2. Dalam perjalanan");
        System.out.println("3. Terkirim");
        System.out.print("Pilih status baru: ");
        String pilihan = input.nextLine();

        switch (pilihan) {
            case "1" -> pengiriman.setStatus("Diproses");
            case "2" -> pengiriman.setStatus("Dalam perjalanan");
            case "3" -> pengiriman.setStatus("Terkirim");
            default -> {
                System.out.println("Pilihan status tidak valid.");
                return;
            }
        }
        System.out.println("Status berhasil diperbarui menjadi: " + pengiriman.getStatus());
    }
}
