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

import com.example.Projectpandi.Model.User;
import com.example.Projectpandi.Adapter.UserAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class Bayar extends AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView RecyclerView;
    private FloatingActionButton btnAdd;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<User> list = new ArrayList<>();
    private UserAdapter userAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bayar);
        RecyclerView   = (RecyclerView)findViewById(R.id.recyclerview1);
        btnAdd = (FloatingActionButton)findViewById(R.id.btnadd);

        progressDialog = new ProgressDialog(Bayar.this);
        progressDialog.setTitle("loading");
        progressDialog.setMessage("Mengambil Data...");
        userAdapter = new UserAdapter(getApplicationContext(),list);
        userAdapter.setDialog(new UserAdapter.Dialog(){

            @Override
            public void onClick(int pos){
            final CharSequence[] dialogItem = {"Edit","Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder( Bayar.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), Bayar2.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nama", list.get(pos).getNama());
                                intent.putExtra("namaortu", list.get(pos).getNamaortu());
                                intent.putExtra("kelas", list.get(pos).getKelas());
                                intent.putExtra("tanggal", list.get(pos).getTanggal());
                                intent.putExtra("nominal", list.get(pos).getNominal());
                                intent.putExtra("uploadpendaftaran", list.get(pos).getUploadpendaftaran());
                                startActivity(intent);
                                break;
                           case 1:
                                deleteData(list.get(pos).getId(), list.get(pos).getUploadpendaftaran());
                               break;
                       }
                    }
                });
                dialog.show();
            }
    });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        RecyclerView.setLayoutManager(layoutManager);
        RecyclerView.addItemDecoration(decoration);
        RecyclerView.setAdapter(userAdapter);



        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Bayar2.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData(){
        progressDialog.show();
        db.collection("Biaya masuk")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot>task){
                        list.clear();
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document:task.getResult()){
                                User user = new User(document.getString("nama"), document.getString("namaortu"), document.getString("kelas"), document.getString("tanggal"), document.getString("nominal"), document.getString("uploadpendaftaran"));
                                user.setId(document.getId());
                                list.add(user);
                            }
                            userAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getApplicationContext(), "Data Gagal Tampil", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
    private void deleteData(String id, String uploadpendaftaran){
        progressDialog.show();
        db.collection("Biaya masuk").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Data Gagal Dihapus!", Toast.LENGTH_SHORT).show();
                        }else{
                            FirebaseStorage.getInstance().getReferenceFromUrl(uploadpendaftaran).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    getData();
                                }
                            });
                        }

                    }
                });
    }
    
}