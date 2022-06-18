package com.example.Projectpandi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Bulanan2 extends AppCompatActivity {
    private EditText EditNamabln, EditNisbln, EditKelasbln, EditTanggalbln, EditBulanan;
    private Button btnBayar14;
    private ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener setListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulanan2);
        EditNamabln        = findViewById(R.id.namabln);
        EditNisbln        = findViewById(R.id.nisbln);
        EditKelasbln       = findViewById(R.id.kelasbln);
        EditTanggalbln     = findViewById(R.id.tanggalbln);
        EditBulanan     = findViewById(R.id.bulanan);
        btnBayar14        = findViewById(R.id.btntransaksibln);

        progressDialog= new ProgressDialog(Bulanan2.this);
        progressDialog.setTitle("loading");
        progressDialog.setTitle("Membayar...");
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month= calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        EditTanggalbln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Bulanan2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        EditTanggalbln.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnBayar14.setOnClickListener(v -> {
            if (EditNamabln.getText().length() > 0 && EditNisbln.getText().length() > 0 && EditKelasbln.getText().length() > 0 && EditTanggalbln.getText().length() > 0 && EditBulanan.getText().length() > 0) {
                bayarData14(EditNamabln.getText().toString(), EditNisbln.getText().toString(), EditKelasbln.getText().toString(), EditTanggalbln.getText().toString(), EditBulanan.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Silahkan Isi Semua Data!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void bayarData14(String namabln, String nisbln, String kelasbln, String tanggalbln, String bulanan) {
        Map<String, Object> user14 = new HashMap<>();
        user14.put("namabln", namabln);
        user14.put("nisbln", nisbln);
        user14.put("kelasbln", kelasbln);
        user14.put("tanggalbln", tanggalbln);
        user14.put("bulanan", bulanan);

        progressDialog.show();
        db.collection("bulanan")
                .add(user14)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

    }

}