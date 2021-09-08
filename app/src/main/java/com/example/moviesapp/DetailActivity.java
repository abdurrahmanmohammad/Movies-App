package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.models.Movie;

import org.parceler.Parcels;


public class DetailActivity extends AppCompatActivity {
    Movie movie; // Movie to display in activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Views
        TextView title, year, runtime, popularity, overview, genres, homepage;
        RatingBar ratingBar;
        ImageView poster;

        // Initialize activity
        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie")); // Retrieve movie from previous page
        //getSupportActionBar().setHomeButtonEnabled(true); // Display the back button in the toolbar

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
        popularity.setText(String.format("Popularity: %s", movie.getPopularity()));
        Glide.with(this).load(movie.getBackdrop_path()).into(poster); // Load image URL into image view
        overview.setText(movie.getOverview());
        genres.setText(movie.getGenres());

        // Add listener to button
        homepage.setOnClickListener(view -> {
            // Open browser & redirect user to movie homepage
            Uri uri = Uri.parse(movie.getHomepage());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }
}