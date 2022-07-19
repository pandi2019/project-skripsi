package com.example.Projectpandi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
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

public class Bulanan2<intent> extends AppCompatActivity {
    private EditText EditNamabln, EditNisbln, EditKelasbln, EditTanggalbln, EditBulanan, Salin1;
    private ImageView Uploadbulanan;
    private Button btnBayar14;
    private ImageButton btnSalin1;
    private ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener setListener;
    private String id = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulanan2);
        EditNamabln = findViewById(R.id.namabln);
        EditNisbln = findViewById(R.id.nisbln);
        EditKelasbln = findViewById(R.id.kelasbln);
        EditTanggalbln = findViewById(R.id.tanggalbln);
        EditBulanan = findViewById(R.id.bulanan);
        btnBayar14 = findViewById(R.id.btntransaksibln);
        Salin1 = findViewById(R.id.salin1);
        btnSalin1 = findViewById(R.id.btnsalin1);
        Uploadbulanan = findViewById(R.id.uploadbulanan);

        progressDialog = new ProgressDialog(Bulanan2.this);
        progressDialog.setTitle("loading");
        progressDialog.setTitle("Membayar...");

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        Uploadbulanan.setOnClickListener(v -> selectImage());

        EditTanggalbln.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    Bulanan2.this, (view, year1, month1, dayOfMonth) -> {
                month1 = month1 + 1;
                String date = day + "/" + month1 + "/" + year1;
                EditTanggalbln.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });

        btnSalin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("EditText", Salin1.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(Bulanan2.this, "Salin!!!", Toast.LENGTH_SHORT).show();
            }
        });

        btnBayar14.setOnClickListener(v -> {
            if (EditNamabln.getText().length() > 0 && EditNisbln.getText().length() > 0 && EditKelasbln.getText().length() > 0 && EditTanggalbln.getText().length() > 0 && EditBulanan.getText().length() > 0) {
                upload2(EditNamabln.getText().toString(), EditNisbln.getText().toString(), EditKelasbln.getText().toString(), EditTanggalbln.getText().toString(), EditBulanan.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Silahkan Isi Semua Data!", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            EditNamabln.setText(intent.getStringExtra("namabln"));
            EditNisbln.setText(intent.getStringExtra("nisbln"));
            EditKelasbln.setText(intent.getStringExtra("kelasbln"));
            EditTanggalbln.setText(intent.getStringExtra("tanggalbln"));
            EditBulanan.setText(intent.getStringExtra("bulanan"));

            Picasso.get().load(intent.getStringExtra("uploadbulanan"))
                    .centerCrop()
                    .fit()
                    .into(Uploadbulanan);
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Bulanan2.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.skbkarawang);

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
        if (requestCode == 20 && resultCode == RESULT_OK && data != null) {
            final Uri path = data.getData();
            Thread thread = new Thread(() -> {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(path);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Uploadbulanan.post(() -> {
                        Uploadbulanan.setImageBitmap(bitmap);
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
                Uploadbulanan.post(() -> Uploadbulanan.setImageBitmap(bitmap));
            });

            thread.start();
        }
    }

    private void upload2(String namabln, String nisbln, String kelasbln, String tanggalbln, String bulanan) {
        progressDialog.show();
        Uploadbulanan.setDrawingCacheEnabled(true);
        Uploadbulanan.buildDrawingCache();

        Bitmap bitmap = ((BitmapDrawable) Uploadbulanan.getDrawable()).getBitmap();
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
                                bayarData14(namabln, nisbln, kelasbln, tanggalbln, bulanan, task.getResult().toString());
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

    private void bayarData14(String namabln, String nisbln, String kelasbln, String tanggalbln, String bulanan, String uploadbulanan) {
        Map<String, Object> user14 = new HashMap<>();
        user14.put("namabln", namabln);
        user14.put("nisbln", nisbln);
        user14.put("kelasbln", kelasbln);
        user14.put("tanggalbln", tanggalbln);
        user14.put("bulanan", bulanan);
        user14.put("uploadbulanan", uploadbulanan);

        progressDialog.show();
        if (id != null) {
            db.collection("Biaya angsuran").document(id)
                    .set(user14)
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
            db.collection("Biaya angsuran")
                    .add(user14)
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