package com.mycompany.logistik.controller;

import com.mycompany.logistik.model.*;
import com.mycompany.logistik.service.ManajemenPengiriman;
import com.mycompany.logistik.util.AlertHelper;
import com.mycompany.logistik.util.FileHelper;
import com.mycompany.logistik.util.ResiGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller utama untuk mengendalikan GUI Smart Economy Sistem Logistik.
 * Menghubungkan tampilan FXML dengan logika bisnis ManajemenPengiriman.
 */
public class MainController implements Initializable {

    private static final String DEFAULT_SAVE_FILE = "data_logistik.dat";
    private ManajemenPengiriman manajemen;

    // View Navigation Panes
    @FXML private Pane paneDashboard;
    @FXML private Pane panePelanggan;
    @FXML private Pane paneKurir;
    @FXML private Pane panePengiriman;
    @FXML private Pane paneMonitoring;
    @FXML private Pane paneRiwayat;
    @FXML private Pane paneFileIO;

    // Sidebar Buttons
    @FXML private Button btnNavDashboard;
    @FXML private Button btnNavPelanggan;
    @FXML private Button btnNavKurir;
    @FXML private Button btnNavPengiriman;
    @FXML private Button btnNavMonitoring;
    @FXML private Button btnNavRiwayat;
    @FXML private Button btnNavFileIO;

    // Header Label
    @FXML private Label lblPageTitle;
    @FXML private Label lblPageDesc;

    // ================= DASHBOARD COMPONENTS =================
    @FXML private Label lblTotalPengiriman;
    @FXML private Label lblTotalDiproses;
    @FXML private Label lblTotalDikirim;
    @FXML private Label lblTotalDiterima;
    @FXML private Label lblTotalPelanggan;
    @FXML private Label lblTotalKurir;
    @FXML private TableView<Pengiriman> tblDashboardPengiriman;
    @FXML private TableColumn<Pengiriman, String> colDashResi;
    @FXML private TableColumn<Pengiriman, String> colDashBarang;
    @FXML private TableColumn<Pengiriman, String> colDashPelanggan;
    @FXML private TableColumn<Pengiriman, String> colDashKurir;
    @FXML private TableColumn<Pengiriman, String> colDashStatus;

    // ================= PELANGGAN COMPONENTS =================
    @FXML private TextField txtPelangganId;
    @FXML private TextField txtPelangganNama;
    @FXML private TextField txtPelangganTelp;
    @FXML private TextArea txtPelangganAlamat;
    @FXML private TextField txtCariPelanggan;
    @FXML private TableView<Pelanggan> tblPelanggan;
    @FXML private TableColumn<Pelanggan, String> colPelangganId;
    @FXML private TableColumn<Pelanggan, String> colPelangganNama;
    @FXML private TableColumn<Pelanggan, String> colPelangganTelp;
    @FXML private TableColumn<Pelanggan, String> colPelangganAlamat;

    // ================= KURIR COMPONENTS =================
    @FXML private TextField txtKurirId;
    @FXML private TextField txtKurirNama;
    @FXML private TextField txtKurirTelp;
    @FXML private ComboBox<String> cmbKurirKendaraan;
    @FXML private TextField txtCariKurir;
    @FXML private TableView<Kurir> tblKurir;
    @FXML private TableColumn<Kurir, String> colKurirId;
    @FXML private TableColumn<Kurir, String> colKurirNama;
    @FXML private TableColumn<Kurir, String> colKurirTelp;
    @FXML private TableColumn<Kurir, String> colKurirKendaraan;

    // ================= PENGIRIMAN COMPONENTS =================
    @FXML private TextField txtResi;
    @FXML private ComboBox<Pelanggan> cmbPelanggan;
    @FXML private ComboBox<Kurir> cmbKurir;
    @FXML private TextField txtNamaBarang;
    @FXML private TextArea txtAlamatTujuan;
    @FXML private DatePicker dpTanggalPengiriman;
    @FXML private TextField txtCatatanPengiriman;
    @FXML private TextField txtCariPengiriman;
    @FXML private TableView<Pengiriman> tblPengiriman;
    @FXML private TableColumn<Pengiriman, String> colKirimResi;
    @FXML private TableColumn<Pengiriman, String> colKirimBarang;
    @FXML private TableColumn<Pengiriman, String> colKirimPelanggan;
    @FXML private TableColumn<Pengiriman, String> colKirimKurir;
    @FXML private TableColumn<Pengiriman, String> colKirimAlamat;
    @FXML private TableColumn<Pengiriman, String> colKirimTanggal;
    @FXML private TableColumn<Pengiriman, String> colKirimStatus;

