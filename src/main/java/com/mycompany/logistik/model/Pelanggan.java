package com.mycompany.logistik.model;

/**
 * Subclass Pelanggan mewarisi (Inheritance) dari Pengguna.
 * Menyimpan data pelanggan spesifik seperti alamat.
 */
public class Pelanggan extends Pengguna {
    private static final long serialVersionUID = 1L;

    private String alamat;

    public Pelanggan() {
        super();
    }

    public Pelanggan(String id, String nama, String nomorTelepon, String alamat) {
        super(id, nama, nomorTelepon);
        setAlamat(alamat);
    }

    public String getAlamat() {
        return alamat;
    }

    public final void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat pelanggan tidak boleh kosong.");
        }
        this.alamat = alamat.trim();
    }

    @Override
    public String getPeran() {
        return "Pelanggan";
    }

    @Override
    public String getInfo() {
        return String.format("%s | Alamat: %s", super.getInfo(), alamat);
    }
}

