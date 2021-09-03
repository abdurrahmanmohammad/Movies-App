package com.example.moviesapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moviesapp.R;
import com.example.moviesapp.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public static final String TAG = "MovieAdapter"; // For debugging
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=04387a9831174fd21d2ca3db9bdd8ca6";


    Context context; // Context to inflate the views - where the adapter is being constructed from
    List<Movie> movies = new ArrayList<>(); // List of movies the adapter needs to hold on to

    public MovieAdapter(Context context) {
        this.context = context;
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

    private void getMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject; // Get the actual JSON object
                try {
                    JSONArray results = jsonObject.getJSONArray("results"); // Get results array from JSON object
                    Log.i(TAG, "Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    notifyDataSetChanged(); // Whenever data changes, re-render view
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }


    // Inner view holder class - representation of row in recycler view
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title;
        TextView overview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Import resources
            poster = itemView.findViewById(R.id.movie_poster);
            title = itemView.findViewById(R.id.movie_title);
            overview = itemView.findViewById(R.id.movie_overview);
        }

        public void bind(Movie movie) {
            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());
            Glide.with(context).load(movie.getPosterPath()).into(poster); // Load image URL into image view
        }
    }
}
