package com.brain.rest.store;

import com.brain.rest.models.Movie;
import com.brain.rest.utilities.*;
import com.datastax.driver.core.*;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by Aaron Woods on 01/04/2016.
 */
public class MovieDataStore {
    private Session session;
    private Cluster cluster;
    private String table;

    public MovieDataStore() {
        cluster = Cluster.builder().addContactPoints("127.0.0.1").build();
        cluster.getConfiguration().getSocketOptions().setConnectTimeoutMillis(100000);
        session = cluster.connect();
        session.execute("USE brainstore;");
        System.out.println("Cassandra Connection Successful");
        table = "movies";
    }

    public LinkedList<Movie> getUserMovies(String username) throws SQLException { //Visualise
        LinkedList<Movie> movies = new LinkedList<>();
        Movie movie;
        PreparedStatement ps = session.prepare("SELECT * FROM movies WHERE username = ?;");
        BoundStatement bs = new BoundStatement(ps);
        ResultSet rs = session.execute(bs.bind(username));
        Row r = rs.one();
        if(r==null){
            return null;
        }
        while(r != null){ //work on this for testing, alternative select * in cqlsh
            System.out.println("Listing movies's... "+ r.getString("username"));
            movie = new Movie(r.getString("name"),
                    r.getString("username"),
                    r.getString("shortplot"),
                    r.getString("year"),
                    r.getString("genre"),
                    r.getString("director"),
                    r.getString("coverart"),
                    r.getString("imdbrating"),
                    r.getString("imdbid"),
                    r.getString("usermovieid"));
            movies.add(movie);
            r= rs.one();
        }
        //String name, String username, String shortPlot, String year, String genre, String director, String coverArt, String imdbRating, String imdbID, String userMovieID
        close();
        return movies;
    }

    public Movie postMovie(Movie movie) throws SQLException {
        Row r = checkMoviePresence(movie.getUserMovieID());
        if(r != null){
            System.out.println("Found occurence of userMovieId, already posted movie");
            close();
            return null;

        }
        else {
            System.out.println("No occurence of usermovieID - Attempting to add new movie");
            PreparedStatement ps = session.prepare("INSERT INTO " + table + " (usermovieid, coverart, " +
                    "director, genre, imdbid, imdbrating, name, shortplot, username, year) VALUES (?,?,?,?,?,?,?,?,?,?);");
            //name-Title, shortPlot-Plot,year-Year,genre-Genre,director-Director,coverart-Poster,imdbrating-imdbRating,imdbid-imdbID. USERNAME - UserMov
            BoundStatement bs = new BoundStatement(ps);
            session.execute(bs.bind(movie.getUserMovieID(),
                    movie.getCoverArt(),
                    movie.getDirector(),
                    movie.getGenre(),
                    movie.getImdbID(),
                    movie.getImdbRating(),
                    movie.getName(),
                    movie.getShortPlot(),
                    movie.getUsername(),
                    movie.getYear()));
            close();
            System.out.println("Added movie "+movie.getName()+" to "+movie.getUsername()+"'s profile with id " +movie.getUserMovieID());
            return movie;
        }
    }

    public boolean dropMovie(String userMovieID) throws SQLException{
        Row r = checkMoviePresence(userMovieID);
        if(r!=null){
            PreparedStatement ps = session.prepare("DELETE FROM "+table+" WHERE usermovieid = ?;");
            BoundStatement bs = new BoundStatement(ps);
            session.execute(bs.bind(userMovieID));
            System.out.println(userMovieID + " removed");
            close();
            return true;
        }
        else{
            close();
            return false;
        }
    }

    /****************************************************************************/
    /********************************TEST AREA***********************************/
    /****************************************************************************/


    private Row checkMoviePresence(String userMovieID){
        PreparedStatement ps = session.prepare("SELECT * FROM movies WHERE usermovieid = ?;");
        BoundStatement bs = new BoundStatement(ps);
        ResultSet rs = session.execute(bs.bind(userMovieID));
        return rs.one();
    }

    private void close(){
        session.close();
        cluster.close();
    }
}
