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

public class LaporanAngsuran extends AppCompatActivity {
    private EditText EditNominalAngsuran, EditJumlahAngsuran, EditTanggalAngsuran, EditAngkatan1, EditDeskripsi1;
    private TextView HasilAngsuran;
    private Button btnSimpanAngsuran, btnTotalAngsuran, btnResetAngsuran;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporanangsuran);

        EditNominalAngsuran = findViewById(R.id.nominalangsuran);
        EditJumlahAngsuran = findViewById(R.id.jumlahangsuran);
        EditTanggalAngsuran = findViewById(R.id.tanggalangsuran);
        EditAngkatan1   = findViewById(R.id.angkatan1);
        EditDeskripsi1  = findViewById(R.id.deskripsi1);
        HasilAngsuran = findViewById(R.id.hasilangsuran);
        btnTotalAngsuran = findViewById(R.id.totalangsuran);
        btnSimpanAngsuran = findViewById(R.id.btnsimpanangsuran);
        btnResetAngsuran = findViewById(R.id.btnresetangsuran);

        progressDialog = new ProgressDialog(LaporanAngsuran.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Simpan...");

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        EditTanggalAngsuran.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    LaporanAngsuran.this, (view, year1, month1, dayOfMonth) -> {
                month1 = month1 + 1;
                String date = day + "/" + month1 + "/" + year1;
                EditTanggalAngsuran.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });

        btnTotalAngsuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double nominalangsuran1, jumlahangsuran1, hasilangsuran;
                nominalangsuran1 = Double.valueOf(EditNominalAngsuran.getText().toString().trim());
                jumlahangsuran1 = Double.valueOf(EditJumlahAngsuran.getText().toString().trim());
                hasilangsuran = nominalangsuran1 * jumlahangsuran1;
                String hasilangsuran1 = String.valueOf(hasilangsuran);
                HasilAngsuran.setText(hasilangsuran1);
            }
        });

        btnResetAngsuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNominalAngsuran.setText("");
                EditJumlahAngsuran.setText("");
                EditTanggalAngsuran.setText("");
                EditAngkatan1.setText("");
                EditDeskripsi1.setText("");
                HasilAngsuran.setText("");
            }
        });

        btnSimpanAngsuran.setOnClickListener(v -> {
            if (EditNominalAngsuran.getText().length() > 0 && EditJumlahAngsuran.getText().length() > 0 && EditTanggalAngsuran.getText().length() > 0 && EditAngkatan1.getText().length() > 0 && EditDeskripsi1.getText().length() > 0 && HasilAngsuran.getText().length() > 0) {
                saveDataAngsuran(EditNominalAngsuran.getText().toString(), EditJumlahAngsuran.getText().toString(), EditTanggalAngsuran.getText().toString(), EditAngkatan1.getText().toString(), EditDeskripsi1.getText().toString(), HasilAngsuran.getText().toString());

            } else {
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            EditNominalAngsuran.setText(intent.getStringExtra("nominalangsuran"));
            EditJumlahAngsuran.setText(intent.getStringExtra("jumlahangsuran"));
            EditTanggalAngsuran.setText(intent.getStringExtra("tanggalangsuran"));
            EditAngkatan1.setText(intent.getStringExtra("angkatan1"));
            EditDeskripsi1.setText(intent.getStringExtra("deskripsi1"));
        }
    }

    private void saveDataAngsuran(String nominalangsuran, String jumlahangsuran, String tanggalangsuran, String angkatan1, String deskripsi1, String hasilangsuran) {
        Map<String, Object> angsuran1 = new HashMap<>();
        angsuran1.put("nominalangsuran", nominalangsuran);
        angsuran1.put("jumlahangsuran", jumlahangsuran);
        angsuran1.put("tanggalangsuran", tanggalangsuran);
        angsuran1.put("angkatan1", angkatan1);
        angsuran1.put("deskripsi1", deskripsi1);
        angsuran1.put("hasilangsuran", hasilangsuran);

        progressDialog.show();
        if (id != null) {
            db.collection("Laporan biaya angsuran").document(id)
                    .set(angsuran1)
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
            db.collection("Laporan biaya angsuran")
                    .add(angsuran1)
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