package com.mycompany.logistik.model;

import java.io.Serializable;

/**
 * Enum untuk status pengiriman barang logistik.
 */
public enum StatusPengiriman implements Serializable {
    DIPROSES("Diproses"),
    DIKIRIM("Dikirim"),
    DITERIMA("Diterima");

    private final String label;

    StatusPengiriman(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static StatusPengiriman fromLabel(String label) {
        if (label == null) return DIPROSES;
        for (StatusPengiriman s : values()) {
            if (s.label.equalsIgnoreCase(label.trim()) || s.name().equalsIgnoreCase(label.trim())) {
                return s;
            }
        }
        return DIPROSES;
    }

    @Override
    public String toString() {
        return label;
    }
}

