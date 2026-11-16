package com.mycompany.logistik;

import com.mycompany.logistik.model.*;
import com.mycompany.logistik.service.ManajemenPengiriman;
import com.mycompany.logistik.util.FileHelper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Program konsol interaktif untuk mendemonstrasikan penerapan:
 * - Scanner untuk membaca input pengguna
 * - Control Flow: if/else, switch, dan loop while/for
 * - Pemanfaatan Model PBO (Pengguna, Pelanggan, Kurir, Pengiriman, RiwayatPengiriman)
 * - File I/O dan Serialization
 */
public class ConsoleDemo {

    private static final String DEFAULT_SAVE_FILE = "data_logistik.dat";
    private static final String DEFAULT_EXPORT_FILE = "laporan_pengiriman.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ManajemenPengiriman manajemen = new ManajemenPengiriman();
        
        // Coba muat data dari serialisasi jika ada, jika belum ada inisialisasi data awal
        File fileData = new File(DEFAULT_SAVE_FILE);
        if (fileData.exists()) {
            try {
                manajemen = FileHelper.muatObjek(fileData);
                System.out.println("[INFO] Berhasil memuat data dari: " + fileData.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("[WARN] Gagal memuat data tersimpan, menggunakan data awal default.");
                manajemen.inisialisasiDataAwal();
            }
        } else {
            manajemen.inisialisasiDataAwal();
        }

        boolean berjalan = true;

        System.out.println("====================================================================");
        System.out.println("    SELAMAT DATANG DI SMART ECONOMY: SISTEM INFORMASI LOGISTIK      ");
        System.out.println("                   (DEMO KONSOL PBO & LOGIKA)                       ");
        System.out.println("====================================================================");

        // Control flow: Loop WHILE untuk menu utama
        while (berjalan) {
            System.out.println("\n--------------------------- MENU UTAMA -----------------------------");
            System.out.println("1. Tampilkan Dashboard & Statistik Pengiriman");
            System.out.println("2. Daftar Pelanggan (Lihat / Tambah)");
            System.out.println("3. Daftar Kurir (Lihat / Tambah)");
            System.out.println("4. Buat Pengiriman Baru");
            System.out.println("5. Perbarui Status Pengiriman");
            System.out.println("6. Lihat Riwayat Pengiriman");
            System.out.println("7. Ekspor Laporan ke File Teks (File I/O)");
            System.out.println("8. Simpan Data ke File Biner (Serialization)");
            System.out.println("9. Muat Data dari File Biner (Deserialization)");
            System.out.println("0. Keluar dari Program");
            System.out.print("Pilih opsi menu (0-9): ");

            String inputPilihan = scanner.nextLine().trim();

            // Control flow: SWITCH CASE untuk pemilihan aksi
            switch (inputPilihan) {
                case "1":
                    tampilkanDashboard(manajemen);
                    break;

                case "2":
                    kelolaPelanggan(scanner, manajemen);
                    break;

                case "3":
                    kelolaKurir(scanner, manajemen);
                    break;

                case "4":
                    buatPengirimanBaru(scanner, manajemen);
                    break;

                case "5":
                    perbaruiStatusPengiriman(scanner, manajemen);
                    break;

                case "6":
                    tampilkanRiwayatPengiriman(manajemen);
                    break;

                case "7":
                    eksporLaporanTeks(manajemen);
                    break;

                case "8":
                    simpanDataSerialisasi(manajemen);
                    break;

                case "9":
                    manajemen = muatDataSerialisasi();
                    break;

                case "0":
                    berjalan = false;
                    System.out.println("\nMenyimpan data sebelum keluar...");
                    try {
                        FileHelper.simpanObjek(new File(DEFAULT_SAVE_FILE), manajemen);
                        System.out.println("[SUKSES] Data berhasil disimpan otomatis.");
                    } catch (IOException e) {
                        System.err.println("[ERROR] Gagal menyimpan data: " + e.getMessage());
                    }
                    System.out.println("Terima kasih telah menggunakan sistem logistik Smart Economy!");
                    break;

                default:
                    // Control flow: IF/ELSE atau validasi default
                    System.out.println("[PERINGATAN] Pilihan tidak valid. Silakan masukkan angka 0 sampai 9.");
                    break;
            }
        }

        scanner.close();
    }

