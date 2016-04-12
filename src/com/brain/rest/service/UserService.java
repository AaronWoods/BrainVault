package com.brain.rest.service;

import com.brain.rest.models.Password;
import com.brain.rest.models.User;
import com.brain.rest.store.UserDataStore;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
	
	private UserDataStore data = null;

	public  UserService(){
		data = new UserDataStore();
	}

	public Response userInfo(String username){
		try{
			User user = data.fetchUser(username);
			if(user!=null) return Response.ok(user).status(200).build();
			else return Response.status(204).build();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public Response newUser(User user){
		try{
			User responseUser = data.createUser(user);
			if(responseUser!=null)return Response.ok(user).status(201).build();
			else return Response.status(409).build();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public Response updateInfo(User user){
		try{
			User responseUser = data.updateUser(user);
			if(responseUser!=null)return Response.ok(responseUser).status(200).build();
			else return Response.status(404).build();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public Response removeUser(String username){
		try{
			if(data.deleteUser(username)==true) return Response.status(204).build();
			else return Response.status(404).build();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public Response changePassword(String username, Password password){
		try{
			if(data.changePassword(username,password)==true) return Response.status(200).build();
			else return Response.status(401).build();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
