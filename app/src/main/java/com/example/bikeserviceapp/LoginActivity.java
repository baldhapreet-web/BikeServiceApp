package com.example.bikeserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    MaterialButton loginBtn;
    MaterialTextView goRegister;

    FirebaseAuth auth;

    // ✅ ADMIN EMAIL
    String ADMIN_EMAIL = "baldhapreet@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.btnLogin);
        goRegister = findViewById(R.id.goRegister);

        auth = FirebaseAuth.getInstance();

        // LOGIN BUTTON
        loginBtn.setOnClickListener(v -> loginUser());

        // ✅ REGISTER BUTTON CLICK (FIXED)
        goRegister.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LoginActivity.this,
                    RegisterActivity.class
            );
            startActivity(intent);
        });
    }


    private void loginUser() {

        String userEmail = email.getText().toString().trim();
        String userPass = password.getText().toString().trim();

        // ✅ Validation
        if(userEmail.isEmpty()){
            email.setError("Enter Email");
            return;
        }

        if(userPass.isEmpty()){
            password.setError("Enter Password");
            return;
        }

        // ✅ Firebase Login
        auth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){

                        String loggedEmail =
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getEmail();

                        // ================= ADMIN LOGIN =================
                        if(loggedEmail != null &&
                                loggedEmail.equalsIgnoreCase(ADMIN_EMAIL)){

                            Toast.makeText(this,
                                    "Admin Login Success",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(
                                    LoginActivity.this,
                                    AdminActivity.class));

                            finish();
                            return;
                        }

                        // ================= USER PROFILE CHECK =================
                        String userId =
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getUid();

                        DatabaseReference ref =
                                FirebaseDatabase.getInstance()
                                        .getReference("users")
                                        .child(userId);

                        ref.addListenerForSingleValueEvent(
                                new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {

                                        Intent intent;

                                        if(snapshot.exists()){
                                            intent = new Intent(
                                                    LoginActivity.this,
                                                    MenuActivity.class);
                                        } else {
                                            intent = new Intent(
                                                    LoginActivity.this,
                                                    ProfileActivity.class);
                                        }

                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error){}
                                });

                    }else{
                        Toast.makeText(LoginActivity.this,
                                "Login Failed",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}