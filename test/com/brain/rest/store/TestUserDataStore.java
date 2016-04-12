package com.brain.rest.store;

import com.datastax.driver.core.Cluster;
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

    public void close(){
        session.close();
        cluster.close();
    }
}