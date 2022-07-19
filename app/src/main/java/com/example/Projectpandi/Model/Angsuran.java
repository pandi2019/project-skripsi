package com.example.Projectpandi.Model;

public class Angsuran {
    private String id, nominalangsuran, jumlahangsuran, tanggalangsuran, angkatan1, deskripsi1, hasilangsuran;

    public Angsuran() {
    }

    public Angsuran(String nominalangsuran, String jumlahangsuran, String tanggalangsuran, String angkatan1, String deskripsi1, String hasilangsuran) {
        this.nominalangsuran = nominalangsuran;
        this.jumlahangsuran = jumlahangsuran;
        this.tanggalangsuran = tanggalangsuran;
        this.angkatan1 = angkatan1;
        this.deskripsi1 = deskripsi1;
        this.hasilangsuran = hasilangsuran;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNominalangsuran() {
        return nominalangsuran;
    }

    public void setNominalangsuran(String nominalangsuran) {
        this.nominalangsuran = nominalangsuran;
    }

    public String getJumlahangsuran() {
        return jumlahangsuran;
    }

    public void setJumlahangsuran(String jumlahangsuran) {
        this.jumlahangsuran = jumlahangsuran;
    }

    public String getTanggalangsuran() {
        return tanggalangsuran;
    }

    public void setTanggalangsuran(String tanggalangsuran) {
        this.tanggalangsuran = tanggalangsuran;
    }

    public String getAngkatan1() {
        return angkatan1;
    }

    public void setAngkatan1(String angkatan1) {
        this.angkatan1 = angkatan1;
    }

    public String getDeskripsi1() {
        return deskripsi1;
    }

    public void setDeskripsi1(String deskripsi1) {
        this.deskripsi1 = deskripsi1;
    }

    public String getHasilangsuran() {
        return hasilangsuran;
    }

    public void setHasilangsuran(String hasilangsuran) {
        this.hasilangsuran = hasilangsuran;
    }
}
