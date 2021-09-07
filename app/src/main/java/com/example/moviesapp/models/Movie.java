package com.example.moviesapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    private String base_url = "https://image.tmdb.org/t/p";
    private String size = "w342";
    private String posterPath;
    private String backdropPath;
    private String title;
    private String overview;
    private double rating;

    private int id;
    private String popularity;
    private String releaseDate;
    private int runtime;
    private String homepage;
    private List<String> genres = new ArrayList<>();


    // empty constructor needed by the Parceler library
    public Movie() {
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        // Set variables from passed in objects
        // If fields do not exist in JSON object, throw an exception - caller should deal with the thrown exception
        posterPath = String.format("%s/%s/%s", base_url, size, jsonObject.getString("poster_path"));
        backdropPath = String.format("%s/%s/%s", base_url, size, jsonObject.getString("backdrop_path"));
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        popularity = jsonObject.getString("popularity");
        releaseDate = jsonObject.getString("release_date");
        rating = jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");

        // Pass in ID
        // Do API call and populate fields
        // Do call for movie and call for genres
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

    public List<String> getGenres() {
        return genres;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getId() {
        return id;
    }

    public String getBackdrop_path() {
        return backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public double getRating() {
        return rating;
    }

    // ******************** Setters ********************

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
