package com.mycompany.logistik.model;

/**
 * Subclass Kurir mewarisi (Inheritance) dari Pengguna.
 * Menyimpan data kurir spesifik seperti tipe kendaraan atau area tugas.
 */
public class Kurir extends Pengguna {
    private static final long serialVersionUID = 1L;

    private String kendaraan;

    public Kurir() {
        super();
    }

    public Kurir(String id, String nama, String nomorTelepon, String kendaraan) {
        super(id, nama, nomorTelepon);
        setKendaraan(kendaraan);
    }

    public String getKendaraan() {
        return kendaraan;
    }

    public final void setKendaraan(String kendaraan) {
        if (kendaraan == null || kendaraan.trim().isEmpty()) {
            this.kendaraan = "Sepeda Motor"; // Default
        } else {
            this.kendaraan = kendaraan.trim();
        }
    }

    @Override
    public String getPeran() {
        return "Kurir";
    }

    @Override
    public String getInfo() {
        return String.format("%s | Kendaraan: %s", super.getInfo(), kendaraan);
    }
}

