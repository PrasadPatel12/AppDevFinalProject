package com.example.music2go;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> userList = new ArrayList<>();;
    Adapter adapter;
    private FirebaseAuth firebaseAuth;
    String s2 = "";
    String s3 = "";
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
        SharedPreferences sharedpreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String s1 = sh.getString("image", "");
//        String s2 = sh.getString("name", "");
//        String s3 = sh.getString("username", "");
        String s4 = sh.getString("email", "");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // While the list has users iterate through them
                for (DataSnapshot databaseUser : snapshot.getChildren()) {
                    // Fetching data and storing it into String variables
                    System.out.println("TEST = " + databaseUser.child("email").getValue().toString());
                    if (databaseUser.child("email").getValue().toString().equals(s4)) {
                        s2 = databaseUser.child("fullname").getValue().toString();
                        String[] parts = s2.split(" ");
                        String fname = parts[0];
                        String lname = parts[1];
                        s3 = databaseUser.child("username").getValue().toString();
                        System.out.println("MATCH YES");
                        userList.add(new ModelClass("First Name", fname, "_______________________________________________"));
                        userList.add(new ModelClass("Last Name", lname, "_______________________________________________"));
                        userList.add(new ModelClass("Username", s3, "_______________________________________________"));
                        userList.add(new ModelClass("Email", s4, "_______________________________________________"));
                        recyclerView = v.findViewById(R.id.recyclerView);
                        layoutManager = new LinearLayoutManager(v.getContext());
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new Adapter(userList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }
        });
//        initData(s2, s3, s4);
//        recyclerView = v.findViewById(R.id.recyclerView);
//        layoutManager = new LinearLayoutManager(v.getContext());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new Adapter(userList);
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        firebaseAuth = FirebaseAuth.getInstance();
        Button signout = v.findViewById(R.id.button4);
        Button plus = v.findViewById(R.id.button);
        ImageView imageView = v.findViewById(R.id.imageView1);
        firebaseAuth = FirebaseAuth.getInstance();

        if (s1.equals("dog")) {
            imageView.setImageResource(R.drawable.dog);
        } else if (s1.equals("cat")) {
            imageView.setImageResource(R.drawable.cat);
        } else if (s1.equals("parrot")) {
            imageView.setImageResource(R.drawable.parrot);
        } else if (s1.equals("fish")) {
            imageView.setImageResource(R.drawable.fish);
        }

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.imagedialog, null);
                builder.setPositiveButton("OK", null);
                builder.setView(dialogLayout);
                builder.show();
                ImageButton dog = dialogLayout.findViewById(R.id.imageButton1);
                ImageButton cat = dialogLayout.findViewById(R.id.imageButton2);
                ImageButton parrot = dialogLayout.findViewById(R.id.imageButton3);
                ImageButton fish = dialogLayout.findViewById(R.id.imageButton4);

                dog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("image", "dog");
                        editor.commit();
                        imageView.setImageResource(R.drawable.dog);
                    }
                });

                cat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("image", "cat");
                        editor.commit();
                        imageView.setImageResource(R.drawable.cat);
                    }
                });

                parrot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("image", "parrot");
                        editor.commit();
                        imageView.setImageResource(R.drawable.parrot);
                    }
                });

                fish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("image", "fish");
                        editor.commit();
                        imageView.setImageResource(R.drawable.fish);
                    }
                });
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getContext(), login.class);
                startActivity(intent);
            }
        });
        return v;
    }

//    private void initData(String name, String username, String email) {
//        userList = new ArrayList<>();
//        userList.add(new ModelClass("Full Name", name, "_______________________________________________"));
//
//        userList.add(new ModelClass("Username", username, "_______________________________________________"));
//
//        userList.add(new ModelClass("Email", email, "_______________________________________________"));

//        userList.add(new ModelClass("Email", email, "_______________________________________________"));
//    }

//    private void initRecyclerView() {
//        recyclerView = getView().findViewById(R.id.recyclerView);
//        layoutManager = new LinearLayoutManager(this.getContext());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new Adapter(userList);
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }
}

//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.ListView;
//
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SettingsFragment extends Fragment {
//    RecyclerView recyclerView;
//    LinearLayoutManager layoutManager;
//    List<ModelClass> userList;
//    Adapter adapter;
//    private FirebaseAuth firebaseAuth;
//
//    public SettingsFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_settings, container, false);
//        SharedPreferences sharedpreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//        String s1 = sh.getString("image", "");
//        String s2 = sh.getString("name", "");
//        String s3 = sh.getString("username", "");
//        String s4 = sh.getString("email", "");
//        initData(s2, s3, s4);
//        recyclerView = v.findViewById(R.id.recyclerView);
//        layoutManager = new LinearLayoutManager(v.getContext());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new Adapter(userList);
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        firebaseAuth = FirebaseAuth.getInstance();
//        Button signout = v.findViewById(R.id.button4);
//        Button plus = v.findViewById(R.id.button);
//        ImageView imageView = v.findViewById(R.id.imageView1);
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        if (s1.equals("dog")) {
//            imageView.setImageResource(R.drawable.dog);
//        } else if (s1.equals("cat")) {
//            imageView.setImageResource(R.drawable.cat);
//        } else if (s1.equals("parrot")) {
//            imageView.setImageResource(R.drawable.parrot);
//        } else if (s1.equals("fish")) {
//            imageView.setImageResource(R.drawable.fish);
//        }
//
//        plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                LayoutInflater inflater = getLayoutInflater();
//                View dialogLayout = inflater.inflate(R.layout.imagedialog, null);
//                builder.setPositiveButton("OK", null);
//                builder.setView(dialogLayout);
//                builder.show();
//                ImageButton dog = dialogLayout.findViewById(R.id.imageButton1);
//                ImageButton cat = dialogLayout.findViewById(R.id.imageButton2);
//                ImageButton parrot = dialogLayout.findViewById(R.id.imageButton3);
//                ImageButton fish = dialogLayout.findViewById(R.id.imageButton4);
//
//                dog.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                        editor.putString("image", "dog");
//                        editor.commit();
//                        imageView.setImageResource(R.drawable.dog);
//                    }
//                });
//
//                cat.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                        editor.putString("image", "cat");
//                        editor.commit();
//                        imageView.setImageResource(R.drawable.cat);
//                    }
//                });
//
//                parrot.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                        editor.putString("image", "parrot");
//                        editor.commit();
//                        imageView.setImageResource(R.drawable.parrot);
//                    }
//                });
//
//                fish.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                        editor.putString("image", "fish");
//                        editor.commit();
//                        imageView.setImageResource(R.drawable.fish);
//                    }
//                });
//            }
//        });
//
//        signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut();
//                Intent intent = new Intent(getContext(), login.class);
//                startActivity(intent);
//            }
//        });
//        return v;
//    }
//
//    private void initData(String name, String username, String email) {
//        userList = new ArrayList<>();
//        userList.add(new ModelClass("First Name", name, "_______________________________________________"));
//
//        userList.add(new ModelClass("Username", username, "_______________________________________________"));
//
//        userList.add(new ModelClass("Email", email, "_______________________________________________"));
//
////        userList.add(new ModelClass("Email", email, "_______________________________________________"));
//    }

//    private void initRecyclerView() {
//        recyclerView = getView().findViewById(R.id.recyclerView);
//        layoutManager = new LinearLayoutManager(this.getContext());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new Adapter(userList);
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }
