package com.example.Projectpandi.Model;

public class LaporanM {
        private String id, nominalpembayaran, jumlahpembayar, tanggallaporan, angkatan, deskripsi, hasil;

        public LaporanM(){
        }

        public LaporanM(String nominalpembayaran, String jumlahpembayar, String tanggallaporan, String angkatan, String deskripsi, String hasil){
            this.nominalpembayaran = nominalpembayaran;
            this.jumlahpembayar = jumlahpembayar;
            this.tanggallaporan= tanggallaporan;
            this.angkatan= angkatan;
            this.deskripsi= deskripsi;
            this.hasil = hasil;
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNominalpembayaran() {
        return nominalpembayaran;
    }

    public void setNominalpembayaran(String nominalpembayaran) {
        this.nominalpembayaran = nominalpembayaran;
    }

    public String getJumlahpembayar() {
        return jumlahpembayar;
    }

    public void setJumlahpembayar(String jumlahpembayar) {
        this.jumlahpembayar = jumlahpembayar;
    }

    public String getTanggallaporan() {
        return tanggallaporan;
    }

    public void setTanggallaporan(String tanggallaporan) {
        this.tanggallaporan = tanggallaporan;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHasil() {
        return hasil;
    }

    public void setHasil(String hasil) {
        this.hasil = hasil;
    }
}

