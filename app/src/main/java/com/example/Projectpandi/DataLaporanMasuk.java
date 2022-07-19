package com.example.Projectpandi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.Projectpandi.Adapter.LaporanmasukAdapter;

import com.example.Projectpandi.Model.LaporanM;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DataLaporanMasuk extends AppCompatActivity {
    private RecyclerView recyclerViewMasuk;
    private FloatingActionButton btnAddMasuk;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<LaporanM> list = new ArrayList<>();
    private LaporanmasukAdapter laporanmasukAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datalaporanmasuk);

        recyclerViewMasuk = findViewById(R.id.recyclerviewlaporanmasuk);
        btnAddMasuk= findViewById(R.id.btnaddlaporanmasuk);

        progressDialog = new ProgressDialog(DataLaporanMasuk.this);
        progressDialog.setTitle("loading");
        progressDialog.setMessage("Mengambil Data...");
        laporanmasukAdapter= new LaporanmasukAdapter(getApplicationContext(), list);
        laporanmasukAdapter.setDialog(new LaporanmasukAdapter.Dialog(){

            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(DataLaporanMasuk.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), LaporanMasuk.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nominalpembayaran", list.get(pos).getNominalpembayaran());
                                intent.putExtra("jumlahpembayar", list.get(pos).getJumlahpembayar());
                                intent.putExtra("tanggallaporan", list.get(pos).getTanggallaporan());
                                intent.putExtra("angkatan", list.get(pos).getAngkatan());
                                intent.putExtra("deskripsi", list.get(pos).getDeskripsi());
                                intent.putExtra("hasil", list.get(pos).getHasil());
                                startActivity(intent);
                                break;
                            case 1:
                                deleteData(list.get(pos).getId());
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerViewMasuk.setLayoutManager(layoutManager);
        recyclerViewMasuk.addItemDecoration(decoration);
        recyclerViewMasuk.setAdapter(laporanmasukAdapter);

        btnAddMasuk.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LaporanMasuk.class));
            finish();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getData() {
        progressDialog.show();
        db.collection("Laporan biaya masuk")
                .get()
                .addOnCompleteListener(task -> {
                    list.clear();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            LaporanM laporan1 = new LaporanM(document.getString("nominalpembayaran"), document.getString("jumlahpembayar"), document.getString("tanggallaporan"), document.getString("angkatan"), document.getString("deskripsi"), document.getString("hasil"));
                            laporan1.setId(document.getId());
                            list.add(laporan1);
                        }
                        laporanmasukAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "Data Gagal Tampil", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });

    }
    private void deleteData(String id) {
        progressDialog.show();
        db.collection("Laporan biaya masuk").document(id)
                .delete()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Data Gagal Dihapus!", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    getData();
                });
    }
}
