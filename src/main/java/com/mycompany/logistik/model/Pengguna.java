package com.mycompany.logistik.model;

import java.io.Serializable;

/**
 * Superclass abstrak yang merepresentasikan Pengguna umum dalam sistem logistik.
 * Mengimplementasikan Serializable untuk mendukung persistensi objek.
 * Menerapkan Encapsulation dengan field private dan getter/setter tervalidasi.
 */
public abstract class Pengguna implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nama;
    private String nomorTelepon;

    public Pengguna() {
    }

    public Pengguna(String id, String nama, String nomorTelepon) {
        setId(id);
        setNama(nama);
        setNomorTelepon(nomorTelepon);
    }

    public String getId() {
        return id;
    }

    public final void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID tidak boleh kosong.");
        }
        this.id = id.trim();
    }

    public String getNama() {
        return nama;
    }

    public final void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public final void setNomorTelepon(String nomorTelepon) {
        if (nomorTelepon == null || nomorTelepon.trim().isEmpty()) {
            throw new IllegalArgumentException("Nomor telepon tidak boleh kosong.");
        }
        String cleanPhone = nomorTelepon.trim().replaceAll("[\\s\\-\\+]", "");
        if (!cleanPhone.matches("\\d{8,15}")) {
            throw new IllegalArgumentException("Nomor telepon harus berupa angka antara 8 hingga 15 digit.");
        }
        this.nomorTelepon = nomorTelepon.trim();
    }

    /**
     * Method abstrak untuk polymorphism peran pengguna.
     * @return Peran pengguna (misal: Pelanggan, Kurir)
     */
    public abstract String getPeran();

    /**
     * Method untuk menampilkan ringkasan informasi pengguna.
     * Dapat dioverride oleh subclass (Polymorphism).
     * @return Informasi pengguna
     */
    public String getInfo() {
        return String.format("[%s] ID: %s | Nama: %s | Telp: %s", getPeran(), id, nama, nomorTelepon);
    }

    @Override
    public String toString() {
        return nama + " (" + id + ")";
    }
}

