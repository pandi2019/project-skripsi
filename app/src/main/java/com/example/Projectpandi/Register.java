package com.example.Projectpandi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText etUsername, etEmail, etPassword, etConfirPassword;
    private Button btnRegister, btnlogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        etUsername = findViewById(R.id.username);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        etConfirPassword = findViewById(R.id.confirpassword);
        btnRegister = findViewById(R.id.btnregister);
        btnlogin = findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog( Register.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        btnlogin.setOnClickListener((v) -> {
            finish();
        });
        btnRegister.setOnClickListener((v) -> {
            if (etUsername.getText().length() > 0 && etEmail.getText().length() > 0 && etPassword.getText().length() > 0 && etConfirPassword.getText().length() > 0)
                if (etPassword.getText().toString().equals(etConfirPassword.getText().toString())) {
                    register(etUsername.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
                } else{
                    Toast.makeText(this, "Silahkan masukan password yang sama !", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Silahkan isi semua data !", Toast.LENGTH_SHORT).show();
        }
        });
    }
    private void register(String username, String email, String password) {
       progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser!=null) {
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(String.valueOf(etUsername))
                                .build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    }else {
                        Toast.makeText(getApplicationContext(), "Register Gagal", Toast.LENGTH_SHORT).show();

                    }
                    }else{
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
