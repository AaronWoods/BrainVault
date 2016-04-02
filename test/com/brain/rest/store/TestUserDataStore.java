package com.brain.rest.store;

import java.sql.SQLException;

import com.brain.rest.utilities.ToJson;
import org.codehaus.jettison.json.JSONArray;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class TestUserDataStore {
    private Cluster cluster;
    private Session session;

    String table = "users";

    public TestUserDataStore() {
        cluster = Cluster.builder().addContactPoints("127.0.0.1").build();
        cluster.getConfiguration().getSocketOptions().setConnectTimeoutMillis(100000);
        session = cluster.connect();
        session.execute("USE brainstore;");
        System.out.println("Cassandra Connection Successful");
    }

    public JSONArray populateData() throws SQLException { //testing purposes, adding users

        System.out.println("Attempting to add user");
        PreparedStatement ps = session.prepare("INSERT INTO "+table+" (username,firstname,lastname,password) VALUES (?,?,?,?);");
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet rs =session.execute(boundStatement.bind("woodsa22","Aaron","Woods","pass"));
        ResultSet rs2 =session.execute(boundStatement.bind("gibbons4","Sean","Gibbons","pass"));
        ResultSet rs3 =session.execute(boundStatement.bind("Millisk5","Killian","Mills","pass"));
        close();
        System.out.println("Added test users");
        return toJSON(rs);
    }

    public void close(){
        session.close();
        cluster.close();
    }

    public JSONArray toJSON(ResultSet rs){
        ToJson converter = new ToJson();
        JSONArray json = new JSONArray();
        json = converter.toJSONArray(rs);
        return json;
    }
}