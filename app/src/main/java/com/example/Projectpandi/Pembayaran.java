package com.example.Projectpandi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class Pembayaran extends AppCompatActivity {
    private ImageButton btnPendaftaran, btnBulanan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayaran);
        btnPendaftaran = findViewById(R.id.btntransaksi);
        btnBulanan      = findViewById(R.id.btnbulanan);

        btnPendaftaran.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Bayar.class));
        });
        btnBulanan.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Bulanan.class));
        });

    }
}