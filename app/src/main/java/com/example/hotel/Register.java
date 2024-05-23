package com.example.hotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextName;
    Button buttonReg;
    FirebaseAuth mAuth;
    TextView textView;

    DatabaseReference mDataBase;
    private String USER_KEY = "User";

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        editTextName = findViewById(R.id.Name);

        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);

        textView = findViewById(R.id.loginNow);
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        buttonReg.setOnClickListener(view -> {
            String email, password, name;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());
            name = String.valueOf(editTextName.getText());

            if (TextUtils.isEmpty(email)){
                Toast.makeText(Register.this, "Enter email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(name)){
                Toast.makeText(Register.this, "Enter your name", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                // Save user details to SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user_name", name);
                                editor.putString("user_email", email);
                                editor.apply();

                                // Save user details to Firebase Realtime Database
                                String uid = task.getResult().getUser().getUid();
                                User newUser = new User(name, email, "User", uid);
                                mDataBase.child(uid).setValue(newUser);

                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }
}
