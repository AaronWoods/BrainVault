package com.brain.rest.store;

import com.brain.rest.models.Book;
import com.datastax.driver.core.*;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by Aaron Woods on 12/04/2016.
 */
public class BookDataStore {
    private Session session;
    private Cluster cluster;
    private String table;

    public BookDataStore() {
        cluster = Cluster.builder().addContactPoints("127.0.0.1").build();
        cluster.getConfiguration().getSocketOptions().setConnectTimeoutMillis(100000);
        session = cluster.connect();
        session.execute("USE brainstore;");
        System.out.println("Cassandra Connection Successful");
        table = "books";
    }

    public LinkedList<Book> getUserBooks(String username) throws SQLException { //Visualise
        LinkedList<Book> books = new LinkedList<>();
        Book book;
        PreparedStatement ps = session.prepare("SELECT * FROM books WHERE username = ?;");
        BoundStatement bs = new BoundStatement(ps);
        ResultSet rs = session.execute(bs.bind(username));
        Row r = rs.one();
        if(r==null){
            return null;
        }
        while(r != null){ //work on this for testing, alternative select * in cqlsh
            System.out.println("Listing books's... "+ r.getString("username"));
            book = new Book(r.getString("userbookid"),
                    r.getString("name"),
                    r.getString("username"),
                    r.getString("author"),
                    r.getString("publisher"),
                    r.getString("description"),
                    r.getString("thumbnail"),
                    r.getString("id"));
            //userbookid text,name text,username text,subtitle text,author text,publisher text,description text,thumbnail text,id text
            books.add(book);
            r= rs.one();
        }
        //String name, String username, String shortPlot, String year, String genre, String director, String coverArt, String imdbRating, String imdbID, String userMovieID
        close();
        return books;
    }

    public Book postBook(Book book)throws SQLException{
        Row r = checkBookPresence(book.getUserBookID());
        if(r != null){
            System.out.println("Found occurence of userBookId, already posted book");
            close();
            return null;
        }
        else {
            System.out.println("No occurence of userbookID - Attempting to add new book");
            PreparedStatement ps = session.prepare("INSERT INTO " + table + " (userbookid,name,username," +
                    "author,publisher,description,thumbnail,id) VALUES (?,?,?,?,?,?,?,?);");
            //userbookid,name,username,author,publisher,description,thumbnail,id
            BoundStatement bs = new BoundStatement(ps);
            session.execute(bs.bind(book.getUserBookID(),
                    book.getName(),
                    book.getUsername(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getDescription(),
                    book.getThumbnailImg(),
                    book.getId()));
            close();
            System.out.println("Added book "+book.getName()+" to "+book.getUsername()+"'s profile with id " +book.getUserBookID());
            return book;
        }
    }

    public boolean dropBook(String userBookID)throws SQLException {
        Row r = checkBookPresence(userBookID);
        if(r!=null){
            PreparedStatement ps = session.prepare("DELETE FROM "+table+" WHERE userbookid = ?;");
            BoundStatement bs = new BoundStatement(ps);
            session.execute(bs.bind(userBookID));
            System.out.println(userBookID + " removed");
            close();
            return true;
        }
        else{
            close();
            return false;
        }
    }

    private Row checkBookPresence(String userBookID) {
        PreparedStatement ps = session.prepare("SELECT * FROM books WHERE userbookid = ?;");
        BoundStatement bs = new BoundStatement(ps);
        ResultSet rs = session.execute(bs.bind(userBookID));
        return rs.one();
    }

    public Response manipDB(){
        PreparedStatement ps = session.prepare("CREATE CUSTOM INDEX books_username\n" +
                "ON books ( username )");
        BoundStatement bs = new BoundStatement(ps);
        session.execute(bs.bind());
        return Response.status(200).build();
    }

    private void close(){
        session.close();
        cluster.close();
    }


}
