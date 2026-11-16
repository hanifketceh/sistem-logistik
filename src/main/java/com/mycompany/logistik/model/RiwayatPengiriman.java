package com.mycompany.logistik.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model untuk mencatat riwayat mutasi status pengiriman.
 */
public class RiwayatPengiriman implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private String nomorResi;
    private String statusSebelumnya;
    private String statusTerbaru;
    private String waktuPerubahan;
    private String keterangan;

    public RiwayatPengiriman() {
    }

    public RiwayatPengiriman(String nomorResi, String statusSebelumnya, String statusTerbaru, String keterangan) {
        this.nomorResi = nomorResi;
        this.statusSebelumnya = statusSebelumnya;
        this.statusTerbaru = statusTerbaru;
        this.waktuPerubahan = LocalDateTime.now().format(FORMATTER);
        this.keterangan = keterangan == null || keterangan.trim().isEmpty() ? "Perubahan status pengiriman" : keterangan.trim();
    }

    public RiwayatPengiriman(String nomorResi, String statusSebelumnya, String statusTerbaru, String waktuPerubahan, String keterangan) {
        this.nomorResi = nomorResi;
        this.statusSebelumnya = statusSebelumnya;
        this.statusTerbaru = statusTerbaru;
        this.waktuPerubahan = waktuPerubahan;
        this.keterangan = keterangan;
    }

    public String getNomorResi() {
        return nomorResi;
    }

    public void setNomorResi(String nomorResi) {
        this.nomorResi = nomorResi;
    }

    public String getStatusSebelumnya() {
        return statusSebelumnya;
    }

    public void setStatusSebelumnya(String statusSebelumnya) {
        this.statusSebelumnya = statusSebelumnya;
    }

    public String getStatusTerbaru() {
        return statusTerbaru;
    }

    public void setStatusTerbaru(String statusTerbaru) {
        this.statusTerbaru = statusTerbaru;
    }

    public String getWaktuPerubahan() {
        return waktuPerubahan;
    }

    public void setWaktuPerubahan(String waktuPerubahan) {
        this.waktuPerubahan = waktuPerubahan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    @Override
    public String toString() {
        return String.format("[%s] Resi: %s | %s -> %s | Ket: %s",
                waktuPerubahan, nomorResi, statusSebelumnya, statusTerbaru, keterangan);
    }
}

