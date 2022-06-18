package com.example.Projectpandi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button btnlogin, buatakun;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.Password);
        buatakun = findViewById(R.id.Buatakun);
        btnlogin = findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog( Login.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        buatakun.setOnClickListener((v) -> {
            startActivity(new Intent(getApplicationContext(), Register.class));
        });
        btnlogin.setOnClickListener((v) -> {
            if (email.getText().length()>0 && password.getText().length()>0){
                login(email.getText().toString(), password.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(),"Silahkan isi semua data !", Toast.LENGTH_SHORT).show();
            }
            });
    }
    public void login(String email, String password){
    //CODING LOGIN
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null) {
                    if (task.getResult().getUser()!=null) {
                    reload();
                    }else{
                        Toast.makeText(getApplicationContext(),"Login Gagal", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Login Gagal", Toast.LENGTH_SHORT).show();

                }
                }
            });
        }

        private void reload(){
        startActivity(new Intent(getApplicationContext(), Beranda.class));
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
    }