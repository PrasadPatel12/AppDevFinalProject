package com.example.music2go;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;

public class register extends AppCompatActivity {

    Button register;
    EditText fname;
    EditText lname;
    EditText username;
    EditText inputPassword;
    EditText inputEmail;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
    //    User user;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        SharedPreferences sh = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        Boolean isFirstRun = sh.getBoolean("isFirstRun2", true);

        if (isFirstRun){
            SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("currentNum", "1");
            editor.putBoolean("isFirstRun2", false);
            editor.commit();
        }

        register = findViewById(R.id.button3);
        fname = findViewById(R.id.editText3);
        lname = findViewById(R.id.editText4);
        username = findViewById(R.id.editText5);
        inputPassword = findViewById(R.id.editText6);
        inputEmail = findViewById(R.id.editText7);
        //user = new User();
        firebaseAuth = FirebaseAuth.getInstance();
//        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//        SharedPreferences sh = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = fname.getText().toString() + " " + lname.getText().toString();
                String username2 = username.getText().toString();
                String email = inputEmail.getText().toString().trim(); // The Email
                String password = inputPassword.getText().toString().trim(); // The Password

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

                // If Password is too short
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password must have a minimum of 6 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Authenticating the Email and Password
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If Email format is incorrect or if Email is already registered
                        if (!task.isSuccessful()) {
                            Toast.makeText(register.this, "Invalid email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // If authentication is successful
                        else {
//                            SharedPreferences.Editor editor = sharedpreferences.edit();
//                            editor.putString("name", fullname);
//                            editor.putString("username", username2);
//                            editor.putString("email", email);
//                            editor.commit();

                            String num = sh.getString("currentNum", "");
                            System.out.println("NUMBER IS " + num);
                            myRef.child("user" + num).setValue(email);
                            myRef.child("user" + num).child("email").setValue(email);
                            myRef.child("user" + num).child("fullname").setValue(fullname);
                            myRef.child("user" + num).child("username").setValue(username2);

                            int num2 = (Integer.parseInt(num) + 1);
                            String num3 = String.valueOf(num2);
                            SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("currentNum", "" + num3);
                            editor.commit();
                            startActivity(new Intent(register.this, login.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
}

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.AuthResult;
//
//public class register extends AppCompatActivity {
//
//    Button register;
//    EditText fname;
//    EditText lname;
//    EditText username;
//    EditText inputPassword;
//    EditText inputEmail;
//    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
//    User user;
//    int num = 1;
//    private FirebaseAuth firebaseAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        getSupportActionBar().hide();
//
//        register = findViewById(R.id.button3);
//        fname = findViewById(R.id.editText3);
//        lname = findViewById(R.id.editText4);
//        username = findViewById(R.id.editText5);
//        inputPassword = findViewById(R.id.editText6);
//        inputEmail = findViewById(R.id.editText7);
//        //user = new User();
//        firebaseAuth = FirebaseAuth.getInstance();
//        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//        SharedPreferences sh = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                user.setFname(fname.getText().toString());
////                user.setLname(lname.getText().toString());
////                user.setUsername(username.getText().toString());
////                user.setPassword(password.getText().toString());
////                user.setEmail(email.getText().toString());
////                String key = myRef.push().getKey();  //Storing key in key variable
////                myRef.child(key).setValue(user); //Adding the user to firebase
////                System.out.println("key: " + key + " was added");
//                String fullname = fname.getText().toString() + " " + lname.getText().toString();
//                String username2 = username.getText().toString();
//                String email = inputEmail.getText().toString().trim(); // The Email
//                String password = inputPassword.getText().toString().trim(); // The Password
//
//                // If Email is empty
//                if (TextUtils.isEmpty(email)) {
//                    Toast.makeText(getApplicationContext(), "Email cannot be empty.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // If Password is empty
//                if (TextUtils.isEmpty(password)) {
//                    Toast.makeText(getApplicationContext(), "Password cannot be empty.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // If Password is too short
//                if (password.length() < 6) {
//                    Toast.makeText(getApplicationContext(), "Password must have a minimum of 6 characters.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Authenticating the Email and Password
//                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        // If Email format is incorrect or if Email is already registered
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(register.this, "Invalid email.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                        // If authentication is successful
//                        else {
//                            SharedPreferences.Editor editor = sharedpreferences.edit();
//                            editor.putString("name", fullname);
//                            editor.putString("username", username2);
//                            editor.putString("email", email);
//                            editor.commit();
//                            startActivity(new Intent(register.this, login.class));
//                            finish();
//                        }
//                    }
//                });
//
////                //Activates as soon as a user is added to firebase
////                myRef.addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////
////                        // While the list has users iterate through them
////                        for (DataSnapshot databaseUser : snapshot.getChildren()) {
////
////                            // Fetching data and storing it into String variables
////                            String databaseFname = databaseUser.child("fname").getValue().toString();
////                            String databaseLname = databaseUser.child("lname").getValue().toString();
////                            String databaseUsername = databaseUser.child("usernname").getValue().toString();
////                            String databaseEmail = databaseUser.child("email").getValue().toString();
////                            String databasePassword = databaseUser.child("password").getValue().toString();
////                            String databaseKey = databaseUser.getKey();
////                        }
////                    }
////
////                    @Override
////                    public void onCancelled(@NonNull DatabaseError error) {
////                        Toast.makeText(register.this, "An error occurred", Toast.LENGTH_LONG).show();
////                    }
////                });
//            }
//        });
//    }
//}