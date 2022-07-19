package com.example.Projectpandi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Beranda extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private TextView Judul;
    private ImageButton btnLogout, btnTransaksi, btnProgram, btnPengajar, btnLaporan;

    @SuppressLint("SetTextViewI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beranda);
        Judul   = findViewById(R.id.judul);
        btnLogout = findViewById(R.id.btnlogout);
        btnTransaksi = findViewById(R.id.btntransaksi);
        btnProgram = findViewById(R.id.btnprogram);
        btnLaporan = findViewById(R.id.btnlaporan);
        btnPengajar = findViewById(R.id.btnpengajar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btnPengajar.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Datapengajar.class));
            finish();
        });

        btnLaporan.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LaporanBayar.class));
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
            new AlertDialog.Builder(this)
                    .setIcon(R.mipmap.skbkarawang)
                    .setTitle(R.string.app_name)
                    .setMessage("Apakah kamu yakin ingin keluar?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        });
    }
}