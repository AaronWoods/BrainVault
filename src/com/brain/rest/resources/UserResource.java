package com.brain.rest.resources;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.brain.rest.models.Interest;
import com.brain.rest.models.Moment;
import com.brain.rest.models.Password;
import com.brain.rest.models.User;
import com.brain.rest.service.UserService;
import com.brain.rest.store.UserDataStore;
import com.sun.research.ws.wadl.Link;
import org.codehaus.jettison.json.JSONArray;

@Path("/users")
public class UserResource {
	
	UserService userService = new UserService();

	//Test object fetch
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getObject(){
		return Response.status(200).entity(new Moment("Aaron Woods", "woodsa22")).build();
	}

	//Fetch user
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("username") String username){
		System.out.println("Trying to get user from resource");
		return userService.userInfo(username);
	}

	//Create user
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(User user){
		System.out.println("Trying to add new user from resource");
		return userService.newUser(user);
	}

	//update user information
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User user){
		System.out.println("Attemting to update user "+user.getUsername());
		return userService.updateInfo(user);
	}

	//delete user account
	@DELETE
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeUser(@PathParam("username")String username){
		System.out.println("Trying to remove user from resource");
		return userService.removeUser(username);
	}

	//update password
	@PUT
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changePass(Password password, @PathParam("username")String username){
		System.out.println("Attempting to change password");
		return userService.changePassword(username, password);
	}

//TEST AREA
	@GET
	@Path("/testusers")
	@Produces(MediaType.APPLICATION_JSON)
	public LinkedList<User> test2(){
		UserDataStore usd = new UserDataStore();
		JSONArray json = new JSONArray();
		LinkedList<User> users = new LinkedList<>();
		try{
          	users = usd.displayTableData();
//			json = usd.populateData();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return users;
	}
}
	
	

