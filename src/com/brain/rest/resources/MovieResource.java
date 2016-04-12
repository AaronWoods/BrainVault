package com.brain.rest.resources;

import com.brain.rest.service.MovieService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Aaron Woods on 01/04/2016.
 */

@Path("/movies")
public class MovieResource {

    private MovieService movieService = new MovieService();

    //View logged movies by username
    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserMovies(@PathParam("username")String username){
        return movieService.getMovies(username);
    }

    //search/find movie
    @GET
    @Path("/search/{searchTerm}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response movieSearch(@PathParam("searchTerm")String searchTerm){
        return movieService.searchMovie(searchTerm);
    }

    //fetch movie info and post movie
    @GET
    @Path("/{username}/{imdbid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postMovie(@PathParam("username") String username, @PathParam("imdbid")String movieId){
        System.out.println("TRYING TO POST MOVIE");
        return movieService.postMovie(username, movieId);
    }

    //remove movie
    @DELETE
    @Path("/{userMovieID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeMovie(@PathParam("userMovieID") String userMovieID){
        System.out.println("TRYING TO REMOVE MOVIE");
        return movieService.deleteMovie(userMovieID);
    }
    /*TEST AREA*/

}
