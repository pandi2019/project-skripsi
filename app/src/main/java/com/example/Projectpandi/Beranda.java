package com.example.Projectpandi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Beranda extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private TextView Judul;
    private ImageButton btnLogout, btnTransaksi, btnJadwal, btnProgram, btnPengajar, btnLokasi;

    @SuppressLint("SetTextViewI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beranda);
        Judul   = findViewById(R.id.judul);
        btnLogout = findViewById(R.id.btnlogout);
        btnTransaksi = findViewById(R.id.btntransaksi);
        btnProgram = findViewById(R.id.btnprogram);
        btnPengajar = findViewById(R.id.btnpengajar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btnPengajar.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Datapengajar.class));
            finish();
        });

        btnProgram.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Daftarbelajar.class));
            finish();
        });

        btnTransaksi.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Pembayaran.class));
            finish();
        });

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        });
    }
}