    // ================= MONITORING COMPONENTS =================
    @FXML private ComboBox<String> cmbFilterMonitoring;
    @FXML private TableView<Pengiriman> tblMonitoring;
    @FXML private TableColumn<Pengiriman, String> colMonResi;
    @FXML private TableColumn<Pengiriman, String> colMonBarang;
    @FXML private TableColumn<Pengiriman, String> colMonPelanggan;
    @FXML private TableColumn<Pengiriman, String> colMonKurir;
    @FXML private TableColumn<Pengiriman, String> colMonStatus;
    @FXML private Label lblMonitorResiTerpilih;
    @FXML private Label lblMonitorStatusSaatIni;
    @FXML private ComboBox<StatusPengiriman> cmbStatusBaru;
    @FXML private TextField txtKeteranganUpdateStatus;

    // ================= RIWAYAT COMPONENTS =================
    @FXML private TextField txtCariRiwayat;
    @FXML private TableView<RiwayatPengiriman> tblRiwayat;
    @FXML private TableColumn<RiwayatPengiriman, String> colRiwWaktu;
    @FXML private TableColumn<RiwayatPengiriman, String> colRiwResi;
    @FXML private TableColumn<RiwayatPengiriman, String> colRiwSebelum;
    @FXML private TableColumn<RiwayatPengiriman, String> colRiwSesudah;
    @FXML private TableColumn<RiwayatPengiriman, String> colRiwKet;

    // ================= FILE I/O COMPONENTS =================
    @FXML private TextArea txtAreaFilePreview;
    @FXML private Label lblFileStatus;

