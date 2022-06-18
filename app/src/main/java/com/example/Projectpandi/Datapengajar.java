package com.example.Projectpandi;

import androidx.annotation.NonNull;
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

import com.example.Projectpandi.Adapter.PengajarAdapter;
import com.example.Projectpandi.Model.Pengajar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        pengajarAdapter = new PengajarAdapter(getApplicationContext(),list);
        pengajarAdapter.setDialog(new PengajarAdapter.Dialog(){

            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Datapengajar.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView11.setLayoutManager(layoutManager);
        recyclerView11.addItemDecoration(decoration);
        recyclerView11.setAdapter(pengajarAdapter);

        btnAdd11.setOnClickListener(v -> {
                startActivity(new Intent(getApplicationContext(), Inputpengajar.class));
            });
            getData();
        }

        private void getData(){
            progressDialog.show();
            db.collection("pegawai")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task){
                            list.clear();
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot document:task.getResult()){
                                    Pengajar daftar3 = new Pengajar(document.getString("namapengajar"), document.getString("nippegawai"), document.getString("jabatan"));
                                    daftar3.setId(document.getId());
                                    list.add(daftar3);
                                }
                                pengajarAdapter.notifyDataSetChanged();
                            }else {
                                Toast.makeText(getApplicationContext(), "Data Gagal Tampil", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });

        }
        private void deleteData(String id){
            progressDialog.show();
            db.collection("daftar3").document(id)
                    .delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Data Gagal Dihapus!", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                            getData();
                        }
                    });
        }

    }