package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

    public static final String TAG = "DetailActivity"; // For debugging
    public static final String MOVIE_URL = "https://api.themoviedb.org/3/movie";
    public static final String API_KEY = "04387a9831174fd21d2ca3db9bdd8ca6";
    private RequestQueue mQueue; // Request queue for API requests


    TextView title;
    TextView overview;
    ImageView poster;
    RatingBar ratingBar;

    Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = findViewById(R.id.detail_title);
        overview = findViewById(R.id.detail_overview);
        poster = findViewById(R.id.detail_poster);
        ratingBar = findViewById(R.id.detail_ratingBar);

        mQueue = Volley.newRequestQueue(this); // Instantiate the RequestQueue

        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie")); // Retrieve movie from previous page
        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        Glide.with(this).load(movie.getBackdrop_path()).into(poster); // Load image URL into image view
        ratingBar.setRating((float) movie.getRating());
        getDetails();
    }

    public void getDetails() {
        String url = String.format("%s/%d?api_key=%s", MOVIE_URL, movie.getId(), API_KEY);
        // Request a JSON object response from the API URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    Log.d(TAG, "onSuccess");
                    try {
                        movie.setHomepage(response.getString("homepage"));
                        movie.setRuntime(response.getInt("runtime"));

                        JSONArray genres = response.getJSONArray("genres"); // Get results array from JSON object
                        ArrayList<String> parsedGenres = new ArrayList<>();
                        for(int i = 0; i < genres.length(); i++) {
                            parsedGenres.add(genres.getJSONObject(i).getString("name"));
                        }
                        movie.setGenres(parsedGenres);

                        Log.d(TAG, parsedGenres.toString());

                    } catch (JSONException e) {
                        Log.e(TAG, "Hit JSON exception", e);
                        e.printStackTrace();
                    }
                }, error -> {
                    Log.d(TAG, "onFailure");
                    Log.e(TAG, "Hit VolleyError exception", error);
                    error.printStackTrace();
                });
        mQueue.add(jsonObjectRequest); // Add the request to the RequestQueue

    }
}