    private static void tampilkanDashboard(ManajemenPengiriman m) {
        System.out.println("\n========== RINGKASAN & STATISTIK LOGISTIK ==========");
        System.out.println("Total Pengiriman  : " + m.getTotalPengiriman());
        System.out.println("Status Diproses   : " + m.getTotalDiproses());
        System.out.println("Status Dikirim    : " + m.getTotalDikirim());
        System.out.println("Status Diterima   : " + m.getTotalDiterima());
        System.out.println("Total Pelanggan   : " + m.getTotalPelanggan());
        System.out.println("Total Kurir       : " + m.getTotalKurir());
        System.out.println("-----------------------------------------------------");
        System.out.println("Daftar Pengiriman Saat Ini:");

        // Control flow: Loop FOR
        List<Pengiriman> daftar = m.getDaftarPengiriman();
        if (daftar.isEmpty()) {
            System.out.println("Belum ada data pengiriman.");
        } else {
            for (int i = 0; i < daftar.size(); i++) {
                Pengiriman p = daftar.get(i);
                System.out.printf("%d. [%s] %s | Barang: %s | Status: [%s]\n",
                        (i + 1), p.getNomorResi(), p.getAlamatTujuan(), p.getNamaBarang(), p.getStatusLabel());
            }
        }
    }

    private static void kelolaPelanggan(Scanner scanner, ManajemenPengiriman m) {
        System.out.println("\n========== MANAJEMEN PELANGGAN ==========");
        System.out.println("1. Lihat Daftar Pelanggan");
        System.out.println("2. Tambah Pelanggan Baru");
        System.out.print("Pilih opsi (1/2): ");
        String sub = scanner.nextLine().trim();

        if ("1".equals(sub)) {
            List<Pelanggan> list = m.getDaftarPelanggan();
            if (list.isEmpty()) {
                System.out.println("Belum ada data pelanggan.");
            } else {
                System.out.println("\n--- DAFTAR PELANGGAN ---");
                // Demonstrasi Polymorphism melalui getInfo()
                for (Pelanggan p : list) {
                    System.out.println(p.getInfo());
                }
            }
        } else if ("2".equals(sub)) {
            try {
                System.out.print("Masukkan ID Pelanggan (contoh: PLG-004): ");
                String id = scanner.nextLine();
                System.out.print("Masukkan Nama Pelanggan: ");
                String nama = scanner.nextLine();
                System.out.print("Masukkan Nomor Telepon: ");
                String telp = scanner.nextLine();
                System.out.print("Masukkan Alamat: ");
                String alamat = scanner.nextLine();

                Pelanggan baru = new Pelanggan(id, nama, telp, alamat);
                m.tambahPelanggan(baru);
                System.out.println("[SUKSES] Pelanggan berhasil ditambahkan: " + baru.getInfo());
            } catch (Exception e) {
                System.out.println("[ERROR] Gagal menambah pelanggan: " + e.getMessage());
            }
        } else {
            System.out.println("Opsi tidak dikenali.");
        }
    }

