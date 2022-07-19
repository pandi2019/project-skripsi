package com.example.Projectpandi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LaporanMasuk extends AppCompatActivity {
    private EditText EditNominalpembayaran, EditJumlahpembayar, EditTanggalLaporan, EditAngkatan, EditDeskripsi;
    private TextView Hasilpembayaran;
    private Button btnSimpanmasuk, btnTotal, btnReset;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporanmasuk);
        EditNominalpembayaran = findViewById(R.id.nominalpembayaran);
        EditJumlahpembayar = findViewById(R.id.jumlahpembayar);
        EditTanggalLaporan = findViewById(R.id.tanggallaporan);
        EditAngkatan    = findViewById(R.id.angkatan);
        EditDeskripsi   = findViewById(R.id.deskripsi);
        Hasilpembayaran = findViewById(R.id.hasil);
        btnTotal = findViewById(R.id.totalpembayaran);
        btnSimpanmasuk = findViewById(R.id.btnsimpanmasuk);
        btnReset = findViewById(R.id.btnreset);

        progressDialog = new ProgressDialog(LaporanMasuk.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Simpan...");

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        EditTanggalLaporan.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    LaporanMasuk.this, (view, year1, month1, dayOfMonth) -> {
                month1 = month1 + 1;
                String date = day + "/" + month1 + "/" + year1;
                EditTanggalLaporan.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double nominalpembayaran1, jumlahpembayar1, hasil;
                nominalpembayaran1 = Double.valueOf(EditNominalpembayaran.getText().toString().trim());
                jumlahpembayar1 = Double.valueOf(EditJumlahpembayar.getText().toString().trim());
                hasil = nominalpembayaran1 * jumlahpembayar1;
                String hasil1 = String.valueOf(hasil);
                Hasilpembayaran.setText(hasil1);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNominalpembayaran.setText("");
                EditJumlahpembayar.setText("");
                EditTanggalLaporan.setText("");
                EditAngkatan.setText("");
                EditDeskripsi.setText("");
                Hasilpembayaran.setText("");
            }
        });

        btnSimpanmasuk.setOnClickListener(v -> {
            if (EditNominalpembayaran.getText().length() > 0 && EditJumlahpembayar.getText().length() > 0 && EditTanggalLaporan.getText().length() > 0 && EditAngkatan.getText().length() > 0 && EditDeskripsi.getText().length() > 0 && Hasilpembayaran.getText().length() > 0) {
                saveDataLaporan(EditNominalpembayaran.getText().toString(), EditJumlahpembayar.getText().toString(), EditTanggalLaporan.getText().toString(), EditAngkatan.getText().toString(), EditDeskripsi.getText().toString(), Hasilpembayaran.getText().toString());

            } else {
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            EditNominalpembayaran.setText(intent.getStringExtra("nominalpembayaran"));
            EditJumlahpembayar.setText(intent.getStringExtra("jumlahpembayar"));
            EditTanggalLaporan.setText(intent.getStringExtra("tanggallaporan"));
            EditAngkatan.setText(intent.getStringExtra("angkatan"));
            EditDeskripsi.setText(intent.getStringExtra("deskripsi"));
        }
    }

    private void saveDataLaporan(String nominalpembayaran, String jumlahpembayar, String tanggallaporan, String angkatan, String deskripsi, String hasil) {
        Map<String, Object> laporan1 = new HashMap<>();
        laporan1.put("nominalpembayaran", nominalpembayaran);
        laporan1.put("jumlahpembayar", jumlahpembayar);
        laporan1.put("tanggallaporan", tanggallaporan);
        laporan1.put("angkatan", angkatan);
        laporan1.put("deskripsi", deskripsi);
        laporan1.put("hasil", hasil);

        progressDialog.show();
        if (id != null) {
            db.collection("Laporan biaya masuk").document(id)
                    .set(laporan1)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if ((task.isSuccessful())) {
                                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            db.collection("Laporan biaya masuk")
                    .add(laporan1)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });

        }

    }
}