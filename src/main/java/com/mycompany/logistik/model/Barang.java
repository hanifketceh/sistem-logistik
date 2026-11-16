package com.mycompany.logistik.model;

import java.io.Serializable;

public class Barang implements Serializable {
    private static final long serialVersionUID = 1L;

    private String namaBarang;
    private double berat;

    public Barang(String namaBarang, double berat) {
        this.namaBarang = namaBarang;
        this.berat = berat;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }
}
