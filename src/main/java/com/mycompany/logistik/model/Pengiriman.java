package com.mycompany.logistik.model;

import java.io.Serializable;

public class Pengiriman implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idPengiriman;
    private Pelanggan pelanggan;
    private Kurir kurir;
    private Barang barang;
    private String status;

    public Pengiriman(String idPengiriman, Pelanggan pelanggan,
                      Kurir kurir, Barang barang) {
        this.idPengiriman = idPengiriman;
        this.pelanggan = pelanggan;
        this.kurir = kurir;
        this.barang = barang;
        this.status = "Diproses";
    }

    public String getIdPengiriman() {
        return idPengiriman;
    }

    public void setIdPengiriman(String idPengiriman) {
        this.idPengiriman = idPengiriman;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public Kurir getKurir() {
        return kurir;
    }

    public void setKurir(Kurir kurir) {
        this.kurir = kurir;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID Pengiriman : " + idPengiriman
                + "\nPelanggan     : " + pelanggan.getNama()
                + "\nAlamat        : " + pelanggan.getAlamat()
                + "\nTelepon       : " + pelanggan.getNomorTelepon()
                + "\nKurir         : " + kurir.getNama()
                + "\nID Kurir      : " + kurir.getIdKurir()
                + "\nBarang        : " + barang.getNamaBarang()
                + "\nBerat         : " + barang.getBerat() + " kg"
                + "\nStatus        : " + status;
    }
}
