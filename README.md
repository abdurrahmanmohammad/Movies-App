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

<img src='https://imgur.com/ywfXjZe' title='Video Walkthrough' width='' alt='Video Walkthrough' />
<img src='https://imgur.com/ywfXjZe' title='Video Walkthrough' width='' alt='Video Walkthrough' />
