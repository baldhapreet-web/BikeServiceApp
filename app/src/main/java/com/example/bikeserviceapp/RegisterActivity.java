package com.example.bikeserviceapp;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    MaterialButton btnRegister;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        auth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> {

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        if(task.isSuccessful()){

                            Toast.makeText(RegisterActivity.this,
                                    "Registration Successful! Please Login",
                                    Toast.LENGTH_LONG).show();

                            // SIGN OUT newly created user
                            FirebaseAuth.getInstance().signOut();

                            // GO TO LOGIN SCREEN
                            Intent intent =
                                    new Intent(RegisterActivity.this, LoginActivity.class);

                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(RegisterActivity.this,
                                    task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}