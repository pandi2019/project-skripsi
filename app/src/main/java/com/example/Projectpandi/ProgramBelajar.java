package com.example.Projectpandi;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProgramBelajar extends AppCompatActivity {
    private TextInputEditText EditNamabelajar, EditTanggallahirbelajar, EditOrangTua,
            EditAlamatbelajar, EditNobelajar, EditEmailbelajar, EditHariTanggalbelajar;

    private AutoCompleteTextView EditAgamabelajar, EditProgrambelajarskb;
    private Button btnDaftar14;

    private RadioGroup Jeniskelaminbelajar;
    private RadioButton laki, perempuan;

    private ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener setListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "ProgramBelajar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.programbelajar);

        EditNamabelajar = findViewById(R.id.namabelajar);

        Jeniskelaminbelajar = findViewById(R.id.jeniskelaminbelajar);
        laki = findViewById(R.id.lakibelajar);
        perempuan = findViewById(R.id.perempuanbelajar);

        EditTanggallahirbelajar = findViewById(R.id.tanggallahirbelajar);
        EditAgamabelajar = findViewById(R.id.agamabelajar);
        EditOrangTua = findViewById(R.id.orangtuabelajar);
        EditAlamatbelajar = findViewById(R.id.alamatbelajar);
        EditNobelajar = findViewById(R.id.nobelajar);
        EditEmailbelajar = findViewById(R.id.emailbelajar);
        EditHariTanggalbelajar = findViewById(R.id.haritanggalbelajar);
        EditProgrambelajarskb = findViewById(R.id.programbelajarskb);
        btnDaftar14 = findViewById(R.id.btndaftarbelajar);

        progressDialog = new ProgressDialog(ProgramBelajar.this);
        progressDialog.setTitle("loading");
        progressDialog.setTitle("Program Belajar...");

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        String[] agamaItem = getResources().getStringArray(R.array.agama);
        ArrayAdapter<String> adapterAgama = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, agamaItem);
        EditAgamabelajar.setAdapter(adapterAgama);

        String[] programBelajarItem = getResources().getStringArray(R.array.programbelajar);
        ArrayAdapter<String> adapterProgramBelajar = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, programBelajarItem);
        EditProgrambelajarskb.setAdapter(adapterProgramBelajar);

        EditHariTanggalbelajar.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ProgramBelajar.this, (view, year1, month1, dayOfMonth) -> {
                month1 = month1 + 1;
                String date = day + "/" + month1 + "/" + year1;
                EditHariTanggalbelajar.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            btnDaftar14.setOnClickListener(v -> {

                String kelaminValue;
                int selectedId = Jeniskelaminbelajar.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    RadioButton answer = findViewById(selectedId);
                    kelaminValue = answer.getText().toString();
                } else {
                    Toast.makeText(getApplicationContext(), "Pilihan jenis kelamin gagal!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String namabelajar = EditNamabelajar.getText().toString().trim();
                String tanggallahirbelajar = EditTanggallahirbelajar.getText().toString().trim();
                String agamabelajar = EditAgamabelajar.getText().toString().trim();
                String orangtuabelajar = EditOrangTua.getText().toString().trim();
                String alamatbelajar = EditAlamatbelajar.getText().toString().trim();
                String nobelajar = EditNobelajar.getText().toString().trim();
                String emailbelajar = EditEmailbelajar.getText().toString().trim();
                String haritanggalbelajar = EditHariTanggalbelajar.getText().toString().trim();
                String programbelajarskb = EditProgrambelajarskb.getText().toString().trim();

                if (namabelajar.isEmpty() || tanggallahirbelajar.isEmpty() || agamabelajar.isEmpty() ||
                        orangtuabelajar.isEmpty() || kelaminValue.isEmpty() || alamatbelajar.isEmpty() ||
                        nobelajar.isEmpty() || emailbelajar.isEmpty() || haritanggalbelajar.isEmpty() || programbelajarskb.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Silahkan Isi Semua Data!", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> belajar = new HashMap<>();
                    belajar.put("namabelajar", namabelajar);
                    belajar.put("jeniskelaminbelajar", kelaminValue);
                    belajar.put("tanggallahirbelajar", tanggallahirbelajar);
                    belajar.put("agamabelajar", agamabelajar);
                    belajar.put("orangtuabelajar", orangtuabelajar);
                    belajar.put("alamatbelajar", alamatbelajar);
                    belajar.put("nobelajar", nobelajar);
                    belajar.put("emailbelajar", emailbelajar);
                    belajar.put("haritanggalbelajar", haritanggalbelajar);
                    belajar.put("programbelajarskb", programbelajarskb);

                    progressDialog.show();
                    db.collection("programbelajar")
                            .add(belajar)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();

                                onBackPressed();
                                progressDialog.dismiss();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            });
                }
            });
        } else {
            String id = extras.getString("id");
            String namabelajar = extras.getString("namabelajar");
            String jeniskelaminbelajar = extras.getString("jeniskelaminbelajar");
            String tanggallahirbelajar = extras.getString("tanggallahirbelajar");
            String agamabelajar = extras.getString("agamabelajar");
            String orangtuabelajar = extras.getString("orangtuabelajar");
            String alamatbelajar = extras.getString("alamatbelajar");
            String nobelajar = extras.getString("nobelajar");
            String emailbelajar = extras.getString("emailbelajar");
            String haritanggalbelajar = extras.getString("haritanggalbelajar");
            String programbelajarskb = extras.getString("programbelajarskb");

            EditNamabelajar.setText(namabelajar);
            EditTanggallahirbelajar.setText(tanggallahirbelajar);
            EditAgamabelajar.setText(agamabelajar);
            EditOrangTua.setText(orangtuabelajar);
            EditAlamatbelajar.setText(alamatbelajar);
            EditNobelajar.setText(nobelajar);
            EditEmailbelajar.setText(emailbelajar);
            EditHariTanggalbelajar.setText(haritanggalbelajar);
            EditProgrambelajarskb.setText(programbelajarskb);

            // Lanjut Proses Update...
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(ProgramBelajar.this, Daftarbelajar.class));
        finish();
    }
}