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
import org.junit.Assert;
import org.junit.Test;

public class TestUserResource {

    @Test
    public void test(){
        Assert.assertEquals(1,1);
    }

}