    private static void kelolaKurir(Scanner scanner, ManajemenPengiriman m) {
        System.out.println("\n========== MANAJEMEN KURIR ==========");
        System.out.println("1. Lihat Daftar Kurir");
        System.out.println("2. Tambah Kurir Baru");
        System.out.print("Pilih opsi (1/2): ");
        String sub = scanner.nextLine().trim();

        if ("1".equals(sub)) {
            List<Kurir> list = m.getDaftarKurir();
            if (list.isEmpty()) {
                System.out.println("Belum ada data kurir.");
            } else {
                System.out.println("\n--- DAFTAR KURIR ---");
                // Demonstrasi Polymorphism melalui getInfo()
                for (Kurir k : list) {
                    System.out.println(k.getInfo());
                }
            }
        } else if ("2".equals(sub)) {
            try {
                System.out.print("Masukkan ID Kurir (contoh: KRR-004): ");
                String id = scanner.nextLine();
                System.out.print("Masukkan Nama Kurir: ");
                String nama = scanner.nextLine();
                System.out.print("Masukkan Nomor Telepon: ");
                String telp = scanner.nextLine();
                System.out.print("Masukkan Kendaraan (Motor/Mobil Box/Truk): ");
                String kendaraan = scanner.nextLine();

                Kurir baru = new Kurir(id, nama, telp, kendaraan);
                m.tambahKurir(baru);
                System.out.println("[SUKSES] Kurir berhasil ditambahkan: " + baru.getInfo());
            } catch (Exception e) {
                System.out.println("[ERROR] Gagal menambah kurir: " + e.getMessage());
            }
        } else {
            System.out.println("Opsi tidak dikenali.");
        }
    }

    private static void buatPengirimanBaru(Scanner scanner, ManajemenPengiriman m) {
        System.out.println("\n========== BUAT PENGIRIMAN BARU ==========");
        if (m.getDaftarPelanggan().isEmpty()) {
            System.out.println("[PERINGATAN] Harap tambahkan pelanggan terlebih dahulu!");
            return;
        }
        if (m.getDaftarKurir().isEmpty()) {
            System.out.println("[PERINGATAN] Harap tambahkan kurir terlebih dahulu!");
            return;
        }

        try {
            System.out.println("\nPilih Pelanggan:");
            for (int i = 0; i < m.getDaftarPelanggan().size(); i++) {
                System.out.println((i + 1) + ". " + m.getDaftarPelanggan().get(i).getInfo());
            }
            System.out.print("Nomor pilihan pelanggan: ");
            int idxP = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idxP < 0 || idxP >= m.getDaftarPelanggan().size()) {
                System.out.println("[ERROR] Indeks pelanggan tidak valid.");
                return;
            }
            Pelanggan pelanggan = m.getDaftarPelanggan().get(idxP);

            System.out.println("\nPilih Kurir:");
            for (int i = 0; i < m.getDaftarKurir().size(); i++) {
                System.out.println((i + 1) + ". " + m.getDaftarKurir().get(i).getInfo());
            }
            System.out.print("Nomor pilihan kurir: ");
            int idxK = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idxK < 0 || idxK >= m.getDaftarKurir().size()) {
                System.out.println("[ERROR] Indeks kurir tidak valid.");
                return;
            }
            Kurir kurir = m.getDaftarKurir().get(idxK);

            System.out.print("Masukkan Nama Barang: ");
            String barang = scanner.nextLine();

            System.out.print("Masukkan Alamat Tujuan (Kosongkan jika sama dengan alamat pelanggan): ");
            String alamat = scanner.nextLine();
            if (alamat.trim().isEmpty()) {
                alamat = pelanggan.getAlamat();
            }

            System.out.print("Masukkan Catatan Pengiriman: ");
            String catatan = scanner.nextLine();

            String tanggal = LocalDate.now().toString();

