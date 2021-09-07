package com.example.moviesapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    // Constants
    private static final String base_url = "https://image.tmdb.org/t/p";
    private static final String size = "w342";

    //
    private String posterPath;
    private String backdropPath;
    private String title;
    private String overview;
    private double rating;

    private int id;
    private String popularity;
    private String releaseDate;
    private String runtime;
    private String homepage;
    private String genres;


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


    public void setDetails(JSONObject jsonObject) throws JSONException {
        // Parse fields
        int runtimeInt = jsonObject.getInt("runtime");
        runtime = String.format("%dh %dm", runtimeInt / 60, runtimeInt % 60);
        homepage = jsonObject.getString("homepage");
        // Parse genres
        JSONArray JsonGenres = jsonObject.getJSONArray("genres"); // Get results array from JSON object

        genres = JsonGenres.getJSONObject(0).getString("name");
        for (int i = 1; i < JsonGenres.length(); i++)
            genres += ", " + JsonGenres.getJSONObject(i).getString("name");
    }

    // ******************** Getters ********************

    public String getGenres() {
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

    public String getRuntime() {
        return runtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public double getRating() {
        return rating;
    }
}
