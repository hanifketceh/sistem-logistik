package com.mycompany.logistik.util;

import com.mycompany.logistik.model.Pelanggan;
import com.mycompany.logistik.model.Kurir;
import com.mycompany.logistik.model.Pengiriman;
import com.mycompany.logistik.model.RiwayatPengiriman;
import com.mycompany.logistik.service.ManajemenPengiriman;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utilitas untuk mengelola File I/O (Teks) dan Object Serialization/Deserialization.
 */
public class FileHelper {

    // ==========================================
    // 1. FILE I/O MENGGUNAKAN TEKS (WRITER/READER)
    // ==========================================

    /**
     * Mengekspor laporan pengiriman ke file teks menggunakan FileWriter dan BufferedWriter.
     */
    public static void eksporLaporanTeks(File file, ManajemenPengiriman manajemen) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File tujuan tidak boleh null.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("========================================================================================\n");
            writer.write("           LAPORAN SISTEM INFORMASI MANAJEMEN LOGISTIK SMART ECONOMY            \n");
            writer.write("========================================================================================\n");
            writer.write("Waktu Ekspor : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n");
            writer.write("Total Pengiriman : " + manajemen.getTotalPengiriman() + "\n");
            writer.write("  - Diproses     : " + manajemen.getTotalDiproses() + "\n");
            writer.write("  - Dikirim      : " + manajemen.getTotalDikirim() + "\n");
            writer.write("  - Diterima     : " + manajemen.getTotalDiterima() + "\n");
            writer.write("----------------------------------------------------------------------------------------\n");
            writer.write(String.format("%-18s | %-16s | %-14s | %-14s | %-10s | %-12s\n",
                    "NO RESI", "BARANG", "PELANGGAN", "KURIR", "STATUS", "TANGGAL"));
            writer.write("----------------------------------------------------------------------------------------\n");

            for (Pengiriman p : manajemen.getDaftarPengiriman()) {
                writer.write(String.format("%-18s | %-16s | %-14s | %-14s | %-10s | %-12s\n",
                        p.getNomorResi(),
                        p.getNamaBarang(),
                        p.getPelanggan() != null ? p.getPelanggan().getNama() : "-",
                        p.getKurir() != null ? p.getKurir().getNama() : "-",
                        p.getStatusLabel(),
                        p.getTanggalPengiriman()));
                writer.write("  Alamat Tujuan: " + p.getAlamatTujuan() + "\n");
                if (p.getCatatan() != null && !p.getCatatan().isEmpty()) {
                    writer.write("  Catatan: " + p.getCatatan() + "\n");
                }
            }

            writer.write("========================================================================================\n");
            writer.write("                               RIWAYAT AKTIVITAS LOGISTIK                               \n");
            writer.write("========================================================================================\n");
            writer.write(String.format("%-20s | %-18s | %-12s | %-12s | %s\n",
                    "WAKTU", "NO RESI", "SEBELUMNYA", "TERBARU", "KETERANGAN"));
            writer.write("----------------------------------------------------------------------------------------\n");

            for (RiwayatPengiriman r : manajemen.getSeluruhRiwayat()) {
                writer.write(String.format("%-20s | %-18s | %-12s | %-12s | %s\n",
                        r.getWaktuPerubahan(),
                        r.getNomorResi(),
                        r.getStatusSebelumnya(),
                        r.getStatusTerbaru(),
                        r.getKeterangan()));
            }

            writer.write("========================================================================================\n");
            writer.write("                              AKHIR DARI DOKUMEN LAPORAN                                \n");
            writer.write("========================================================================================\n");
            writer.flush();
        }
    }

    /**
     * Membaca isi file teks menggunakan FileReader dan BufferedReader.
     * Mengembalikan teks lengkap dari file.
     */
    public static String bacaFileTeks(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException("File teks tidak ditemukan.");
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    // ==========================================
    // 2. SERIALIZATION DAN DESERIALIZATION OBJEK
    // ==========================================

    /**
     * Menyimpan objek ManajemenPengiriman ke file biner menggunakan ObjectOutputStream.
     */
    public static void simpanObjek(File file, ManajemenPengiriman manajemen) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File penyimpanan tidak boleh null.");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(manajemen);
            oos.flush();
        }
    }

    /**
     * Membaca dan mengembalikan objek ManajemenPengiriman dari file biner menggunakan ObjectInputStream.
     */
    public static ManajemenPengiriman muatObjek(File file) throws IOException, ClassNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException("File data serialisasi tidak ditemukan: " + (file != null ? file.getPath() : "null"));
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof ManajemenPengiriman) {
                return (ManajemenPengiriman) obj;
            } else {
                throw new IOException("Format objek di dalam file tidak sesuai dengan ManajemenPengiriman.");
            }
        }
    }
}

