package com.example.Projectpandi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Projectpandi.Adapter.PengajarAdapter;
import com.example.Projectpandi.Model.Pengajar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Datapengajar extends AppCompatActivity {
    private RecyclerView recyclerView11;
    private FloatingActionButton btnAdd11;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Pengajar> list = new ArrayList<>();
    private PengajarAdapter pengajarAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datapengajar);

        recyclerView11 = findViewById(R.id.recyclerview1);
        btnAdd11 = findViewById(R.id.btnadd4);

        progressDialog = new ProgressDialog(Datapengajar.this);
        progressDialog.setTitle("loading");
        progressDialog.setMessage("Mengambil Data...");

        pengajarAdapter = new PengajarAdapter(getApplicationContext(), list);
        pengajarAdapter.setDialog(pos -> {
            final CharSequence[] dialogItem = {"Edit", "Hapus"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(Datapengajar.this);
            dialog.setItems(dialogItem, (dialogInterface, i) -> {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), Inputpengajar.class);
                        intent.putExtra("id", list.get(pos).getId());
                        intent.putExtra("namapengajar", list.get(pos).getNamapengajar());
                        intent.putExtra("nippegawai", list.get(pos).getNippegawai());
                        intent.putExtra("jabatan", list.get(pos).getJabatan());
                        startActivity(intent);
                        break;
                    case 1:
                        deleteData(list.get(pos).getId());
                        break;
                }
            });
            dialog.show();
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView11.setLayoutManager(layoutManager);
        recyclerView11.addItemDecoration(decoration);
        recyclerView11.setAdapter(pengajarAdapter);

        btnAdd11.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Inputpengajar.class));
            finish();
        });

        getData();
    }

    private void getData() {
        progressDialog.show();
        db.collection("pegawai")
                .get()
                .addOnCompleteListener(task -> {
                    list.clear();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Pengajar daftar3 = new Pengajar(document.getString("namapengajar"), document.getString("nippegawai"), document.getString("jabatan"));
                            daftar3.setId(document.getId());
                            list.add(daftar3);
                        }
                        pengajarAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "Data Gagal Tampil", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });

    }

    private void deleteData(String id) {
        progressDialog.show();
        db.collection("pegawai").document(id)
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