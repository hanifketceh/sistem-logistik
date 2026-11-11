# Sistem Informasi Manajemen Logistik dan Monitoring Pengiriman Barang Ekspedisi

Project Java berbasis terminal untuk tugas PBO. 

## Menjalankan di NetBeans
1. Ekstrak ZIP.
2. Buka NetBeans.
3. Pilih **File > Open Project**.
4. Pilih folder `SistemLogistik` hasil ekstrak.
5. Jalankan `com.mycompany.logistik.Main`.

Project menggunakan Java 17 atau lebih baru.

## Fitur awal
- Menambahkan data pengiriman.
- Menampilkan seluruh data pengiriman.
- Memperbarui status pengiriman.
- Menyimpan dan memuat data menggunakan Object Serialization.

## Penerapan materi PBO
- Scanner dan control flow: input menu dan data melalui `Scanner`, `switch`, serta perulangan.
- Class dan object: model pengguna, pelanggan, kurir, barang, dan pengiriman.
- Encapsulation: atribut dibuat `private`, diakses melalui getter dan setter.
- Inheritance: `Pelanggan` dan `Kurir` mewarisi `Pengguna`.
- File I/O dan serialization: data pengiriman disimpan ke `data_pengiriman.ser`.

File data `data_pengiriman.ser` akan dibuat otomatis saat program menyimpan data.
