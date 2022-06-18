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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProgramBelajar extends AppCompatActivity {
    private EditText EditNamabelajar, EditTanggallahirbelajar, EditAgamabelajar, EditAyahbelajar,
    EditIbubelajar, EditAlamatbelajar, EditNobelajar, EditEmailbelajar, EditHariTanggalbelajar, EditProgrambelajarskb;
    private Button btnDaftar14;
    private RadioGroup Jeniskelaminbelajar;
    private ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener setListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.programbelajar);
        EditNamabelajar             = findViewById(R.id.namabelajar);
        Jeniskelaminbelajar         = (RadioGroup)findViewById(R.id.jeniskelaminbelajar);
        EditTanggallahirbelajar     = findViewById(R.id.tanggallahirbelajar);
        EditAgamabelajar            = findViewById(R.id.agamabelajar);
        EditAyahbelajar             = findViewById(R.id.ayahbelajar);
        EditIbubelajar              = findViewById(R.id.ibubelajar);
        EditAlamatbelajar           = findViewById(R.id.alamatbelajar);
        EditNobelajar               = findViewById(R.id.nobelajar);
        EditEmailbelajar            = findViewById(R.id.emailbelajar);
        EditHariTanggalbelajar      = findViewById(R.id.haritanggalbelajar);
        EditProgrambelajarskb       = findViewById(R.id.programbelajarskb);
        btnDaftar14                 = findViewById(R.id.btndaftarbelajar);


        progressDialog= new ProgressDialog(ProgramBelajar.this);
        progressDialog.setTitle("loading");
        progressDialog.setTitle("Membayar...");
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month= calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        EditHariTanggalbelajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ProgramBelajar.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        EditHariTanggalbelajar.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnDaftar14.setOnClickListener(v -> {
            if (EditNamabelajar.getText().length() > 0 && EditTanggallahirbelajar.getText().length() > 0 && EditAgamabelajar.getText().length() > 0 && EditAyahbelajar.getText().length() > 0
                && EditIbubelajar.getText().length() > 0 && EditAlamatbelajar.getText().length() > 0 &&  EditNobelajar.getText().length() > 0 && EditEmailbelajar.getText().length() > 0 && EditHariTanggalbelajar.getText().length() > 0 && EditProgrambelajarskb.getText().length() > 0){
                daftarData4(EditNamabelajar.getText().toString(),Jeniskelaminbelajar.toString(), EditTanggallahirbelajar.getText().toString(), EditAgamabelajar.getText().toString(),  EditAyahbelajar.getText().toString(),
                        EditIbubelajar.getText().toString(), EditAlamatbelajar.getText().toString(), EditNobelajar.getText().toString(), EditEmailbelajar.getText().toString(),  EditHariTanggalbelajar.getText().toString(), EditProgrambelajarskb.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Silahkan Isi Semua Data!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void daftarData4(String namabelajar, String jeniskelaminbelajar, String tanggallahirbelajar, String agamabelajar, String ayahbelajar,
        String ibubelajar, String alamatbelajar, String nobelajar, String emailbelajar, String haritanggalbelajar, String programbelajarskb) {
        Map<String, Object> belajar = new HashMap<>();
        belajar.put("namabelajar", namabelajar);
        belajar.put("jeniskelaminbelajar", jeniskelaminbelajar);
        belajar.put("tanggallahirbelajar", tanggallahirbelajar);
        belajar.put("agamabelajar", agamabelajar);
        belajar.put("ayahbelajar", ayahbelajar);
        belajar.put("ibubelajar", ibubelajar);
        belajar.put("alamatbelajar", alamatbelajar);
        belajar.put("nobelajar", nobelajar);
        belajar.put("emailbelajar", emailbelajar);
        belajar.put("haritanggalbelajar", haritanggalbelajar);
        belajar.put("programbelajarskb", programbelajarskb);

        progressDialog.show();
        db.collection("programbelajar")
                .add(belajar)
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