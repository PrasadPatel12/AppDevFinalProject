package com.example.music2go;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.telephony.mbms.MbmsErrors;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class CurrentlyPlayingFragment extends Fragment implements View.OnClickListener {
    MediaPlayer mediaPlayer = new MediaPlayer();
    boolean isplay = true; // For the Play/Pause button to work
    boolean isplay2 = true; // For the Play/Pause button to work
    boolean resume = false; // For the Play/Pause button to work
    int currentPosition; // To resume the music

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Music");

    public CurrentlyPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        Boolean isFirstRun = sh.getBoolean("isFirstRun", true);

        if (isFirstRun){
            SharedPreferences sharedpreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("currentMusic", "1");
            editor.putBoolean("isFirstRun", false);
            editor.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_currently_playing, container, false);
        Button rewind = v.findViewById(R.id.rewind);
        Button forward = v.findViewById(R.id.forward);
        Button playpause = v.findViewById(R.id.playpause);
        ImageView artwork = v.findViewById(R.id.albumcover);
        TextView songtitle = v.findViewById(R.id.songtitle);

        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String s1 = sh.getString("currentMusic", "");

        if (s1.equals("1")) {
            songtitle.setText("Calvin Harris - Slide");
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/music2go-8cd3b.appspot.com/o/Artwork%2Fslide.jpg?alt=media&token=b011d660-8fdb-45c6-9cab-dea732dc6518").into(artwork);
            try {
                Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/music2go-8cd3b.appspot.com/o/Music%2F1.mp3?alt=media&token=d9fd195a-d6ae-45d9-902e-c366974ea752");
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(getContext(), uri);
                mediaPlayer.prepare();
            } catch (Exception exception) {
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // While the list has users iterate through them
                    for (DataSnapshot databaseUser : snapshot.getChildren()) {
                        if (databaseUser.getKey().equals(s1)) {
                            // Fetching data and storing it into String variables
                            String databaseArtwork = databaseUser.child("artwork").getValue().toString();
                            String databaseTitle = databaseUser.child("title").getValue().toString();
                            String databaseUrl = databaseUser.child("url").getValue().toString();
                            System.out.println(" " + databaseUser.getKey());
                            System.out.println(" " + databaseArtwork + " " + databaseTitle + " " + databaseUrl);
                            songtitle.setText(databaseTitle);
                            Uri artworkUri = Uri.parse(databaseArtwork);
                            Uri urlUri = Uri.parse(databaseUrl);
                            Picasso.get().load(artworkUri).into(artwork);
                            try {
                                if (mediaPlayer != null) {
                                    if (mediaPlayer.isPlaying())
                                        mediaPlayer.stop();
                                    mediaPlayer.reset();
                                };
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                mediaPlayer.setDataSource(getContext(), urlUri);
                                mediaPlayer.prepare();
                                playpause.setBackgroundResource(R.drawable.ic_playbtn);
                                isplay = true;
                            } catch (Exception exception) {
                                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
                }
            });
        }

        // To Play or Pause the music
        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isplay) {
                    playpause.setBackgroundResource(R.drawable.ic_pause);
                    if (!resume){
                        mediaPlayer.start();
                    }

                    else if (resume){
                        mediaPlayer.seekTo(currentPosition);
                        mediaPlayer.start();
                        resume = false;
                    }
                    isplay = false;
                }

                else if (!isplay) {
                    playpause.setBackgroundResource(R.drawable.ic_playbtn);
                    mediaPlayer.pause();
                    currentPosition = mediaPlayer.getCurrentPosition();
                    isplay = true;
                    resume = true;
                }
            }
        });

        // forward Button
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        String s1 = sh.getString("currentMusic", "");
                        String newS1 = "" + (Integer.parseInt(s1) + 1);
                        SharedPreferences sharedpreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("currentMusic", newS1);
                        editor.commit();
                        // While the list has users iterate through them
                        for (DataSnapshot databaseUser : snapshot.getChildren()) {
                            if (databaseUser.getKey().equals(newS1)) {
                                // Fetching data and storing it into String variables
                                String databaseArtwork = databaseUser.child("artwork").getValue().toString();
                                String databaseTitle = databaseUser.child("title").getValue().toString();
                                String databaseUrl = databaseUser.child("url").getValue().toString();
                                System.out.println(" " + databaseUser.getKey());
                                System.out.println(" " + databaseArtwork + " " + databaseTitle + " " + databaseUrl);
                                songtitle.setText(databaseTitle);
                                Uri artworkUri = Uri.parse(databaseArtwork);
                                Uri urlUri = Uri.parse(databaseUrl);
                                Picasso.get().load(artworkUri).into(artwork);
                                try {
                                    if (mediaPlayer != null) {
                                        if (mediaPlayer.isPlaying())
                                            mediaPlayer.stop();
                                            mediaPlayer.reset();
                                    };
                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    mediaPlayer.setDataSource(getContext(), urlUri);
                                    mediaPlayer.prepare();
                                    playpause.setBackgroundResource(R.drawable.ic_pause);
                                    isplay = false;
                                    mediaPlayer.start();
                                } catch (Exception exception) {
                                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        // rewind Button
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        String s1 = sh.getString("currentMusic", "");
                        String newS1 = "" + (Integer.parseInt(s1) - 1);
                        SharedPreferences sharedpreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("currentMusic", newS1);
                        editor.commit();
                        // While the list has users iterate through them
                        for (DataSnapshot databaseUser : snapshot.getChildren()) {
                            if (databaseUser.getKey().equals(newS1)) {
                                // Fetching data and storing it into String variables
                                String databaseArtwork = databaseUser.child("artwork").getValue().toString();
                                String databaseTitle = databaseUser.child("title").getValue().toString();
                                String databaseUrl = databaseUser.child("url").getValue().toString();
                                System.out.println(" " + databaseUser.getKey());
                                System.out.println(" " + databaseArtwork + " " + databaseTitle + " " + databaseUrl);
                                songtitle.setText(databaseTitle);
                                Uri artworkUri = Uri.parse(databaseArtwork);
                                Uri urlUri = Uri.parse(databaseUrl);
                                Picasso.get().load(artworkUri).into(artwork);
                                try {
                                    if (mediaPlayer != null) {
                                        if (mediaPlayer.isPlaying())
                                            mediaPlayer.stop();
                                            mediaPlayer.reset();
                                    }
                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    mediaPlayer.setDataSource(getContext(), urlUri);
                                    mediaPlayer.prepare();
                                    playpause.setBackgroundResource(R.drawable.ic_pause);
                                    isplay = false;
                                    mediaPlayer.start();
                                } catch (Exception exception) {
                                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
        int resumePosition = mediaPlayer.getCurrentPosition();
        SharedPreferences sharedpreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("resumePosition", resumePosition);
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("I AM BACK");
        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        int position2 = sh.getInt("resumePosition", 0);
        mediaPlayer.seekTo(position2);
        String s1 = sh.getString("currentMusic", "");
        System.out.println(s1);
    }

    @Override
    public void onClick(View v) {
    }
}