            // Membuat pengiriman dengan nomor resi otomatis
            Pengiriman p = m.buatPengirimanOtomatis(pelanggan, kurir, barang, alamat, tanggal, catatan);
            System.out.println("\n[SUKSES] Pengiriman berhasil dibuat!");
            System.out.println("Nomor Resi Resmi : " + p.getNomorResi());
            System.out.println("Status Awal      : " + p.getStatusLabel());
        } catch (Exception e) {
            System.out.println("[ERROR] Gagal membuat pengiriman: " + e.getMessage());
        }
    }

    private static void perbaruiStatusPengiriman(Scanner scanner, ManajemenPengiriman m) {
        System.out.println("\n========== PERBARUI STATUS PENGIRIMAN ==========");
        System.out.print("Masukkan Nomor Resi yang ingin diupdate: ");
        String resi = scanner.nextLine().trim();

        Pengiriman p = m.cariPengiriman(resi);
        if (p == null) {
            System.out.println("[ERROR] Resi tidak ditemukan dalam sistem.");
            return;
        }

        System.out.println("Pengiriman Ditemukan: " + p);
        System.out.println("Status Saat Ini: " + p.getStatusLabel());
        System.out.println("Pilihan Status Baru:");
        System.out.println("1. Diproses");
        System.out.println("2. Dikirim");
        System.out.println("3. Diterima");
        System.out.print("Pilih status baru (1-3): ");
        String opt = scanner.nextLine().trim();

        StatusPengiriman status;
        if ("1".equals(opt)) {
            status = StatusPengiriman.DIPROSES;
        } else if ("2".equals(opt)) {
            status = StatusPengiriman.DIKIRIM;
        } else if ("3".equals(opt)) {
            status = StatusPengiriman.DITERIMA;
        } else {
            System.out.println("[ERROR] Pilihan status tidak valid.");
            return;
        }

        System.out.print("Masukkan keterangan perbaruan status: ");
        String ket = scanner.nextLine().trim();

        try {
            RiwayatPengiriman log = m.updateStatusPengiriman(resi, status, ket);
            System.out.println("[SUKSES] Status berhasil diperbarui!");
            System.out.println("Log riwayat tercatat: " + log);
        } catch (Exception e) {
            System.out.println("[ERROR] Gagal memperbarui status: " + e.getMessage());
        }
    }

    private static void tampilkanRiwayatPengiriman(ManajemenPengiriman m) {
        System.out.println("\n========== RIWAYAT LENGKAP PENGIRIMAN ==========");
        List<RiwayatPengiriman> list = m.getSeluruhRiwayat();
        if (list.isEmpty()) {
            System.out.println("Belum ada riwayat aktivitas pengiriman.");
        } else {
            for (RiwayatPengiriman r : list) {
                System.out.println(r);
            }
        }
    }

    private static void eksporLaporanTeks(ManajemenPengiriman m) {
        File file = new File(DEFAULT_EXPORT_FILE);
        try {
            FileHelper.eksporLaporanTeks(file, m);
            System.out.println("[SUKSES] Laporan berhasil diekspor ke: " + file.getAbsolutePath());
            System.out.println("\nIsi ringkas file laporan teks:");
            System.out.println("-----------------------------------------------------");
            String isi = FileHelper.bacaFileTeks(file);
            System.out.println(isi);
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal mengekspor laporan: " + e.getMessage());
        }
    }

    private static void simpanDataSerialisasi(ManajemenPengiriman m) {
        File file = new File(DEFAULT_SAVE_FILE);
        try {
            FileHelper.simpanObjek(file, m);
            System.out.println("[SUKSES] Data berhasil disimpan (Serialization) ke: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal menyimpan data serialisasi: " + e.getMessage());
        }
    }

    private static ManajemenPengiriman muatDataSerialisasi() {
        File file = new File(DEFAULT_SAVE_FILE);
        try {
            ManajemenPengiriman m = FileHelper.muatObjek(file);
            System.out.println("[SUKSES] Data berhasil dimuat (Deserialization) dari: " + file.getAbsolutePath());
            System.out.println("Jumlah pengiriman dimuat: " + m.getTotalPengiriman());
            return m;
        } catch (Exception e) {
            System.out.println("[ERROR] Gagal memuat data serialisasi: " + e.getMessage());
            return new ManajemenPengiriman();
        }
    }
}

