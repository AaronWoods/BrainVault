package com.brain.rest.service;

import com.brain.rest.store.MovieDataStore;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by Aaron Woods on 01/04/2016.
 */
public class MovieService {

    MovieDataStore data = new MovieDataStore();

    public Response getMovies(String username){
        try{

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
