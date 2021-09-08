package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.moviesapp.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    // Views
    private TextView title;
    private RatingBar ratingBar;
    private TextView year;
    private TextView runtime;
    private TextView popularity;
    private ImageView poster;
    private TextView overview;
    private TextView genres;
    private TextView homepage;
    // Utils
    Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize activity
        //getSupportActionBar().setHomeButtonEnabled(true); // Display the back button in the toolbar
        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie")); // Retrieve movie from previous page

        // Retrieve views
        title = findViewById(R.id.detail_title);
        ratingBar = findViewById(R.id.detail_ratingBar);
        year = findViewById(R.id.detail_year);
        runtime = findViewById(R.id.detail_runtime);
        popularity = findViewById(R.id.detail_popularity);
        poster = findViewById(R.id.detail_poster);
        overview = findViewById(R.id.detail_overview);
        genres = findViewById(R.id.detail_genres);
        homepage = findViewById(R.id.detail_homepage);

        // Initialize views
        title.setText(movie.getTitle());
        ratingBar.setRating((float) movie.getRating());
        year.setText(movie.getReleaseDate().substring(0, 4));
        runtime.setText(movie.getRuntime());
        popularity.setText("Popularity: " + movie.getPopularity());
        Glide.with(this).load(movie.getBackdrop_path()).into(poster); // Load image URL into image view
        overview.setText(movie.getOverview());
        genres.setText(movie.getGenres());

        homepage.setOnClickListener(view -> {
            Uri uri = Uri.parse(movie.getHomepage());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }
}