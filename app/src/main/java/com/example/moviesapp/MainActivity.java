package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moviesapp.adapters.MovieAdapter;
import com.example.moviesapp.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity"; // For debugging
    List<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>(); // Initialize movies list

        RecyclerView moviesRecyclerView = findViewById(R.id.movies_recycler_view);

        // Bind adapter to data source to populate recycler view
        // Step 1: Create an adapter
        MovieAdapter movieAdapter = new MovieAdapter(this);
        // Step 2: Set the adapter on the recycler view
        moviesRecyclerView.setAdapter(movieAdapter);
        // Step 3: Set a layout manager on the recycler view
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));





    }
}