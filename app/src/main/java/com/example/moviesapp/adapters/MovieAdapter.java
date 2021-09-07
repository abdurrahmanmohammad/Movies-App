package com.example.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.moviesapp.DetailActivity;
import com.example.moviesapp.R;
import com.example.moviesapp.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public static final String TAG = "MovieAdapter"; // For debugging
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/popular";
    public static final String API_KEY = "04387a9831174fd21d2ca3db9bdd8ca6";
    private RequestQueue mQueue; // Request queue for API requests
    private int page = 1, limit = 1000; // Initially load page 1 of API response

    Context context; // Context to inflate the views - where the adapter is being constructed from
    List<Movie> movies = new ArrayList<>(); // List of movies the adapter needs to hold on to

    public MovieAdapter(Context context) {
        this.context = context;
        mQueue = Volley.newRequestQueue(context); // Instantiate the RequestQueue
        getMovies(); // Initialize movies
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        // Inflate layout from XML and return holder - add a movie item and return it inside a view holder
        View movieView = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false); // Returns a view
        return new ViewHolder(movieView); // Wrap movieView inside a view holder
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder " + position);
        // Populate data into item through holder
        Movie movie = movies.get(position); // Get the movie in the passed in position
        holder.bind(movie); //  Bind the movie data into the view holder

    }

    @Override
    public int getItemCount() {
        return movies.size(); // The number of items is the number of movies in the list
    }

    public void getMovies() {
        if (page >= limit) return; // Page should be within the limit
        String url = String.format("%s?api_key=%s&page=%d", NOW_PLAYING_URL, API_KEY, page);
        // Request a JSON object response from the API URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    Log.d(TAG, "onSuccess");
                    try {
                        JSONArray results = response.getJSONArray("results"); // Get results array from JSON object
                        Log.i(TAG, "Results: " + results.toString());
                        movies.addAll(Movie.fromJsonArray(results));
                        notifyDataSetChanged(); // Whenever data changes, re-render view
                        Log.i(TAG, "Movies: " + movies.size());
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
        page++; // Increment page for next API call
    }


    // Inner view holder class - representation of row in recycler view
    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container; // The container for the whole row
        ImageView poster;
        TextView title;
        TextView overview;
        TextView year;
        TextView popularity;
        TextView rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Import resources
            container = itemView.findViewById(R.id.container);
            poster = itemView.findViewById(R.id.movie_poster);
            title = itemView.findViewById(R.id.movie_title);
            overview = itemView.findViewById(R.id.movie_overview);
            year = itemView.findViewById(R.id.movie_year);
            popularity = itemView.findViewById(R.id.movie_popularity);
            rating = itemView.findViewById(R.id.movie_rating);
        }

        public void bind(Movie movie) {
            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());
            year.setText(movie.getReleaseDate().substring(0, 4));
            popularity.setText("Popularity: " + movie.getPopularity());
            rating.setText(Double.toString(movie.getRating()));
            // If phone is landscape, use backdrop path, else use poster path
            String imageURL = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? movie.getBackdrop_path() : movie.getPosterPath();

            Glide.with(context).load(imageURL).into(poster); // Load image URL into image view

            // Set a click listener - do something when you click on the title
            // 1. Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap
                    Intent detailsActivity = new Intent(context, DetailActivity.class); // Create a new intent
                    detailsActivity.putExtra("movie", Parcels.wrap(movie)); // Pass in title
                    context.startActivity(detailsActivity); // Start the activity
                }
            });

        }
    }
}
