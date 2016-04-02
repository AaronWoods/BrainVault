package com.brain.rest.store;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Created by Aaron Woods on 01/04/2016.
 */
public class MovieDataStore {
    private Cluster cluster;
    private Session session;

    String table = "movies";

    public MovieDataStore() {
        cluster = Cluster.builder().addContactPoints("127.0.0.1").build();
        cluster.getConfiguration().getSocketOptions().setConnectTimeoutMillis(100000);
        session = cluster.connect();
        session.execute("USE brainstore;");
        System.out.println("Cassandra Connection Successful");
    }
}
