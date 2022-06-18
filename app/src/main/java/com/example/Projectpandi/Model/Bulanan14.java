package com.example.Projectpandi.Model;

public class Bulanan14 {
    private String id, namabln, nisbln, kelasbln, tanggalbln, bulanan;

    public Bulanan14() {

    }

    public Bulanan14(String namabln, String nisbln, String kelasbln, String tanggalbln, String bulanan) {
        this.namabln = namabln;
        this.nisbln = nisbln;
        this.kelasbln = kelasbln;
        this.tanggalbln = tanggalbln;
        this.bulanan = bulanan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamabln() {
        return namabln;
    }

    public void setNamabln(String namabln) {
        this.namabln = namabln;
    }

    public String getNisbln() {
        return nisbln;
    }

    public void setNisbln(String nisbln) {
        this.nisbln = nisbln;
    }

    public String getKelasbln() {
        return kelasbln;
    }

    public void setKelasbln(String kelasbln) {
        this.kelasbln = kelasbln;
    }

    public String getTanggalbln() {
        return tanggalbln;
    }

    public void setTanggalbln(String tanggalbln) {
        this.tanggalbln = tanggalbln;
    }

    public String getBulanan() {
        return bulanan;
    }

    public void setBulanan(String bulanan) {
        this.bulanan = bulanan;
    }
}