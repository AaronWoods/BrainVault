package com.brain.rest.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Aaron Woods on 31/03/2016.
 */
@XmlRootElement
public class Movie extends Interest {

    private String shortPlot, year, genre, director, coverArt, imdbRating, imdbID, userMovieID;

    public Movie(){}

    public Movie(String name, String year, String coverArt) {
        super(name);
        this.year = year;
        this.coverArt = coverArt;
    }

    public Movie(String name, String username, String shortPlot, String year, String genre, String director, String coverArt, String imdbRating, String imdbID, String userMovieID) {
        super(name, username);
        this.shortPlot = shortPlot;
        this.year = year;
        this.genre = genre;
        this.director = director;
        this.coverArt = coverArt;
        this.imdbRating = imdbRating;
        this.imdbID = imdbID;
        this.userMovieID = userMovieID;
    }

    public String getShortPlot() {
        return shortPlot;
    }
    public void setShortPlot(String shortPlot) {
        this.shortPlot = shortPlot;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getCoverArt() {
        return coverArt;
    }
    public void setCoverArt(String coverArt) {
        this.coverArt = coverArt;
    }
    public String getImdbRating() {
        return imdbRating;
    }
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }
    public String getImdbID() {
        return imdbID;
    }
    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
    public String getUserMovieID() {
        return userMovieID;
    }
    public void setUserMovieID(String userMovieID) {
        this.userMovieID = userMovieID;
    }
}
