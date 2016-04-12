package com.brain.rest.service;

import com.brain.rest.models.Movie;
import com.brain.rest.store.MovieDataStore;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Aaron Woods on 01/04/2016.
 *
 */
public class MovieService {

    private Client client= null;
    private MovieDataStore data=null;

    public MovieService(){
        client = Client.create();
        data = new MovieDataStore();
    }

    public Response getMovies(String username){
        try{
            LinkedList<Movie> movies = data.getUserMovies(username);
            if(movies != null){
                GenericEntity<List<Movie>> entity = new GenericEntity<List<Movie>>(new LinkedList(movies)) {};
                System.out.println("NOT NULL DATA ROWS");
                return Response.ok(entity).status(200).build();
            }
            else return Response.status(404).build();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Response searchMovie(String searchTerm){
        Movie tempMovie;
        StringBuilder builder;
        LinkedList<Movie> movies = new LinkedList<>();
        JSONObject jsonTemp, objectResponse;
        WebResource webResource = client.resource("http://www.omdbapi.com/?s="+searchTerm+"&y=&plot=short&r=objectResponse");
        ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if(clientResponse.getStatus() == 200){
            System.out.println("SUCCESS from external api");
            builder = convertIOStreamToStringBuilder(clientResponse);
            try {
                objectResponse = new JSONObject(builder.toString());
                JSONArray js = objectResponse.getJSONArray("Search");
                for(int i =0; i<js.length(); i++){
                    jsonTemp = js.getJSONObject(i);
                    if(jsonTemp.getString("Type").equals("movie")) {
                        tempMovie = new Movie(jsonTemp.getString("Title"),
                                jsonTemp.getString("Year"),
                                jsonTemp.getString("Poster"));
                        movies.add(tempMovie);
                    }
                }
            }catch (JSONException e){ e.printStackTrace(); }
            GenericEntity<List<Movie>> entity = new GenericEntity<List<Movie>>(new LinkedList(movies)) {};
            return Response.ok(entity).status(200).build();
        }
        else{
            Response.status(400).build();
        }
        return null;
    }

    public Response postMovie(String username, String movieId){
        Movie movie = null;
        StringBuilder builder;
        JSONObject objectResponse;
        WebResource webResource = client.resource("http://www.omdbapi.com/?i="+movieId+"&y=&plot=short&r=objectResponse");
        ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if(clientResponse.getStatus() == 200){
            System.out.println("SUCCESS from external api");
            builder = convertIOStreamToStringBuilder(clientResponse);
            try {
                objectResponse = new JSONObject(builder.toString());
                movie = new Movie(objectResponse.getString("Title"),
                        username,
                        objectResponse.getString("Plot"),
                        objectResponse.getString("Year"),
                        objectResponse.getString("Genre"),
                        objectResponse.getString("Director"),
                        objectResponse.getString("Poster"),
                        objectResponse.getString("imdbRating"),
                        objectResponse.getString("imdbID"),
                        username+objectResponse.getString("imdbID"));
                //name-Title, shortPlot-Plot,year-Year,genre-Genre,director-Director,coverart-Poster,imdbrating-imdbRating,imdbid-imdbID. USERNAME - UserMovieID
            }catch (JSONException e){ e.printStackTrace(); }
            try {
                Movie responseMovie = data.postMovie(movie);
                if(responseMovie == null){
                    return Response.status(409).build();
                }
                else{
                    return Response.ok(responseMovie).status(201).build();
                }
            }catch (SQLException e){ e.printStackTrace(); }
        }
        else{
            System.out.println("Failed to contact external api");
            return Response.status(404).build();
        }
        return null;
    }

    public Response deleteMovie(String userMovieID){
        try{
            if(data.dropMovie(userMovieID)) return Response.status(204).build();
            else return Response.status(404).build();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private StringBuilder convertIOStreamToStringBuilder(ClientResponse clientResponse){
        StringBuilder builder=null;
        String line;
        try {
            builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientResponse.getEntityInputStream()));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e){ e.printStackTrace(); }
        return builder;
    }
    /*TEST AREA*/
}
