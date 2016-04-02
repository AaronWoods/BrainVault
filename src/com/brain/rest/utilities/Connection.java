package com.brain.rest.utilities;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Created by Aaron Woods on 01/04/2016.
 */
public class Connection {
    private Cluster cluster;
    private Session session;

    String table = "users";

    public Connection() {
        cluster = Cluster.builder().addContactPoints("127.0.0.1").build();
        cluster.getConfiguration().getSocketOptions().setConnectTimeoutMillis(100000);
        session = cluster.connect();
        session.execute("USE brainstore;");
        System.out.println("Cassandra Connection Successful");
    }
}
