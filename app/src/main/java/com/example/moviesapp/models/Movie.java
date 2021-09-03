package com.example.moviesapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String base_url = "https://image.tmdb.org/t/p";
    private String size = "w342";
    private String poster_path;
    private String backdrop_path;
    private String title;
    private String overview;

    public Movie(JSONObject jsonObject) throws JSONException {
        // Set variables from passed in objects
        // If fields do not exist in JSON object, throw an exception - caller should deal with the thrown exception
        poster_path = String.format("%s/%s/%s", base_url, size, jsonObject.getString("poster_path"));
        backdrop_path = String.format("%s/%s/%s", base_url, size, jsonObject.getString("backdrop_path"));
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }

    // Create a list of Movie objects from a JSON array
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>(); // Initialize the return object
        // Iterate throught the JSON array and append a new Movie object for each entry
        for (int i = 0; i < movieJsonArray.length(); i++)
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        return movies; // Return a list of Movie objects containing API JSON data
    }

    // ******************** Getters ********************
    public String getPosterPath() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
