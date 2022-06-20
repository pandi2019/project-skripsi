package com.example.Projectpandi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Projectpandi.Adapter.BelajarAdapter;
import com.example.Projectpandi.Adapter.PengajarAdapter;
import com.example.Projectpandi.Model.Belajarskb;
import com.example.Projectpandi.Model.Pengajar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Daftarbelajar extends AppCompatActivity {
    private RecyclerView RecyclerView21;
    private FloatingActionButton btnAdd21;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Belajarskb> list = new ArrayList<>();
    private BelajarAdapter belajarAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftarbelajar);

        RecyclerView21 = findViewById(R.id.recyclerview2);
        btnAdd21 = findViewById(R.id.btnadd2);

        progressDialog = new ProgressDialog(Daftarbelajar.this);
        progressDialog.setTitle("loading");
        progressDialog.setMessage("Mengambil Data...");

        belajarAdapter = new BelajarAdapter(getApplicationContext(), list);
        belajarAdapter.setDialog(pos -> {
            final CharSequence[] dialogItem = {"Edit", "Hapus"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(Daftarbelajar.this);
            dialog.setItems(dialogItem, (dialogInterface, i) -> {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), ProgramBelajar.class);
                        intent.putExtra("id", list.get(pos).getId());
                        intent.putExtra("namabelajar", list.get(pos).getNamabelajar());
                        intent.putExtra("jeniskelaminbelajar", list.get(pos).getJeniskelaminbelajar());
                        intent.putExtra("tanggallahirbelajar", list.get(pos).getTanggallahirbelajar());
                        intent.putExtra("agamabelajar", list.get(pos).getAgamabelajar());
                        intent.putExtra("orangtuabelajar", list.get(pos).getOrangtuabelajar());
                        intent.putExtra("alamatbelajar", list.get(pos).getAlamatbelajar());
                        intent.putExtra("nobelajar", list.get(pos).getNobelajar());
                        intent.putExtra("emailbelajar", list.get(pos).getEmailbelajar());
                        intent.putExtra("haritanggalbelajar", list.get(pos).getHaritanggalbelajar());
                        intent.putExtra("programbelajarskb", list.get(pos).getProgrambelajarskb());
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
        RecyclerView21.setLayoutManager(layoutManager);
        RecyclerView21.addItemDecoration(decoration);
        RecyclerView21.setAdapter(belajarAdapter);

        btnAdd21.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ProgramBelajar.class));
            finish();
        });

        loadDaftarBelajar();
    }

    private void deleteData(String id) {
        progressDialog.show();
        db.collection("programbelajar").document(id)
                .delete()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Data Gagal Dihapus!", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    loadDaftarBelajar();
                });
    }

    private void loadDaftarBelajar() {
        progressDialog.show();
        db.collection("programbelajar")
                .get()
                .addOnCompleteListener(task -> {
                    list.clear();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Belajarskb skb = new Belajarskb(document.getString("namabelajar"), document.getString("jeniskelaminbelajar"),
                                    document.getString("agamabelajar"), document.getString("alamatbelajar"), document.getString("emailbelajar"),
                                    document.getString("haritanggalbelajar"), document.getString("nobelajar"), document.getString("orangtuabelajar"),
                                    document.getString("programbelajarskb"), document.getString("tanggallahirbelajar"));
                            skb.setId(document.getId());
                            list.add(skb);
                        }

                        belajarAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), "Data Gagal Tampil", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });
    }
}