package com.brain.rest.resources;

import com.brain.rest.models.Movie;
import com.brain.rest.models.User;
import com.brain.rest.service.MovieService;
import com.brain.rest.store.UserDataStore;
import org.codehaus.jettison.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by Aaron Woods on 01/04/2016.
 */

@Path("/movies")
public class MovieResource {

    MovieService movieService = new MovieService();

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public LinkedList<Movie> getUserMovies(@PathParam("username")String username){
        LinkedList<Movie> movies = new LinkedList<>();

    }
}