    // Observable Lists for Tables
    private ObservableList<Pelanggan> masterPelanggan = FXCollections.observableArrayList();
    private ObservableList<Kurir> masterKurir = FXCollections.observableArrayList();
    private ObservableList<Pengiriman> masterPengiriman = FXCollections.observableArrayList();
    private ObservableList<RiwayatPengiriman> masterRiwayat = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inisialisasiDataService();
        setupTableColumns();
        setupComboBoxes();
        setupListeners();
        muatDataKeTabel();
        perbaruiDashboard();
        tampilkanNavigasi("dashboard");
    }

    private void inisialisasiDataService() {
        File fileSimpan = new File(DEFAULT_SAVE_FILE);
        if (fileSimpan.exists()) {
            try {
                manajemen = FileHelper.muatObjek(fileSimpan);
            } catch (Exception e) {
                manajemen = new ManajemenPengiriman();
                manajemen.inisialisasiDataAwal();
            }
        } else {
            manajemen = new ManajemenPengiriman();
            manajemen.inisialisasiDataAwal();
        }
    }

    private void setupTableColumns() {
        // Dashboard Table
        colDashResi.setCellValueFactory(new PropertyValueFactory<>("nomorResi"));
        colDashBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colDashPelanggan.setCellValueFactory(new PropertyValueFactory<>("namaPelanggan"));
        colDashKurir.setCellValueFactory(new PropertyValueFactory<>("namaKurir"));
        colDashStatus.setCellValueFactory(new PropertyValueFactory<>("statusLabel"));

        // Pelanggan Table
        colPelangganId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPelangganNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colPelangganTelp.setCellValueFactory(new PropertyValueFactory<>("nomorTelepon"));
        colPelangganAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));

        // Kurir Table
        colKurirId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colKurirNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colKurirTelp.setCellValueFactory(new PropertyValueFactory<>("nomorTelepon"));
        colKurirKendaraan.setCellValueFactory(new PropertyValueFactory<>("kendaraan"));

        // Pengiriman Table
        colKirimResi.setCellValueFactory(new PropertyValueFactory<>("nomorResi"));
        colKirimBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colKirimPelanggan.setCellValueFactory(new PropertyValueFactory<>("namaPelanggan"));
        colKirimKurir.setCellValueFactory(new PropertyValueFactory<>("namaKurir"));
        colKirimAlamat.setCellValueFactory(new PropertyValueFactory<>("alamatTujuan"));
        colKirimTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPengiriman"));
        colKirimStatus.setCellValueFactory(new PropertyValueFactory<>("statusLabel"));

        // Monitoring Table
        colMonResi.setCellValueFactory(new PropertyValueFactory<>("nomorResi"));
        colMonBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colMonPelanggan.setCellValueFactory(new PropertyValueFactory<>("namaPelanggan"));
        colMonKurir.setCellValueFactory(new PropertyValueFactory<>("namaKurir"));
        colMonStatus.setCellValueFactory(new PropertyValueFactory<>("statusLabel"));

        // Pasang Badge Renderer untuk Status
        applyStatusBadgeFactory(colDashStatus);
        applyStatusBadgeFactory(colKirimStatus);
        applyStatusBadgeFactory(colMonStatus);

        // Riwayat Table
        colRiwWaktu.setCellValueFactory(new PropertyValueFactory<>("waktuPerubahan"));
        colRiwResi.setCellValueFactory(new PropertyValueFactory<>("nomorResi"));
        colRiwSebelum.setCellValueFactory(new PropertyValueFactory<>("statusSebelumnya"));
        colRiwSesudah.setCellValueFactory(new PropertyValueFactory<>("statusTerbaru"));
        colRiwKet.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
        applyRiwayatBadgeFactory(colRiwSesudah);
    }

    private void applyStatusBadgeFactory(TableColumn<Pengiriman, String> column) {
        column.setCellFactory(col -> new TableCell<Pengiriman, String>() {
            private final Label badge = new Label();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    badge.setText(item);
                    badge.getStyleClass().removeAll("badge-diproses", "badge-dikirim", "badge-diterima");
                    if (item.equalsIgnoreCase("Diproses")) {
                        badge.getStyleClass().add("badge-diproses");
                    } else if (item.equalsIgnoreCase("Dikirim")) {
                        badge.getStyleClass().add("badge-dikirim");
                    } else if (item.equalsIgnoreCase("Diterima")) {
                        badge.getStyleClass().add("badge-diterima");
                    }
                    setGraphic(badge);
                    setText(null);
                }
            }
        });
    }

    private void applyRiwayatBadgeFactory(TableColumn<RiwayatPengiriman, String> column) {
        column.setCellFactory(col -> new TableCell<RiwayatPengiriman, String>() {
            private final Label badge = new Label();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    badge.setText(item);
                    badge.getStyleClass().removeAll("badge-diproses", "badge-dikirim", "badge-diterima");
                    if (item.equalsIgnoreCase("Diproses")) {
                        badge.getStyleClass().add("badge-diproses");
                    } else if (item.equalsIgnoreCase("Dikirim")) {
                        badge.getStyleClass().add("badge-dikirim");
                    } else if (item.equalsIgnoreCase("Diterima")) {
                        badge.getStyleClass().add("badge-diterima");
                    }
                    setGraphic(badge);
                    setText(null);
                }
            }
        });
    }

    private void setupComboBoxes() {
        cmbKurirKendaraan.setItems(FXCollections.observableArrayList(
                "Sepeda Motor", "Mobil Box", "Truk Engkel", "Blind Van", "Truk Fuso"
        ));
        cmbKurirKendaraan.getSelectionModel().selectFirst();

        cmbStatusBaru.setItems(FXCollections.observableArrayList(StatusPengiriman.values()));
        cmbStatusBaru.getSelectionModel().selectFirst();

        cmbFilterMonitoring.setItems(FXCollections.observableArrayList(
                "Semua Status", "Diproses", "Dikirim", "Diterima"
        ));
        cmbFilterMonitoring.getSelectionModel().selectFirst();

        dpTanggalPengiriman.setValue(LocalDate.now());
        generateNomorResiBaru();
    }

    private void setupListeners() {
        // Klik baris tabel pelanggan untuk isi form
        tblPelanggan.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtPelangganId.setText(newVal.getId());
                txtPelangganNama.setText(newVal.getNama());
                txtPelangganTelp.setText(newVal.getNomorTelepon());
                txtPelangganAlamat.setText(newVal.getAlamat());
            }
        });

        // Klik baris tabel kurir untuk isi form
        tblKurir.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtKurirId.setText(newVal.getId());
                txtKurirNama.setText(newVal.getNama());
                txtKurirTelp.setText(newVal.getNomorTelepon());
                cmbKurirKendaraan.setValue(newVal.getKendaraan());
            }
        });

        // Klik baris tabel pengiriman untuk isi form
        tblPengiriman.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtResi.setText(newVal.getNomorResi());
                txtNamaBarang.setText(newVal.getNamaBarang());
                txtAlamatTujuan.setText(newVal.getAlamatTujuan());
                txtCatatanPengiriman.setText(newVal.getCatatan());
                if (newVal.getTanggalPengiriman() != null) {
                    try {
                        dpTanggalPengiriman.setValue(LocalDate.parse(newVal.getTanggalPengiriman()));
                    } catch (Exception ignored) {}
                }
                cmbPelanggan.setValue(newVal.getPelanggan());
                cmbKurir.setValue(newVal.getKurir());
            }
        });

        // Pilihan pelanggan otomatis isi alamat tujuan di form pengiriman
        cmbPelanggan.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && (txtAlamatTujuan.getText() == null || txtAlamatTujuan.getText().trim().isEmpty())) {
                txtAlamatTujuan.setText(newVal.getAlamat());
            }
        });

        // Klik baris monitoring untuk update status
        tblMonitoring.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                lblMonitorResiTerpilih.setText(newVal.getNomorResi());
                lblMonitorStatusSaatIni.setText(newVal.getStatusLabel());
            }
        });

        // Filter monitoring
        cmbFilterMonitoring.valueProperty().addListener((obs, oldVal, newVal) -> filterTabelMonitoring(newVal));

        // Pencarian pelanggan real-time
        txtCariPelanggan.textProperty().addListener((obs, oldVal, newVal) -> {
            FilteredList<Pelanggan> filtered = new FilteredList<>(masterPelanggan, p -> {
                if (newVal == null || newVal.trim().isEmpty()) return true;
                String lower = newVal.toLowerCase();
                return p.getNama().toLowerCase().contains(lower)
                        || p.getId().toLowerCase().contains(lower)
                        || p.getNomorTelepon().contains(lower);
            });
            tblPelanggan.setItems(filtered);
        });

        // Pencarian kurir real-time
        txtCariKurir.textProperty().addListener((obs, oldVal, newVal) -> {
            FilteredList<Kurir> filtered = new FilteredList<>(masterKurir, k -> {
                if (newVal == null || newVal.trim().isEmpty()) return true;
                String lower = newVal.toLowerCase();
                return k.getNama().toLowerCase().contains(lower)
                        || k.getId().toLowerCase().contains(lower)
                        || k.getKendaraan().toLowerCase().contains(lower);
            });
            tblKurir.setItems(filtered);
        });

        // Pencarian pengiriman real-time
        txtCariPengiriman.textProperty().addListener((obs, oldVal, newVal) -> {
            FilteredList<Pengiriman> filtered = new FilteredList<>(masterPengiriman, p -> {
                if (newVal == null || newVal.trim().isEmpty()) return true;
                String lower = newVal.toLowerCase();
                return p.getNomorResi().toLowerCase().contains(lower)
                        || p.getNamaBarang().toLowerCase().contains(lower)
                        || p.getNamaPelanggan().toLowerCase().contains(lower)
                        || p.getNamaKurir().toLowerCase().contains(lower);
            });
            tblPengiriman.setItems(filtered);
        });

        // Pencarian riwayat real-time
        txtCariRiwayat.textProperty().addListener((obs, oldVal, newVal) -> {
            FilteredList<RiwayatPengiriman> filtered = new FilteredList<>(masterRiwayat, r -> {
                if (newVal == null || newVal.trim().isEmpty()) return true;
                String lower = newVal.toLowerCase();
                return r.getNomorResi().toLowerCase().contains(lower)
                        || r.getStatusTerbaru().toLowerCase().contains(lower)
                        || r.getKeterangan().toLowerCase().contains(lower);
            });
            tblRiwayat.setItems(filtered);
        });
    }

    private void muatDataKeTabel() {
        masterPelanggan.setAll(manajemen.getDaftarPelanggan());
        tblPelanggan.setItems(masterPelanggan);
        cmbPelanggan.setItems(masterPelanggan);

        masterKurir.setAll(manajemen.getDaftarKurir());
        tblKurir.setItems(masterKurir);
        cmbKurir.setItems(masterKurir);

        masterPengiriman.setAll(manajemen.getDaftarPengiriman());
        tblPengiriman.setItems(masterPengiriman);
        tblDashboardPengiriman.setItems(masterPengiriman);
        tblMonitoring.setItems(masterPengiriman);

        masterRiwayat.setAll(manajemen.getSeluruhRiwayat());
        tblRiwayat.setItems(masterRiwayat);
    }

    private void perbaruiDashboard() {
        lblTotalPengiriman.setText(String.valueOf(manajemen.getTotalPengiriman()));
        lblTotalDiproses.setText(String.valueOf(manajemen.getTotalDiproses()));
        lblTotalDikirim.setText(String.valueOf(manajemen.getTotalDikirim()));
        lblTotalDiterima.setText(String.valueOf(manajemen.getTotalDiterima()));
        lblTotalPelanggan.setText(String.valueOf(manajemen.getTotalPelanggan()));
        lblTotalKurir.setText(String.valueOf(manajemen.getTotalKurir()));
    }

    private void simpanOtomatis() {
        try {
            FileHelper.simpanObjek(new File(DEFAULT_SAVE_FILE), manajemen);
        } catch (Exception ignored) {}
    }

    // ================= NAVIGATION HANDLERS =================
    @FXML
    public void navKeDashboard() { tampilkanNavigasi("dashboard"); }

    @FXML
    public void navKePelanggan() { tampilkanNavigasi("pelanggan"); }

    @FXML
    public void navKeKurir() { tampilkanNavigasi("kurir"); }

    @FXML
    public void navKePengiriman() { tampilkanNavigasi("pengiriman"); }

    @FXML
    public void navKeMonitoring() { tampilkanNavigasi("monitoring"); }

    @FXML
    public void navKeRiwayat() { tampilkanNavigasi("riwayat"); }

    @FXML
    public void navKeFileIO() { tampilkanNavigasi("fileio"); }

    private void resetNavButtons() {
        btnNavDashboard.getStyleClass().remove("nav-button-active");
        btnNavPelanggan.getStyleClass().remove("nav-button-active");
        btnNavKurir.getStyleClass().remove("nav-button-active");
        btnNavPengiriman.getStyleClass().remove("nav-button-active");
        btnNavMonitoring.getStyleClass().remove("nav-button-active");
        btnNavRiwayat.getStyleClass().remove("nav-button-active");
        btnNavFileIO.getStyleClass().remove("nav-button-active");
    }

    private void tampilkanNavigasi(String target) {
        paneDashboard.setVisible(false);
        panePelanggan.setVisible(false);
        paneKurir.setVisible(false);
        panePengiriman.setVisible(false);
        paneMonitoring.setVisible(false);
        paneRiwayat.setVisible(false);
        paneFileIO.setVisible(false);

        resetNavButtons();

        switch (target) {
            case "dashboard":
                paneDashboard.setVisible(true);
                btnNavDashboard.getStyleClass().add("nav-button-active");
                lblPageTitle.setText("Dashboard Ringkasan Ekspedisi");
                lblPageDesc.setText("Pantau statistik keseluruhan pengiriman barang, pelanggan, dan kurir");
                perbaruiDashboard();
                break;
            case "pelanggan":
                panePelanggan.setVisible(true);
                btnNavPelanggan.getStyleClass().add("nav-button-active");
                lblPageTitle.setText("Manajemen Pelanggan");
                lblPageDesc.setText("Kelola data pelanggan pengirim dan penerima paket ekspedisi");
                break;
            case "kurir":
                paneKurir.setVisible(true);
                btnNavKurir.getStyleClass().add("nav-button-active");
                lblPageTitle.setText("Manajemen Kurir");
                lblPageDesc.setText("Kelola armada kurir ekspedisi beserta kendaraan operasional");
                break;
            case "pengiriman":
                panePengiriman.setVisible(true);
                btnNavPengiriman.getStyleClass().add("nav-button-active");
                lblPageTitle.setText("Manajemen Pengiriman Barang");
                lblPageDesc.setText("Penerbitan resi otomatis dan pengelolaan pesanan pengiriman");
                break;
            case "monitoring":
                paneMonitoring.setVisible(true);
                btnNavMonitoring.getStyleClass().add("nav-button-active");
                lblPageTitle.setText("Monitoring Status Pengiriman");
                lblPageDesc.setText("Lacak dan mutasi status barang (Diproses, Dikirim, Diterima)");
                break;
            case "riwayat":
                paneRiwayat.setVisible(true);
                btnNavRiwayat.getStyleClass().add("nav-button-active");
                lblPageTitle.setText("Riwayat Mutasi Pengiriman");
                lblPageDesc.setText("Log lengkap histori perubahan status pengiriman beserta waktu dan catatan");
                break;
            case "fileio":
                paneFileIO.setVisible(true);
                btnNavFileIO.getStyleClass().add("nav-button-active");
                lblPageTitle.setText("File I/O & Backup Data");
                lblPageDesc.setText("Ekspor laporan teks, serialisasi objek, dan pemulihan data");
                break;
        }
    }

    // ================= PELANGGAN ACTIONS =================
    @FXML
    public void tambahPelanggan() {
        try {
            String id = txtPelangganId.getText();
            String nama = txtPelangganNama.getText();
            String telp = txtPelangganTelp.getText();
            String alamat = txtPelangganAlamat.getText();

            Pelanggan p = new Pelanggan(id, nama, telp, alamat);
            manajemen.tambahPelanggan(p);
            masterPelanggan.add(p);
            resetFormPelanggan();
            perbaruiDashboard();
            simpanOtomatis();
            AlertHelper.tampilkanInfo("Sukses", "Data pelanggan berhasil ditambahkan!");
        } catch (Exception e) {
            AlertHelper.tampilkanError("Gagal Tambah Pelanggan", e.getMessage());
        }
    }

    @FXML
    public void editPelanggan() {
        try {
            String id = txtPelangganId.getText();
            String nama = txtPelangganNama.getText();
            String telp = txtPelangganTelp.getText();
            String alamat = txtPelangganAlamat.getText();

            Pelanggan p = new Pelanggan(id, nama, telp, alamat);
            manajemen.editPelanggan(p);
            muatDataKeTabel();
            perbaruiDashboard();
            simpanOtomatis();
            AlertHelper.tampilkanInfo("Sukses", "Data pelanggan berhasil diperbarui!");
        } catch (Exception e) {
            AlertHelper.tampilkanError("Gagal Edit Pelanggan", e.getMessage());
        }
    }

    @FXML
    public void hapusPelanggan() {
        Pelanggan p = tblPelanggan.getSelectionModel().getSelectedItem();
        if (p == null) {
            AlertHelper.tampilkanPeringatan("Peringatan", "Pilih pelanggan dari tabel yang ingin dihapus.");
            return;
        }

        if (AlertHelper.konfirmasi("Konfirmasi Hapus", "Yakin ingin menghapus pelanggan: " + p.getNama() + " (" + p.getId() + ")?")) {
            manajemen.hapusPelanggan(p.getId());
            masterPelanggan.remove(p);
            resetFormPelanggan();
            perbaruiDashboard();
            simpanOtomatis();
            AlertHelper.tampilkanInfo("Sukses", "Pelanggan berhasil dihapus.");
        }
    }

    @FXML
    public void resetFormPelanggan() {
        txtPelangganId.clear();
        txtPelangganNama.clear();
        txtPelangganTelp.clear();
        txtPelangganAlamat.clear();
        tblPelanggan.getSelectionModel().clearSelection();
    }

    // ================= KURIR ACTIONS =================
    @FXML
    public void tambahKurir() {
        try {
            String id = txtKurirId.getText();
            String nama = txtKurirNama.getText();
            String telp = txtKurirTelp.getText();
            String kendaraan = cmbKurirKendaraan.getValue();

            Kurir k = new Kurir(id, nama, telp, kendaraan);
            manajemen.tambahKurir(k);
            masterKurir.add(k);
            resetFormKurir();
            perbaruiDashboard();
            simpanOtomatis();
            AlertHelper.tampilkanInfo("Sukses", "Data kurir berhasil ditambahkan!");
        } catch (Exception e) {
            AlertHelper.tampilkanError("Gagal Tambah Kurir", e.getMessage());
        }
    }

    @FXML
    public void editKurir() {
        try {
            String id = txtKurirId.getText();
            String nama = txtKurirNama.getText();
            String telp = txtKurirTelp.getText();
            String kendaraan = cmbKurirKendaraan.getValue();

            Kurir k = new Kurir(id, nama, telp, kendaraan);
            manajemen.editKurir(k);
            muatDataKeTabel();
            perbaruiDashboard();
            simpanOtomatis();
            AlertHelper.tampilkanInfo("Sukses", "Data kurir berhasil diperbarui!");
        } catch (Exception e) {
            AlertHelper.tampilkanError("Gagal Edit Kurir", e.getMessage());
        }
    }

    @FXML
    public void hapusKurir() {
        Kurir k = tblKurir.getSelectionModel().getSelectedItem();
        if (k == null) {
            AlertHelper.tampilkanPeringatan("Peringatan", "Pilih kurir dari tabel yang ingin dihapus.");
            return;
        }

        if (AlertHelper.konfirmasi("Konfirmasi Hapus", "Yakin ingin menghapus kurir: " + k.getNama() + " (" + k.getId() + ")?")) {
            manajemen.hapusKurir(k.getId());
            masterKurir.remove(k);
            resetFormKurir();
            perbaruiDashboard();
            simpanOtomatis();
            AlertHelper.tampilkanInfo("Sukses", "Kurir berhasil dihapus.");
        }
    }

    @FXML
    public void resetFormKurir() {
        txtKurirId.clear();
        txtKurirNama.clear();
        txtKurirTelp.clear();
        cmbKurirKendaraan.getSelectionModel().selectFirst();
        tblKurir.getSelectionModel().clearSelection();
    }

    // ================= PENGIRIMAN ACTIONS =================
    @FXML
    public void generateNomorResiBaru() {
        txtResi.setText(ResiGenerator.generateResi());
    }

    @FXML
    public void tambahPengiriman() {
        try {
            String resi = txtResi.getText();
            Pelanggan pelanggan = cmbPelanggan.getValue();
            Kurir kurir = cmbKurir.getValue();
            String namaBarang = txtNamaBarang.getText();
            String alamat = txtAlamatTujuan.getText();
            LocalDate tgl = dpTanggalPengiriman.getValue();
            String catatan = txtCatatanPengiriman.getText();

            if (resi == null || resi.trim().isEmpty()) {
                generateNomorResiBaru();
                resi = txtResi.getText();
            }
            if (tgl == null) {
                throw new IllegalArgumentException("Tanggal pengiriman wajib dipilih.");
            }

            Pengiriman p = new Pengiriman(resi, pelanggan, kurir, namaBarang, alamat, tgl.toString(), catatan);
            manajemen.tambahPengiriman(p);

            muatDataKeTabel();
            perbaruiDashboard();
            resetFormPengiriman();
            simpanOtomatis();
            AlertHelper.tampilkanInfo("Sukses", "Pengiriman berhasil dibuat dengan No Resi: " + resi);
        } catch (Exception e) {
            AlertHelper.tampilkanError("Gagal Buat Pengiriman", e.getMessage());
        }
    }

    @FXML
    public void editPengiriman() {
        try {
            String resi = txtResi.getText();
            Pelanggan pelanggan = cmbPelanggan.getValue();
            Kurir kurir = cmbKurir.getValue();
            String namaBarang = txtNamaBarang.getText();
            String alamat = txtAlamatTujuan.getText();
            LocalDate tgl = dpTanggalPengiriman.getValue();
            String catatan = txtCatatanPengiriman.getText();

            Pengiriman pLama = manajemen.cariPengiriman(resi);
            if (pLama == null) {
                throw new IllegalArgumentException("Pengiriman dengan resi " + resi + " tidak ditemukan.");
            }

            pLama.setPelanggan(pelanggan);
            pLama.setKurir(kurir);
            pLama.setNamaBarang(namaBarang);
            pLama.setAlamatTujuan(alamat);
            if (tgl != null) {
                pLama.setTanggalPengiriman(tgl.toString());
            }
            pLama.setCatatan(catatan);

            manajemen.editPengiriman(pLama);
            muatDataKeTabel();
            perbaruiDashboard();
            simpanOtomatis();
            AlertHelper.tampilkanInfo("Sukses", "Data pengiriman berhasil diperbarui!");
        } catch (Exception e) {
            AlertHelper.tampilkanError("Gagal Edit Pengiriman", e.getMessage());
        }
    }

    @FXML
    public void hapusPengiriman() {
        Pengiriman p = tblPengiriman.getSelectionModel().getSelectedItem();
        if (p == null) {
            AlertHelper.tampilkanPeringatan("Peringatan", "Pilih data pengiriman dari tabel yang ingin dihapus.");
            return;
        }

        if (AlertHelper.konfirmasi("Konfirmasi Hapus", "Yakin ingin menghapus pengiriman resi: " + p.getNomorResi() + "?")) {
            manajemen.hapusPengiriman(p.getNomorResi());
            muatDataKeTabel();
            perbaruiDashboard();
            resetFormPengiriman();
            simpanOtomatis();
            AlertHelper.tampilkanInfo("Sukses", "Pengiriman berhasil dihapus.");
        }
    }

    @FXML
    public void resetFormPengiriman() {
        generateNomorResiBaru();
        txtNamaBarang.clear();
        txtAlamatTujuan.clear();
        txtCatatanPengiriman.clear();
        dpTanggalPengiriman.setValue(LocalDate.now());
        cmbPelanggan.getSelectionModel().clearSelection();
        cmbKurir.getSelectionModel().clearSelection();
        tblPengiriman.getSelectionModel().clearSelection();
    }

    // ================= MONITORING ACTIONS =================
    @FXML
    public void perbaruiStatusPengiriman() {
        try {
            String resi = lblMonitorResiTerpilih.getText();
            if (resi == null || resi.equals("-") || resi.trim().isEmpty()) {
                AlertHelper.tampilkanPeringatan("Peringatan", "Pilih salah satu baris pengiriman dari tabel monitoring terlebih dahulu.");
                return;
            }

            StatusPengiriman statusBaru = cmbStatusBaru.getValue();
            String keterangan = txtKeteranganUpdateStatus.getText();

            RiwayatPengiriman log = manajemen.updateStatusPengiriman(resi, statusBaru, keterangan);
            muatDataKeTabel();
            perbaruiDashboard();
            lblMonitorStatusSaatIni.setText(statusBaru.getLabel());
            txtKeteranganUpdateStatus.clear();
            simpanOtomatis();
            AlertHelper.tampilkanInfo("Status Diperbarui", "Status resi " + resi + " kini: " + statusBaru.getLabel());
        } catch (Exception e) {
            AlertHelper.tampilkanError("Gagal Perbarui Status", e.getMessage());
        }
    }

    private void filterTabelMonitoring(String filter) {
        if (filter == null || filter.equals("Semua Status")) {
            tblMonitoring.setItems(masterPengiriman);
        } else {
            FilteredList<Pengiriman> filtered = new FilteredList<>(masterPengiriman, p -> p.getStatusLabel().equalsIgnoreCase(filter));
            tblMonitoring.setItems(filtered);
        }
    }

    // ================= FILE I/O ACTIONS =================
    @FXML
    public void eksporLaporanTeks() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Simpan Laporan Logistik Teks");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File Teks (*.txt)", "*.txt"));
        fileChooser.setInitialFileName("laporan_logistik.txt");

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                FileHelper.eksporLaporanTeks(file, manajemen);
                lblFileStatus.setText("Laporan berhasil diekspor ke: " + file.getName());
                // Tampilkan preview isi file
                txtAreaFilePreview.setText(FileHelper.bacaFileTeks(file));
                AlertHelper.tampilkanInfo("Sukses Ekspor", "Laporan berhasil disimpan ke: " + file.getAbsolutePath());
            } catch (IOException e) {
                AlertHelper.tampilkanError("Error Ekspor", "Gagal menyimpan file: " + e.getMessage());
            }
        }
    }

    @FXML
    public void bacaLaporanTeks() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buka File Laporan Teks");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File Teks (*.txt)", "*.txt"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                String konten = FileHelper.bacaFileTeks(file);
                txtAreaFilePreview.setText(konten);
                lblFileStatus.setText("File dimuat: " + file.getName());
            } catch (IOException e) {
                AlertHelper.tampilkanError("Error Membaca File", "Gagal membaca file: " + e.getMessage());
            }
        }
    }

    @FXML
    public void simpanSerialization() {
        File file = new File(DEFAULT_SAVE_FILE);
        try {
            FileHelper.simpanObjek(file, manajemen);
            lblFileStatus.setText("Status: Data objek berhasil diserialisasi ke " + file.getName());
            AlertHelper.tampilkanInfo("Serialization Berhasil", "Data berhasil disimpan ke file biner objek: " + file.getAbsolutePath());
        } catch (IOException e) {
            AlertHelper.tampilkanError("Error Serialization", "Gagal menyimpan objek: " + e.getMessage());
        }
    }

    @FXML
    public void muatDeserialization() {
        File file = new File(DEFAULT_SAVE_FILE);
        if (!file.exists()) {
            AlertHelper.tampilkanPeringatan("File Tidak Ada", "File serialisasi '" + DEFAULT_SAVE_FILE + "' belum pernah dibuat.");
            return;
        }

        try {
            manajemen = FileHelper.muatObjek(file);
            muatDataKeTabel();
            perbaruiDashboard();
            lblFileStatus.setText("Status: Data objek berhasil dipulihkan dari " + file.getName());
            AlertHelper.tampilkanInfo("Deserialization Berhasil", "Data berhasil dipulihkan dari file biner: " + file.getAbsolutePath());
        } catch (Exception e) {
            AlertHelper.tampilkanError("Error Deserialization", "Gagal memulihkan objek: " + e.getMessage());
        }
    }
}

