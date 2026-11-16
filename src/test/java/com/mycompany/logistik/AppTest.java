package com.mycompany.logistik;

import com.mycompany.logistik.model.*;
import com.mycompany.logistik.service.ManajemenPengiriman;
import com.mycompany.logistik.util.FileHelper;

import java.io.File;

/**
 * Otomatisasi pengujian komprehensif untuk seluruh komponen PBO,
 * logika bisnis, File I/O, serta Serialization/Deserialization.
 */
public class AppTest {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  MEMULAI VERIFIKASI PENGUJIAN OTOMATIS LOGISTIK ");
        System.out.println("=================================================");

        testModelDanPBO();
        testManajemenPengirimanCRUD();
        testFileIOTeks();
        testSerializationDeserialization();

        System.out.println("=================================================");
        System.out.println("  SEMUA PENGUJIAN BERHASIL TANPA ERROR (100% OK) ");
        System.out.println("=================================================");
    }

    private static void testModelDanPBO() {
        System.out.println("\n[TEST 1] Pengujian Model PBO, Inheritance, & Polymorphism...");

        // Polymorphism: Pengguna reference to Pelanggan
        Pengguna p1 = new Pelanggan("PLG-100", "Budi Sanjaya", "081234567890", "Jl. Merdeka No. 1");
        assert p1.getPeran().equals("Pelanggan");
        assert p1.getInfo().contains("Budi Sanjaya");
        assert p1.getInfo().contains("Jl. Merdeka No. 1");

        // Polymorphism: Pengguna reference to Kurir
        Pengguna k1 = new Kurir("KRR-100", "Joko Susilo", "085612345678", "Mobil Box");
        assert k1.getPeran().equals("Kurir");
        assert k1.getInfo().contains("Joko Susilo");
        assert k1.getInfo().contains("Mobil Box");

        // Encapsulation validation test: validasi nomor telepon invalid
        boolean phoneErrorCaught = false;
        try {
            new Pelanggan("PLG-101", "Ani", "abcde123", "Alamat");
        } catch (IllegalArgumentException e) {
            phoneErrorCaught = true;
        }
        assert phoneErrorCaught : "Validasi nomor telepon tidak berjalan!";

        // Encapsulation validation test: validasi nama kosong
        boolean nameErrorCaught = false;
        try {
            new Kurir("KRR-101", "", "081234567890", "Motor");
        } catch (IllegalArgumentException e) {
            nameErrorCaught = true;
        }
        assert nameErrorCaught : "Validasi nama kosong tidak berjalan!";

        System.out.println(">> TEST 1 PASSED: Model, Inheritance, Polymorphism, dan Encapsulation valid.");
    }

    private static void testManajemenPengirimanCRUD() {
        System.out.println("\n[TEST 2] Pengujian Logika Bisnis & CRUD ManajemenPengiriman...");

        ManajemenPengiriman manajemen = new ManajemenPengiriman();
        manajemen.inisialisasiDataAwal();

        assert manajemen.getTotalPelanggan() >= 3;
        assert manajemen.getTotalKurir() >= 3;
        assert manajemen.getTotalPengiriman() >= 3;

        // Tambah Pengiriman baru otomatis
        Pelanggan p = manajemen.getDaftarPelanggan().get(0);
        Kurir k = manajemen.getDaftarKurir().get(0);
        Pengiriman baru = manajemen.buatPengirimanOtomatis(p, k, "Paket Buku", "Bandung", "2026-11-11", "Harap hati-hati");

        assert baru.getNomorResi().startsWith("EXP-");
        assert baru.getStatus() == StatusPengiriman.DIPROSES;

        // Update status pengiriman
        RiwayatPengiriman r1 = manajemen.updateStatusPengiriman(baru.getNomorResi(), StatusPengiriman.DIKIRIM, "Kurir sedang meluncur");
        assert baru.getStatus() == StatusPengiriman.DIKIRIM;
        assert r1.getStatusSebelumnya().equals("Diproses");
        assert r1.getStatusTerbaru().equals("Dikirim");

        RiwayatPengiriman r2 = manajemen.updateStatusPengiriman(baru.getNomorResi(), StatusPengiriman.DITERIMA, "Diterima oleh penerima langsung");
        assert baru.getStatus() == StatusPengiriman.DITERIMA;
        assert r2.getStatusSebelumnya().equals("Dikirim");
        assert r2.getStatusTerbaru().equals("Diterima");

        // Riwayat harus tercatat
        assert baru.getRiwayatList().size() == 3; // Initial + Dikirim + Diterima

        System.out.println(">> TEST 2 PASSED: CRUD, resi otomatis, mutasi status, dan log riwayat valid.");
    }

    private static void testFileIOTeks() {
        System.out.println("\n[TEST 3] Pengujian File I/O Teks (FileWriter & FileReader)...");

        ManajemenPengiriman manajemen = new ManajemenPengiriman();
        manajemen.inisialisasiDataAwal();

        File tempLaporan = new File("test_laporan.txt");
        try {
            FileHelper.eksporLaporanTeks(tempLaporan, manajemen);
            assert tempLaporan.exists() : "File laporan gagal dibuat!";

            String isi = FileHelper.bacaFileTeks(tempLaporan);
            assert isi.toUpperCase().contains("LAPORAN SISTEM INFORMASI MANAJEMEN LOGISTIK SMART ECONOMY");
            assert isi.toUpperCase().contains("TOTAL PENGIRIMAN");
            assert isi.toUpperCase().contains("RIWAYAT AKTIVITAS LOGISTIK");

            System.out.println(">> TEST 3 PASSED: File I/O Teks berhasil diekspor dan dibaca kembali.");
        } catch (Exception e) {
            throw new RuntimeException("Test File I/O gagal: " + e.getMessage(), e);
        } finally {
            if (tempLaporan.exists()) {
                tempLaporan.delete();
            }
        }
    }

    private static void testSerializationDeserialization() {
        System.out.println("\n[TEST 4] Pengujian Serialization & Deserialization Objek...");

        ManajemenPengiriman dataAwal = new ManajemenPengiriman();
        dataAwal.inisialisasiDataAwal();
        int totalAwal = dataAwal.getTotalPengiriman();

        File tempSer = new File("test_data.dat");
        try {
            // Simpan objek (Serialization)
            FileHelper.simpanObjek(tempSer, dataAwal);
            assert tempSer.exists() : "File objek .dat gagal dibuat!";

            // Muat objek kembali (Deserialization)
            ManajemenPengiriman dataPulih = FileHelper.muatObjek(tempSer);
            assert dataPulih != null : "Objek gagal dipulihkan!";
            assert dataPulih.getTotalPengiriman() == totalAwal : "Jumlah pengiriman setelah deserialization tidak cocok!";
            assert dataPulih.getDaftarPelanggan().size() == dataAwal.getDaftarPelanggan().size() : "Jumlah pelanggan tidak cocok!";
            assert dataPulih.getDaftarKurir().size() == dataAwal.getDaftarKurir().size() : "Jumlah kurir tidak cocok!";

            System.out.println(">> TEST 4 PASSED: Serialization dan Deserialization berhasil memulihkan state objek secara presisi.");
        } catch (Exception e) {
            throw new RuntimeException("Test Serialization gagal: " + e.getMessage(), e);
        } finally {
            if (tempSer.exists()) {
                tempSer.delete();
            }
        }
    }
}
