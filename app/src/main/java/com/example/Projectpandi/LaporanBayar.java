package com.example.Projectpandi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class LaporanBayar extends AppCompatActivity {
    private ImageButton btnMasukLaporan, btnAngsuranLaporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporanbayar);
        btnMasukLaporan =   findViewById(R.id.btnmasuklaporan);
        btnAngsuranLaporan  = findViewById(R.id.btnangsuranlaporan);

        btnMasukLaporan.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DataLaporanMasuk.class));
        });

        btnAngsuranLaporan.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DataLaporanAngsuran.class));
        });
    }
}