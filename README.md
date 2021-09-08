# Movies-App

The assignment is to build a mobile app to help people discover the most popular movies. The app will consist of a homepage displaying them and tapping on a movie should display additional details.

## User Stories

* [x] Scroll through the list of the current most popular movies. Each movie in Me list should display, if vailable: thumbnail, title, popularity score and release year. List should not be limited to show only the first 20 movies as returned by the API.
* [x] The movie details page should display all of the fields mentioned above and if available: overview. runtime and a link to the movie homepage.
* [x] The genre's name should be present on both screens (Home and Details screen).

## Instructions required to build and run the app in an emulator
1. Download Android Studio and [set up emulator](https://developer.android.com/studio/run/emulator)
1. Clone repository on your computer
1. Run the project in Android Studio

## Design Choices and Architecture
# Design Choices
* Utilized a RecyclerView over ListView to save memory space and speed up row inflation
* Utilized RelativeLayout for rows to order tiems relative to each other and to achieve a particular look on different display sizes
* Utilized a LinearLayout for movie details page to stack components linearly
* Utilized a ScrollView on the moview details page so user can see data which may extend exceed the screen size

# Architecture - MVVM
* Model
 * Movie model stores movie data retrieved from API
 * Contains logic to parse JSON and store data
 * Contains getter methods to access stored data
* View
 * Displays movie data on screen
 * MainActivity displays a RecyclerView in activity_main view with rows (movie_item) displaying movie data
 * DetailActivity displays additional specific movie information in activity_detail view
* View-Model
 * Connects the model to the UI - act as bridges between UI views and Movie model
 * MainActivity encapsulates the activity_main view which contains a RecyclerView and loads more data in RecyclerView when you are reaching the end
 * MovieDetails encapsulates the activity_detail view and fills it with data from a particular Movie object
 * MovieAdapter contains a view-model for a row view (movie_item), inflates rows into the RecyclerView, and manages row data in the view
 * MovieAdapter instantiates, manages, and manipulates a list of Movie objects and does API calls to get movie data

## Third-party Libraries
* Volley
  * Makes networking for Android apps easier and, most importantly, faster.
  * Automatic scheduling of network requests.
  * Multiple concurrent network connections.
  * Easy to use
  * Developed by Google
* Glide
  * Most popular image loading library
  * Supports fetching, decoding, and displaying video stills, images, and animated GIFs.
  * Includes a flexible API that allows developers to plug in to almost any network stack.
  * Easy to use and gets the job done in 1 line
* Parceler
  * Parceler is a code generation library that generates the Android Parcelable boilerplate source code
  * Facilitates the parcelization of objects
  * Parceler will serialize the fields of your instance directly
  * Eliminates the need to write long parcelable code in class definitions

## Video Walkthrough

<img src='https://github.com/abdurrahmanmohammad/Movies-App/blob/main/walkthrough_p1.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />
<img src='https://github.com/abdurrahmanmohammad/Movies-App/blob/main/walkthrough_p2.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />
