package com.mycompany.logistik.service;

import com.mycompany.logistik.model.*;
import com.mycompany.logistik.util.ResiGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service utama untuk mengelola entitas logistik: Pelanggan, Kurir, Pengiriman,
 * dan Riwayat.
 * Mengimplementasikan Serializable agar seluruh state manajemen dapat disimpan
 * dan dipulihkan.
 */
public class ManajemenPengiriman implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Pelanggan> daftarPelanggan;
    private ArrayList<Kurir> daftarKurir;
    private ArrayList<Pengiriman> daftarPengiriman;
    private ArrayList<RiwayatPengiriman> seluruhRiwayat;

    public ManajemenPengiriman() {
        this.daftarPelanggan = new ArrayList<>();
        this.daftarKurir = new ArrayList<>();
        this.daftarPengiriman = new ArrayList<>();
        this.seluruhRiwayat = new ArrayList<>();
    }

    /**
     * Mengisi data awal percontohan untuk kemudahan demonstrasi.
     */
    public void inisialisasiDataAwal() {
        if (!daftarPelanggan.isEmpty() || !daftarKurir.isEmpty() || !daftarPengiriman.isEmpty()) {
            return;
        }

        // Data Pelanggan Awal
        Pelanggan p1 = new Pelanggan("PLG-001", "Budi Santoso", "081234567890", "Jl. Sudirman No. 45, Jakarta Pusat");
        Pelanggan p2 = new Pelanggan("PLG-002", "Siti Aminah", "085678901234", "Jl. Malioboro No. 12, Yogyakarta");
        Pelanggan p3 = new Pelanggan("PLG-003", "Andi Wijaya", "087812345678", "Jl. Diponegoro No. 88, Surabaya");
        tambahPelanggan(p1);
        tambahPelanggan(p2);
        tambahPelanggan(p3);

        // Data Kurir Awal
        Kurir k1 = new Kurir("KRR-001", "Rian Hidayat", "081399887766", "Sepeda Motor");
        Kurir k2 = new Kurir("KRR-002", "Dedi Pratama", "082155443322", "Mobil Box");
        Kurir k3 = new Kurir("KRR-003", "Agus Setiawan", "083811223344", "Truk Engkel");
        tambahKurir(k1);
        tambahKurir(k2);
        tambahKurir(k3);

        // Data Pengiriman Awal
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Pengiriman peng1 = new Pengiriman("EXP-20261111-1001", p1, k1, "Dokumen Kontrak Bisnis", p1.getAlamat(), today,
                "Dokumen penting segel rapi");
        tambahPengiriman(peng1);

        Pengiriman peng2 = new Pengiriman("EXP-20261111-1002", p2, k2, "Paket Pakaian Batik",
                "Jl. Raya Solo Km 5, Klaten", today, "Fragile - Jangan dibanting");
        tambahPengiriman(peng2);
        updateStatusPengiriman("EXP-20261111-1002", StatusPengiriman.DIKIRIM,
                "Paket sedang dalam perjalanan dengan mobil box");

        Pengiriman peng3 = new Pengiriman("EXP-20261111-1003", p3, k3, "Komponen Elektronik Mesin",
                "Kawasan Industri Rungkut, Surabaya", today, "Prioritas pengiriman cepat");
        tambahPengiriman(peng3);
        updateStatusPengiriman("EXP-20261111-1003", StatusPengiriman.DIKIRIM,
                "Barang diberangkatkan dari gudang utama");
        updateStatusPengiriman("EXP-20261111-1003", StatusPengiriman.DITERIMA,
                "Barang diterima oleh Pak Joko (Security Pabrik)");
    }

    // ==========================================
    // OPERASI MANAJEMEN PELANGGAN
    // ==========================================

    public List<Pelanggan> getDaftarPelanggan() {
        return daftarPelanggan;
    }

    public void tambahPelanggan(Pelanggan pelanggan) {
        if (pelanggan == null) {
            throw new IllegalArgumentException("Data pelanggan tidak boleh null.");
        }
        if (cariPelanggan(pelanggan.getId()) != null) {
            throw new IllegalArgumentException("ID Pelanggan '" + pelanggan.getId() + "' sudah terdaftar.");
        }
        daftarPelanggan.add(pelanggan);
    }

    public void editPelanggan(Pelanggan pelangganBaru) {
        if (pelangganBaru == null) {
            throw new IllegalArgumentException("Data pelanggan tidak boleh null.");
        }
        for (int i = 0; i < daftarPelanggan.size(); i++) {
            if (daftarPelanggan.get(i).getId().equalsIgnoreCase(pelangganBaru.getId())) {
                daftarPelanggan.set(i, pelangganBaru);
                return;
            }
        }
        throw new IllegalArgumentException("Pelanggan dengan ID '" + pelangganBaru.getId() + "' tidak ditemukan.");
    }

    public boolean hapusPelanggan(String id) {
        if (id == null)
            return false;
        return daftarPelanggan.removeIf(p -> p.getId().equalsIgnoreCase(id.trim()));
    }

    public Pelanggan cariPelanggan(String id) {
        if (id == null)
            return null;
        for (Pelanggan p : daftarPelanggan) {
            if (p.getId().equalsIgnoreCase(id.trim())) {
                return p;
            }
        }
        return null;
    }

    // ==========================================
    // OPERASI MANAJEMEN KURIR
    // ==========================================

    public List<Kurir> getDaftarKurir() {
        return daftarKurir;
    }

    public void tambahKurir(Kurir kurir) {
        if (kurir == null) {
            throw new IllegalArgumentException("Data kurir tidak boleh null.");
        }
        if (cariKurir(kurir.getId()) != null) {
            throw new IllegalArgumentException("ID Kurir '" + kurir.getId() + "' sudah terdaftar.");
        }
        daftarKurir.add(kurir);
    }

    public void editKurir(Kurir kurirBaru) {
        if (kurirBaru == null) {
            throw new IllegalArgumentException("Data kurir tidak boleh null.");
        }
        for (int i = 0; i < daftarKurir.size(); i++) {
            if (daftarKurir.get(i).getId().equalsIgnoreCase(kurirBaru.getId())) {
                daftarKurir.set(i, kurirBaru);
                return;
            }
        }
        throw new IllegalArgumentException("Kurir dengan ID '" + kurirBaru.getId() + "' tidak ditemukan.");
    }

    public boolean hapusKurir(String id) {
        if (id == null)
            return false;
        return daftarKurir.removeIf(k -> k.getId().equalsIgnoreCase(id.trim()));
    }

    public Kurir cariKurir(String id) {
        if (id == null)
            return null;
        for (Kurir k : daftarKurir) {
            if (k.getId().equalsIgnoreCase(id.trim())) {
                return k;
            }
        }
        return null;
    }

    // ==========================================
    // OPERASI MANAJEMEN PENGIRIMAN
    // ==========================================

    public List<Pengiriman> getDaftarPengiriman() {
        return daftarPengiriman;
    }

    public void tambahPengiriman(Pengiriman pengiriman) {
        if (pengiriman == null) {
            throw new IllegalArgumentException("Data pengiriman tidak boleh null.");
        }
        if (cariPengiriman(pengiriman.getNomorResi()) != null) {
            throw new IllegalArgumentException("Nomor resi '" + pengiriman.getNomorResi() + "' sudah ada.");
        }
        daftarPengiriman.add(pengiriman);
        // Tambahkan riwayat awalnya ke seluruh riwayat
        for (RiwayatPengiriman r : pengiriman.getRiwayatList()) {
            if (!seluruhRiwayat.contains(r)) {
                seluruhRiwayat.add(r);
            }
        }
    }

    public Pengiriman buatPengirimanOtomatis(Pelanggan pelanggan, Kurir kurir, String namaBarang,
            String alamatTujuan, String tanggalPengiriman, String catatan) {
        String resi;
        do {
            resi = ResiGenerator.generateResi();
        } while (cariPengiriman(resi) != null);

        Pengiriman pengiriman = new Pengiriman(resi, pelanggan, kurir, namaBarang, alamatTujuan, tanggalPengiriman,
                catatan);
        tambahPengiriman(pengiriman);
        return pengiriman;
    }

    public void editPengiriman(Pengiriman pengirimanBaru) {
        if (pengirimanBaru == null) {
            throw new IllegalArgumentException("Data pengiriman tidak boleh null.");
        }
        for (int i = 0; i < daftarPengiriman.size(); i++) {
            if (daftarPengiriman.get(i).getNomorResi().equalsIgnoreCase(pengirimanBaru.getNomorResi())) {
                daftarPengiriman.set(i, pengirimanBaru);
                return;
            }
        }
        throw new IllegalArgumentException(
                "Pengiriman dengan resi '" + pengirimanBaru.getNomorResi() + "' tidak ditemukan.");
    }

    public boolean hapusPengiriman(String nomorResi) {
        if (nomorResi == null)
            return false;
        seluruhRiwayat.removeIf(r -> r.getNomorResi().equalsIgnoreCase(nomorResi.trim()));
        return daftarPengiriman.removeIf(p -> p.getNomorResi().equalsIgnoreCase(nomorResi.trim()));
    }

    public Pengiriman cariPengiriman(String nomorResi) {
        if (nomorResi == null)
            return null;
        for (Pengiriman p : daftarPengiriman) {
            if (p.getNomorResi().equalsIgnoreCase(nomorResi.trim())) {
                return p;
            }
        }
        return null;
    }

    public RiwayatPengiriman updateStatusPengiriman(String nomorResi, StatusPengiriman statusBaru, String keterangan) {
        Pengiriman p = cariPengiriman(nomorResi);
        if (p == null) {
            throw new IllegalArgumentException("Pengiriman dengan resi '" + nomorResi + "' tidak ditemukan.");
        }
        RiwayatPengiriman log = p.perbaruiStatus(statusBaru, keterangan);
        seluruhRiwayat.add(log);
        return log;
    }

    // ==========================================
    // RIWAYAT PENGIRIMAN
    // ==========================================

    public List<RiwayatPengiriman> getSeluruhRiwayat() {
        return seluruhRiwayat;
    }

    public List<RiwayatPengiriman> getRiwayatByResi(String nomorResi) {
        if (nomorResi == null)
            return Collections.emptyList();
        List<RiwayatPengiriman> hasil = new ArrayList<>();
        for (RiwayatPengiriman r : seluruhRiwayat) {
            if (r.getNomorResi().equalsIgnoreCase(nomorResi.trim())) {
                hasil.add(r);
            }
        }
        return hasil;
    }

    // ==========================================
    // STATISTIK UNTUK DASHBOARD
    // ==========================================

    public int getTotalPengiriman() {
        return daftarPengiriman.size();
    }

    public int getTotalDiproses() {
        int count = 0;
        for (Pengiriman p : daftarPengiriman) {
            if (p.getStatus() == StatusPengiriman.DIPROSES) {
                count++;
            }
        }
        return count;
    }

    public int getTotalDikirim() {
        int count = 0;
        for (Pengiriman p : daftarPengiriman) {
            if (p.getStatus() == StatusPengiriman.DIKIRIM) {
                count++;
            }
        }
        return count;
    }

    public int getTotalDiterima() {
        int count = 0;
        for (Pengiriman p : daftarPengiriman) {
            if (p.getStatus() == StatusPengiriman.DITERIMA) {
                count++;
            }
        }
        return count;
    }

    public int getTotalPelanggan() {
        return daftarPelanggan.size();
    }

    public int getTotalKurir() {
        return daftarKurir.size();
    }
}
