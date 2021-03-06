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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.moviesapp.DetailActivity;
import com.example.moviesapp.R;
import com.example.moviesapp.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    // Constants
    private static final String TAG = "MovieAdapter"; // For debugging
    private static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/popular";
    private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie";
    private static final String API_KEY = "04387a9831174fd21d2ca3db9bdd8ca6";
    private static final int LIMIT = 1000; // The maximum page number is 1000
    // Variables
    private Context context; // Context to inflate the views - where the adapter is being constructed from
    private List<Movie> movies = new ArrayList<>(); // List of movies the adapter needs to hold on to
    private static RequestQueue mQueue; // Request queue for API requests
    private int page = 1; // Initially load page 1 of API response

    // Constructor
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

    // Populate movies list: Append 20 movies to list on each call
    public void getMovies() {
        if (page >= LIMIT) return; // Page should be within the limit
        String url = String.format("%s?api_key=%s&page=%d", NOW_PLAYING_URL, API_KEY, page);
        // Request a JSON object response from the API URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    Log.d(TAG, "onSuccess: getMovies");
                    try {
                        JSONArray results = response.getJSONArray("results"); // Get results array from JSON object
                        Log.i(TAG, "Results: " + results.toString());
                        movies.addAll(fromJsonArray(results));
                        notifyDataSetChanged(); // Whenever data changes, re-render view
                        Log.i(TAG, "Movies: " + movies.size());
                    } catch (JSONException e) {
                        Log.e(TAG, "Hit JSON exception", e);
                        e.printStackTrace();
                    }
                }, error -> {
                    Log.d(TAG, "onFailure: getMovies");
                    Log.e(TAG, "Hit VolleyError exception", error);
                    error.printStackTrace();
                });
        mQueue.add(jsonObjectRequest); // Add the request to the RequestQueue
        page++; // Increment page for next API call
    }

    // Create a list of Movie objects from a JSON array
    public List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>(); // Initialize the return object
        // Iterate through the JSON array and append a new Movie object for each entry
        for (int i = 0; i < movieJsonArray.length(); i++) {
            Movie movie = new Movie(movieJsonArray.getJSONObject(i));
            movies.add(movie); // Add the movie to movies array
            getMovieDetails(movie); // Fill in additional details: genres, homepage, runtime
        }
        return movies; // Return a list of Movie objects containing parsed API JSON data
    }

    // Get additional details for a movie and modify it
    public void getMovieDetails(Movie movie) {
        String url = String.format("%s/%d?api_key=%s", MOVIE_URL, movie.getId(), API_KEY);
        Log.d(TAG, "Fetching movie details");
        // Request a JSON object response from the API URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    Log.d(TAG, "onSuccess: getMovieDetails");
                    try {
                        movie.setDetails(response); // Parse and set additional movie fields
                    } catch (JSONException e) {
                        Log.e(TAG, "Hit JSON exception", e);
                        e.printStackTrace();
                    }
                }, error -> {
                    Log.d(TAG, "onFailure: getMovieDetails");
                    Log.e(TAG, "Hit VolleyError exception", error);
                    error.printStackTrace();
                });
        mQueue.add(jsonObjectRequest); // Add the API request to the RequestQueue
    }

    // Inner view holder class: Representation of a row in the recycler view
    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container; // The RelativeLayout container that contains all the row items
        ImageView poster; // Movie poster field in row
        TextView title, rating, year, popularity, genres, overview; // TextView fields in row

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Import resources
            container = itemView.findViewById(R.id.container);
            poster = itemView.findViewById(R.id.movie_poster);
            title = itemView.findViewById(R.id.movie_title);
            rating = itemView.findViewById(R.id.movie_rating);
            year = itemView.findViewById(R.id.detail_year);
            popularity = itemView.findViewById(R.id.detail_popularity);
            genres = itemView.findViewById(R.id.movie_genres);
            overview = itemView.findViewById(R.id.movie_overview);
        }

        // Method to bind movie data to the view
        public void bind(Movie movie) {
            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());
            year.setText(movie.getReleaseDate().substring(0, 4));
            popularity.setText(String.format("Popularity: %s", movie.getPopularity()));
            rating.setText(Double.toString(movie.getRating()));
            genres.setText(movie.getGenres());

            // If phone is landscape, use backdrop path, else use poster path
            String imageURL = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? movie.getBackdrop_path() : movie.getPosterPath();
            Glide.with(context).load(imageURL).into(poster); // Load image URL into image view

            // Set a click listener: Do something when you click on the row
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
