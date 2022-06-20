package com.example.Projectpandi.Model;

public class Pengajar {
    private String id, namapengajar, nippegawai, jabatan;

    public Pengajar(){
    }

    public Pengajar(String namapengajar, String nippegawai, String jabatan){
        this.namapengajar = namapengajar;
        this.nippegawai = nippegawai;
        this.jabatan = jabatan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamapengajar() {
        return namapengajar;
    }

    public void setNamapengajar(String namapengajar) {
        this.namapengajar = namapengajar;
    }

    public String getNippegawai() {
        return nippegawai;
    }

    public void setNippegawai(String nippegawai) {
        this.nippegawai = nippegawai;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}
