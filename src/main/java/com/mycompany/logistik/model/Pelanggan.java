package com.mycompany.logistik.model;

public class Pelanggan extends Pengguna {
    private static final long serialVersionUID = 1L;

    private String alamat;
    private String nomorTelepon;

    public Pelanggan(String nama, String alamat, String nomorTelepon) {
        super(nama);
        this.alamat = alamat;
        this.nomorTelepon = nomorTelepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }
}
