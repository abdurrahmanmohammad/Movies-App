package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.moviesapp.adapters.MovieAdapter;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity"; // For debugging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView moviesRecyclerView = findViewById(R.id.movies_recycler_view);
        // Bind adapter to data source to populate recycler view
        MovieAdapter movieAdapter = new MovieAdapter(this); // Step 1: Create an adapter
        moviesRecyclerView.setAdapter(movieAdapter); // Step 2: Set the adapter on the recycler view
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Step 3: Set a layout manager on the recycler view
    }
}