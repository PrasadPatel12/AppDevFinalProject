package com.example.music2go;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;

public class login extends AppCompatActivity {
    EditText inputEmail;
    EditText inputPassword;
    Button signin;
    Button signup;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Toast.makeText(login.this,"Firebase connection successful", Toast.LENGTH_LONG).show();

        inputEmail = findViewById(R.id.editText1);
        inputPassword = findViewById(R.id.editText2);
        signin = findViewById(R.id.button1);
        signup = findViewById(R.id.button2);
        firebaseAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString(); // The Email
                String password = inputPassword.getText().toString(); // the Password

                // If Email is empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If Password is empty
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Authenticating the Email and Password
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //  if Email isn't registered or if Email format is incorrect or if Email and Password do not match
                                if (!task.isSuccessful()) {
                                    Toast.makeText(login.this, "Email and Password do not match.", Toast.LENGTH_LONG).show();
                                }
                                // If authentication is successful
                                else {
                                    // Redirect to Login Page
                                    Intent intent = new Intent(login.this, home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), register.class);
                startActivity(intent);
            }
        });

    }
}