package com.mycompany.logistik.service;

import com.mycompany.logistik.model.Pengiriman;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManajemenPengiriman {
    private final List<Pengiriman> daftarPengiriman;
    private final String namaFile;

    public ManajemenPengiriman(String namaFile) {
        this.namaFile = namaFile;
        this.daftarPengiriman = new ArrayList<>();
    }

    public void tambahPengiriman(Pengiriman pengiriman) {
        daftarPengiriman.add(pengiriman);
    }

    public List<Pengiriman> getDaftarPengiriman() {
        return daftarPengiriman;
    }

    public Pengiriman cariPengiriman(String id) {
        for (Pengiriman pengiriman : daftarPengiriman) {
            if (pengiriman.getIdPengiriman().equalsIgnoreCase(id)) {
                return pengiriman;
            }
        }
        return null;
    }

    public void simpanData() {
        try (ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(namaFile))) {
            output.writeObject(daftarPengiriman);
            System.out.println("Data tersimpan ke file " + namaFile);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void muatData() {
        File file = new File(namaFile);
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream input = new ObjectInputStream(
                new FileInputStream(file))) {
            List<Pengiriman> data = (List<Pengiriman>) input.readObject();
            daftarPengiriman.addAll(data);
            System.out.println("Data sebelumnya berhasil dimuat.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Data lama tidak dapat dimuat: " + e.getMessage());
        }
    }
}
