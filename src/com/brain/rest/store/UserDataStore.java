package com.brain.rest.store;

import java.util.*;
import java.sql.SQLException;

import com.brain.rest.models.Password;
import com.brain.rest.models.User;
import com.brain.rest.utilities.SecurityHandler;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class UserDataStore {
    private Cluster cluster;
    private Session session;

    private String table = "users";

    public UserDataStore() {
        cluster = Cluster.builder().addContactPoints("127.0.0.1").build();
        cluster.getConfiguration().getSocketOptions().setConnectTimeoutMillis(100000);
        session = cluster.connect();
        session.execute("USE brainstore;");
        System.out.println("Cassandra Connection Successful");
    }

    public User fetchUser(String username)throws SQLException {
        System.out.println("Attempting to fetch "+ username);
        User user = null;
        PreparedStatement ps = session.prepare("SELECT * FROM users WHERE username = ?;");
        BoundStatement bs = new BoundStatement(ps);
        ResultSet rs = session.execute(bs.bind(username));
        Row r = rs.one();
        if(r!=null) user = new User(r.getString("username"), r.getString("firstname"),r.getString("lastname"),r.getString("password"));
        close();
        return user;
    }

    public User createUser(User user)throws SQLException {
        Row r = checkUserPresence(user.getUsername());
        if(r != null){
            System.out.println("Found occurence of username");
            close();
            return null;
        }
        else{
            System.out.println("No occurence of username - Attempting to add new user");
            String encPass = SecurityHandler.encrypt(user.getPassword());
            PreparedStatement ps = session.prepare("INSERT INTO "+table+" (username, password) VALUES (?,?);");
            BoundStatement bs = new BoundStatement(ps);
            session.execute(bs.bind(user.getUsername(), encPass));
            close();
            System.out.println("Added user with user: "+ user.getUsername());
            System.out.println("User's encrypted pass is: "+ encPass);
            return user;
        }
    }

    public User updateUser(User user)throws SQLException {

        Row r = checkUserPresence(user.getUsername());
        if(r != null){
            System.out.println("Found occurence of username");
            PreparedStatement ps = session.prepare("UPDATE "+table+" SET firstname = ?, lastname = ? WHERE username = ?;");
            BoundStatement bs = new BoundStatement(ps);
            session.execute(bs.bind(user.getFname(), user.getLname(), user.getUsername()));
            close();
            return user;
        }
        else {
            close();
            return null;
        }

    }

    public Boolean deleteUser(String username) throws SQLException { //works - tested
        Row r = checkUserPresence(username);
        if(r!=null){
            PreparedStatement ps = session.prepare("DELETE FROM "+table+" WHERE username = ?;");
            BoundStatement bs = new BoundStatement(ps);
            session.execute(bs.bind(username));
            System.out.println(username + " removed");
            close();
            return true;
        }
        else{
            close();
            return false;
        }
    }

    public Boolean changePassword(String username, Password password) throws SQLException{
        Row r = checkUserPresence(username);
        if(r!=null){
                if(SecurityHandler.decrypt(r.getString("password")).equals(password.getPassword())){
                PreparedStatement ps = session.prepare("UPDATE "+table+" SET password = ? WHERE username = ?;");
                BoundStatement bs = new BoundStatement(ps);
                session.execute(bs.bind(SecurityHandler.encrypt(password.getNewPassword()), username));
                System.out.println("Password updated");
                close();
                return true;
            }
            else{
                close();
                return false;
            }

        }
        else{
            close();
            return false;
        }
    }

    private Row checkUserPresence(String username){
        PreparedStatement ps = session.prepare("SELECT * FROM users WHERE username = ?;");
        BoundStatement bs = new BoundStatement(ps);
        ResultSet rs = session.execute(bs.bind(username));
        return rs.one();
    }

    /****************************************************************************/
    /********************************TEST AREA***********************************/
    /****************************************************************************/

    public LinkedList<User> displayTableData() throws SQLException { //Visualise
        LinkedList<User> users = new LinkedList<>();
        User user;
        String query = "SELECT * FROM "+table+";";
        ResultSet rs =session.execute(query);
        Row r = rs.one();
        while(r != null){ //work on this for testing, alternative select * in cqlsh
            System.out.println("Listing id's... "+ r.getString("username"));
            user = new User(r.getString("username"), r.getString("firstname"), r.getString("lastname"), r.getString("password"));
            users.add(user);
            r= rs.one();
        }
        close();
        return users;
    }

    private void close(){
        session.close();
        cluster.close();
    }

}