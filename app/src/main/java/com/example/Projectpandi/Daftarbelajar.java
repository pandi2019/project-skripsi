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

import com.example.Projectpandi.Adapter.BelajarAdapter;
import com.example.Projectpandi.Model.Belajarskb;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        RecyclerView21 = (RecyclerView)findViewById(R.id.recyclerview2);
        btnAdd21 = (FloatingActionButton)findViewById(R.id.btnadd2);
        {
            progressDialog = new ProgressDialog(Daftarbelajar.this);
            progressDialog.setTitle("loading");
            progressDialog.setMessage("Mengambil Data...");
            belajarAdapter = new BelajarAdapter(getApplicationContext(), list);
            belajarAdapter.setDialog(new BelajarAdapter.Dialog() {

                @Override
                public void onClick(int pos) {
                    final CharSequence[] dialogItem = {"Edit", "Hapus"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Daftarbelajar.this);
                    dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case 0:
                                    Intent intent = new Intent(getApplicationContext(), ProgramBelajar.class);
                                    intent.putExtra("id", list.get(pos).getId());
                                    intent.putExtra("namabelajar", list.get(pos).getNamabelajar());
                                    intent.putExtra("jeniskelaminbelajar", list.get(pos).getJeniskelaminbelajar());
                                    intent.putExtra("tanggallahirbelajar", list.get(pos).getTanggallahirbelajar());
                                    intent.putExtra("agamabelajar", list.get(pos).getAgamabelajar());
                                    intent.putExtra("ayahbelajar", list.get(pos).getAyahbelajar());
                                    intent.putExtra("ibubelajar", list.get(pos).getIbubelajar());
                                    intent.putExtra("alamatbelajar", list.get(pos).getAlamatbelajar());
                                    intent.putExtra("nobelajar", list.get(pos).getNobelajar());
                                    intent.putExtra("emailbelajar", list.get(pos).getEmailbelajar());
                                    intent.putExtra("haritanggalbelajar", list.get(pos).getHaritanggalbelajar());
                                    intent.putExtra("programbelajarskb", list.get(pos).getProgrambelajarskb());
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
            RecyclerView21.setLayoutManager(layoutManager);
            RecyclerView21.addItemDecoration(decoration);
            RecyclerView21.setAdapter(belajarAdapter);

            btnAdd21.setOnClickListener(v -> {
                startActivity(new Intent(getApplicationContext(), ProgramBelajar.class));
            });
        }
    }
}