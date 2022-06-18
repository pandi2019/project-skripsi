package com.example.Projectpandi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Projectpandi.Adapter.BulananAdapter;
import com.example.Projectpandi.Model.Bulanan14;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Bulanan extends AppCompatActivity {
    private RecyclerView RecyclerView14;
    private FloatingActionButton btnAdd18;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Bulanan14> list = new ArrayList<>();
    private BulananAdapter bulananAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulanan);
        RecyclerView14 = (RecyclerView) findViewById(R.id.recyclerview14);
        btnAdd18 = (FloatingActionButton) findViewById(R.id.btnadd5);

        progressDialog = new ProgressDialog(Bulanan.this);
        progressDialog.setTitle("loading");
        progressDialog.setMessage("Mengambil Data...");
        bulananAdapter = new BulananAdapter(getApplicationContext(), list);
        bulananAdapter.setDialog(new BulananAdapter.Dialog() {

            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Bulanan.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), Bulanan2.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("namabln", list.get(pos).getNamabln());
                                intent.putExtra("nisbln", list.get(pos).getNisbln());
                                intent.putExtra("kelasbln", list.get(pos).getKelasbln());
                                intent.putExtra("tanggalbln", list.get(pos).getTanggalbln());
                                intent.putExtra("bulanan", list.get(pos).getBulanan());
                                startActivity(intent);
                                break;
                            case 1:
                                deleteDatabase(list.get(pos).getId());
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        RecyclerView14.setLayoutManager(layoutManager);
        RecyclerView14.addItemDecoration(decoration);
        RecyclerView14.setAdapter(bulananAdapter);

        btnAdd18.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Bulanan2.class));
        });
        getData();
    }

    private void getData() {
        progressDialog.show();
        db.collection("bulanan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Bulanan14 user14 = new Bulanan14(document.getString("namabln"), document.getString("nisbln"), document.getString("kelasbln"), document.getString("tanggalbln"), document.getString("bulanan"));
                                user14.setId(document.getId());
                                list.add(user14);
                            }
                            bulananAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data Gagal Tampil", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
}