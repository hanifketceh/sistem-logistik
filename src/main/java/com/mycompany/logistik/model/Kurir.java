package com.mycompany.logistik.model;

public class Kurir extends Pengguna {
    private static final long serialVersionUID = 1L;

    private String idKurir;

    public Kurir(String nama, String idKurir) {
        super(nama);
        this.idKurir = idKurir;
    }

    public String getIdKurir() {
        return idKurir;
    }

    public void setIdKurir(String idKurir) {
        this.idKurir = idKurir;
    }
}
