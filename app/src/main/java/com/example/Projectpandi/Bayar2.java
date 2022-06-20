package com.example.Projectpandi;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Bayar2 extends AppCompatActivity {
    private EditText EditNama, EditNamaortu, EditKelas, EditTanggal, EditNominal;
    private ImageView Uploadpendaftaran;
    private Button btnBayar;
    private ProgressDialog progressDialog;
    private String id = "";
    private DatePickerDialog.OnDateSetListener setListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bayar2);
        EditNama = findViewById(R.id.nama);
        EditNamaortu = findViewById(R.id.namaortu);
        EditKelas = findViewById(R.id.kelas);
        EditTanggal = findViewById(R.id.tanggal);
        EditNominal = findViewById(R.id.nominal);
        btnBayar = findViewById(R.id.btntransaksi);
        Uploadpendaftaran = findViewById(R.id.uploadpendaftaran);

        progressDialog = new ProgressDialog(Bayar2.this);
        progressDialog.setTitle("loading");
        progressDialog.setTitle("Membayar...");

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        Uploadpendaftaran.setOnClickListener(v -> selectImage());

        EditTanggal.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    Bayar2.this, (view, year1, month1, dayOfMonth) -> {
                        month1 = month1 + 1;
                        String date = day + "/" + month1 + "/" + year1;
                        EditTanggal.setText(date);
                    }, year, month, day);
            datePickerDialog.show();
        });

        btnBayar.setOnClickListener(v -> {
            if (EditNama.getText().length() > 0 && EditNamaortu.getText().length() > 0 && EditKelas.getText().length() > 0 && EditTanggal.getText().length() > 0 && EditNominal.getText().length() > 0) {
                upload(EditNama.getText().toString(), EditNamaortu.getText().toString(), EditKelas.getText().toString(), EditTanggal.getText().toString(), EditNominal.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Silahkan Isi Semua Data!", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            EditNama.setText(intent.getStringExtra("nama"));
            EditNamaortu.setText(intent.getStringExtra("namaortu"));
            EditKelas.setText(intent.getStringExtra("kelas"));
            EditTanggal.setText(intent.getStringExtra("tanggal"));
            EditNominal.setText(intent.getStringExtra("nominal"));

            Picasso.get().load(intent.getStringExtra("uploadpendaftaran"))
                    .centerCrop()
                    .fit()
                    .into(Uploadpendaftaran);
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Bayar2.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 10);
            } else if (items[item].equals("Choose from Library")) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 20);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && requestCode == RESULT_OK && data != null) {
            final Uri path = data.getData();
            Thread thread = new Thread(() -> {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(path);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Uploadpendaftaran.post(() -> {
                        Uploadpendaftaran.setImageBitmap(bitmap);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        if (requestCode == 10 && resultCode == RESULT_OK) {
            final Bundle extras = data.getExtras();
            Thread thread = new Thread(() -> {
                Bitmap bitmap = (Bitmap) extras.get("data");
                Uploadpendaftaran.post(() -> Uploadpendaftaran.setImageBitmap(bitmap));
            });

            thread.start();
        }
    }

    private void upload(String nama, String namaortu, String kelas, String tanggal, String nominal) {
        progressDialog.show();
        Uploadpendaftaran.setDrawingCacheEnabled(true);
        Uploadpendaftaran.buildDrawingCache();

        Bitmap bitmap = ((BitmapDrawable) Uploadpendaftaran.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // UPLOAD
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("images").child("IMG" + new Date().getTime() + ".jpeg");
        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(e -> {
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }).addOnSuccessListener(taskSnapshot -> {
            if (taskSnapshot.getMetadata() != null) {
                if (taskSnapshot.getMetadata().getReference() != null) {
                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.getResult() != null) {
                                bayarData(nama, namaortu, kelas, tanggal, nominal, task.getResult().toString());
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                }
            } else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bayarData(String nama, String namaortu, String kelas, String tanggal, String nominal, String uploadpendaftaran) {
        Map<String, Object> user = new HashMap<>();
        user.put("nama", nama);
        user.put("namaortu", namaortu);
        user.put("kelas", kelas);
        user.put("tanggal", tanggal);
        user.put("nominal", nominal);
        user.put("uploadpendaftaran", uploadpendaftaran);

        progressDialog.show();
        db.collection("pendaftaran")
                .add(user)
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


