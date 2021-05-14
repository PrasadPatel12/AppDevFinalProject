package com.example.music2go;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {
    ListView listView;
//    String[] fruits;
    List<String> fruits_list = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Music");

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        listView=(ListView) v.findViewById(R.id.listView);
        SearchView searchView = v.findViewById(R.id.searchView);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // While the list has users iterate through them
                for (DataSnapshot databaseUser : snapshot.getChildren()) {
                    // Fetching data and storing it into String variables
                    String databaseTitle = databaseUser.child("title").getValue().toString();
                    System.out.println(" " + databaseUser.getKey());
                    System.out.println(databaseTitle);

                    fruits_list.add(databaseTitle);

                    // Create an ArrayAdapter from List
                    arrayAdapter = new ArrayAdapter<String>
                            (getContext(), android.R.layout.simple_list_item_1, fruits_list);

                    // DataBind ListView with items from ArrayAdapter
                    listView.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position++;
                SharedPreferences sharedpreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("currentMusic", String.valueOf(position));
                editor.commit();
                
                Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_currentlyPlayingFragment);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchFragment.this.arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchFragment.this.arrayAdapter.getFilter().filter(newText);
                return  false;
            }
        });
        return v;
    }


}
