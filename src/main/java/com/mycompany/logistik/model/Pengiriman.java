package com.mycompany.logistik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model entitas Pengiriman barang.
 * Menyimpan relasi ke Pelanggan, Kurir, serta daftar RiwayatPengiriman.
 */
public class Pengiriman implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nomorResi;
    private Pelanggan pelanggan;
    private Kurir kurir;
    private String namaBarang;
    private String alamatTujuan;
    private String tanggalPengiriman;
    private StatusPengiriman status;
    private String catatan;
    private ArrayList<RiwayatPengiriman> riwayatList;

    public Pengiriman() {
        this.riwayatList = new ArrayList<>();
        this.status = StatusPengiriman.DIPROSES;
    }

    public Pengiriman(String nomorResi, Pelanggan pelanggan, Kurir kurir, String namaBarang,
                      String alamatTujuan, String tanggalPengiriman, String catatan) {
        setNomorResi(nomorResi);
        setPelanggan(pelanggan);
        setKurir(kurir);
        setNamaBarang(namaBarang);
        setAlamatTujuan(alamatTujuan);
        setTanggalPengiriman(tanggalPengiriman);
        this.catatan = catatan != null ? catatan.trim() : "";
        this.status = StatusPengiriman.DIPROSES;
        this.riwayatList = new ArrayList<>();
        
        // Catat riwayat awal dibuat
        RiwayatPengiriman rAwal = new RiwayatPengiriman(this.nomorResi, "-", StatusPengiriman.DIPROSES.getLabel(), "Pengiriman dibuat");
        this.riwayatList.add(rAwal);
    }

    public String getNomorResi() {
        return nomorResi;
    }

    public final void setNomorResi(String nomorResi) {
        if (nomorResi == null || nomorResi.trim().isEmpty()) {
            throw new IllegalArgumentException("Nomor resi tidak boleh kosong.");
        }
        this.nomorResi = nomorResi.trim();
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public final void setPelanggan(Pelanggan pelanggan) {
        if (pelanggan == null) {
            throw new IllegalArgumentException("Pelanggan harus dipilih.");
        }
        this.pelanggan = pelanggan;
    }

    public Kurir getKurir() {
        return kurir;
    }

    public final void setKurir(Kurir kurir) {
        if (kurir == null) {
            throw new IllegalArgumentException("Kurir harus dipilih.");
        }
        this.kurir = kurir;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public final void setNamaBarang(String namaBarang) {
        if (namaBarang == null || namaBarang.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama barang tidak boleh kosong.");
        }
        this.namaBarang = namaBarang.trim();
    }

    public String getAlamatTujuan() {
        return alamatTujuan;
    }

    public final void setAlamatTujuan(String alamatTujuan) {
        if (alamatTujuan == null || alamatTujuan.trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat tujuan tidak boleh kosong.");
        }
        this.alamatTujuan = alamatTujuan.trim();
    }

    public String getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public final void setTanggalPengiriman(String tanggalPengiriman) {
        if (tanggalPengiriman == null || tanggalPengiriman.trim().isEmpty()) {
            throw new IllegalArgumentException("Tanggal pengiriman tidak boleh kosong.");
        }
        this.tanggalPengiriman = tanggalPengiriman.trim();
    }

    public StatusPengiriman getStatus() {
        return status;
    }

    public void setStatus(StatusPengiriman status) {
        if (status == null) {
            this.status = StatusPengiriman.DIPROSES;
        } else {
            this.status = status;
        }
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan == null ? "" : catatan.trim();
    }

    public List<RiwayatPengiriman> getRiwayatList() {
        if (riwayatList == null) {
            riwayatList = new ArrayList<>();
        }
        return riwayatList;
    }

    public void setRiwayatList(List<RiwayatPengiriman> riwayatList) {
        if (riwayatList instanceof ArrayList) {
            this.riwayatList = (ArrayList<RiwayatPengiriman>) riwayatList;
        } else if (riwayatList != null) {
            this.riwayatList = new ArrayList<>(riwayatList);
        } else {
            this.riwayatList = new ArrayList<>();
        }
    }

    /**
     * Memperbarui status dan mencatat riwayat perubahannya.
     */
    public RiwayatPengiriman perbaruiStatus(StatusPengiriman statusBaru, String keterangan) {
        String statusLamaStr = this.status != null ? this.status.getLabel() : "-";
        this.status = statusBaru;
        RiwayatPengiriman riwayat = new RiwayatPengiriman(this.nomorResi, statusLamaStr, statusBaru.getLabel(), keterangan);
        getRiwayatList().add(riwayat);
        return riwayat;
    }

    // Property helper untuk TableView JavaFX
    public String getNamaPelanggan() {
        return pelanggan != null ? pelanggan.getNama() : "-";
    }

    public String getNamaKurir() {
        return kurir != null ? kurir.getNama() : "-";
    }

    public String getStatusLabel() {
        return status != null ? status.getLabel() : "-";
    }

    @Override
    public String toString() {
        return String.format("[%s] %s -> %s (%s)", nomorResi, namaBarang, alamatTujuan, getStatusLabel());
    }
}

