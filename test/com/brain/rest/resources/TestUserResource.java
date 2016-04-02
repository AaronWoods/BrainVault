package com.brain.rest.resources;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.brain.rest.models.User;
import com.brain.rest.service.UserService;
import com.brain.rest.store.UserDataStore;
import org.codehaus.jettison.json.JSONArray;

@Path("/testusers")
public class TestUserResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test3(){
        return "test works";
    }

}
