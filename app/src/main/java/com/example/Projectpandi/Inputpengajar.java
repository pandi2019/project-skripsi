package com.example.Projectpandi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Inputpengajar<intent> extends AppCompatActivity {
    private EditText editNama3, editNip3, editJabatan3;
    private Button btnSave3;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputpengajar);
        editNama3 = findViewById(R.id.nama_pengajar);
        editNip3 = findViewById(R.id.nip_pegawai);
        editJabatan3 = findViewById(R.id.jabatan);
        btnSave3 = findViewById(R.id.btnsimpan2);

        progressDialog = new ProgressDialog(Inputpengajar.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Simpan...");

        btnSave3.setOnClickListener(v -> {
            if (editNama3.getText().length() > 0 && editNip3.getText().length() > 0 && editJabatan3.getText().length() > 0) {
                saveData2(editNama3.getText().toString(), editNip3.getText().toString(), editJabatan3.getText().toString());

            } else {
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            editNama3.setText(intent.getStringExtra("namapengajar"));
            editNip3.setText(intent.getStringExtra("nippegawai"));
            editJabatan3.setText(intent.getStringExtra("jabatan"));
        }
    }

    private void saveData2(String namapengajar, String nippegawai, String jabatan) {
        Map<String, Object> daftar3 = new HashMap<>();
        daftar3.put("namapengajar", namapengajar);
        daftar3.put("nippegawai", nippegawai);
        daftar3.put("jabatan", jabatan);

        progressDialog.show();
        if (id != null) {
            db.collection("Data Pengajar").document(id)
                    .set(daftar3)
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
            db.collection("Data Pengajar")
                    .add(daftar3)
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