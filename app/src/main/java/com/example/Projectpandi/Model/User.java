package com.example.Projectpandi.Model;

public class User {
    private String id, nama, namaortu, kelas, tanggal, nominal, uploadpendaftaran;

    public User(){

    }
    public User(String nama, String namaortu, String kelas, String tanggal, String nominal, String uploadpendaftaran){
        this.nama = nama;
        this.namaortu = namaortu;
        this.kelas = kelas;
        this.tanggal = tanggal;
        this.nominal = nominal;
        this.uploadpendaftaran = uploadpendaftaran;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNamaortu() {
        return namaortu;
    }

    public void setNamaortu(String namaortu) {
        this.namaortu = namaortu;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getUploadpendaftaran() {
        return uploadpendaftaran;
    }

    public void setUploadpendaftaran(String uploadpendaftaran) {
        this.uploadpendaftaran = uploadpendaftaran;
    }
}
