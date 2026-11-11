package com.mycompany.logistik.model;

import java.io.Serializable;

public abstract class Pengguna implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nama;

    public Pengguna(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
