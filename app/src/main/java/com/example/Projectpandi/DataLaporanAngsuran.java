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

import com.example.Projectpandi.Adapter.AngsuranAdapter;
import com.example.Projectpandi.Model.Angsuran;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DataLaporanAngsuran extends AppCompatActivity {
    private RecyclerView recyclerViewAngsuran;
    private FloatingActionButton btnAddAngsuran;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Angsuran> list = new ArrayList<>();
    private AngsuranAdapter angsuranAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datalaporanangsuran);

        recyclerViewAngsuran = findViewById(R.id.recyclerviewangsuran);
        btnAddAngsuran= findViewById(R.id.btnaddangsuran);

        progressDialog = new ProgressDialog(DataLaporanAngsuran.this);
        progressDialog.setTitle("loading");
        progressDialog.setMessage("Mengambil Data...");
        angsuranAdapter= new AngsuranAdapter(getApplicationContext(), list);
        angsuranAdapter.setDialog(new AngsuranAdapter.Dialog(){

            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(DataLaporanAngsuran.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), LaporanAngsuran.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nominalangsuran", list.get(pos).getNominalangsuran());
                                intent.putExtra("jumlahangsuran", list.get(pos).getJumlahangsuran());
                                intent.putExtra("tanggalangsuran", list.get(pos).getTanggalangsuran());
                                intent.putExtra("angkatan1", list.get(pos).getAngkatan1());
                                intent.putExtra("deskripsi1", list.get(pos).getDeskripsi1());
                                intent.putExtra("hasilangsuran", list.get(pos).getHasilangsuran());
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
        recyclerViewAngsuran.setLayoutManager(layoutManager);
        recyclerViewAngsuran.addItemDecoration(decoration);
        recyclerViewAngsuran.setAdapter(angsuranAdapter);

        btnAddAngsuran.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LaporanAngsuran.class));
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
        db.collection("Laporan biaya angsuran")
                .get()
                .addOnCompleteListener(task -> {
                    list.clear();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Angsuran angsuran1 = new Angsuran(document.getString("nominalangsuran"), document.getString("jumlahangsuran"), document.getString("tanggalangsuran"), document.getString("angkatan1"), document.getString("deskripsi1"), document.getString("hasilangsuran"));
                            angsuran1.setId(document.getId());
                            list.add(angsuran1);
                        }
                        angsuranAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "Data Gagal Tampil", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });

    }
    private void deleteData(String id) {
        progressDialog.show();
        db.collection("Laporan biaya angsuran").document(id)
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