package com.brain.rest.service;

import com.brain.rest.models.Book;
import com.brain.rest.store.BookDataStore;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.groovy.control.io.ReaderSource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Aaron Woods on 12/04/2016.
 */
public class BookService {
    private Client client= null;
    private BookDataStore data=null;
    private final String API_KEY = "AIzaSyDUI7oSKLoMHmmxQpr5HfFp1EFil30Bt7k";

    public BookService(){
        client = Client.create();
        data = new BookDataStore();
    }

    public Response getBooks(String username){
        try{
            LinkedList<Book> books = data.getUserBooks(username);
            if(books != null){
                GenericEntity<List<Book>> entity = new GenericEntity<List<Book>>(new LinkedList(books)) {};
                System.out.println("NOT NULL DATA ROWS");
                return Response.ok(entity).status(200).build();
            }
            else return Response.status(404).build();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Response searchBooks(String searchTerm) {
        Book tempBook;
        String temp="";
        StringBuilder builder;
        LinkedList<Book> books = new LinkedList<>();
        JSONObject jsonTemp,jsonTemp2, objectResponse;
        WebResource webResource = client.resource("https://www.googleapis.com/books/v1/volumes?q=" + searchTerm + "&key=" + API_KEY);
        ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (clientResponse.getStatus() == 200) {
            System.out.println("SUCCESS from external api");
            builder = convertIOStreamToStringBuilder(clientResponse);
            try {
                objectResponse = new JSONObject(builder.toString());
                JSONArray js = objectResponse.getJSONArray("items");
                //Retrieve each books search information
                for (int itemCount = 0; itemCount < js.length(); itemCount++) {
                    try {
                        tempBook = new Book();
                        jsonTemp = js.getJSONObject(itemCount);
                        tempBook.setId(jsonTemp.getString("id"));
                        jsonTemp2 = jsonTemp.getJSONObject("volumeInfo");
                        tempBook.setName(jsonTemp2.getString("title"));
                        JSONArray authors = jsonTemp2.getJSONArray("authors");
                        for (int authorCount = 0; authorCount < authors.length(); authorCount++) {
                            temp += authors.getString(authorCount) + " ";
                        }
                        tempBook.setAuthor(temp);
                        temp = "";
                        jsonTemp = jsonTemp2.getJSONObject("imageLinks");
                        tempBook.setThumbnailImg(jsonTemp.getString("smallThumbnail"));
                        books.add(tempBook);
                    }catch (JSONException e){
                        System.out.println("ignore missing values");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            GenericEntity<List<Book>> entity = new GenericEntity<List<Book>>(new LinkedList(books)) {};
            return Response.ok(entity).status(200).build();
        }
        else{
            Response.status(400).build();
        }
        return null;
    }

    public Response postBook(String username, String bookID){
        Book book;
        String temp="";
        StringBuilder builder;
        LinkedList<Book> books = new LinkedList<>();
        JSONObject bookInfo,jsonTemp, objectResponse;
        WebResource webResource = client.resource("https://www.googleapis.com/books/v1/volumes/"+bookID+"?key=" + API_KEY);
        ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (clientResponse.getStatus() == 200) {
            System.out.println("SUCCESS from external api");
            book = new Book();
            builder = convertIOStreamToStringBuilder(clientResponse);
            try {
                objectResponse = new JSONObject(builder.toString());
                book.setId(bookID);
                book.setUsername(username);
                book.setUserBookID(username+objectResponse.getString("id"));
                bookInfo = objectResponse.getJSONObject("volumeInfo");
                book.setName(bookInfo.getString("title"));
                book.setPublisher(bookInfo.getString("publisher"));
                book.setDescription(bookInfo.getString("description"));
                JSONArray authors = bookInfo.getJSONArray("authors");
                for(int numAuthors = 0; numAuthors<authors.length(); numAuthors++){
                    temp+= authors.getString(numAuthors)+ " ";
                }
                book.setAuthor(temp);
                jsonTemp = bookInfo.getJSONObject("imageLinks");
                book.setThumbnailImg(jsonTemp.getString("smallThumbnail"));
            }catch(JSONException e){
                e.printStackTrace();
            }
            try {
                Book responseBook = data.postBook(book);
                if(responseBook == null){
                    return Response.status(409).build();
                }
                else{
                    return Response.ok(responseBook).status(201).build();
                }
            }catch (SQLException e){ e.printStackTrace(); }
            return Response.ok(book).status(200).build();

        }
        else{
            return Response.status(404).build();
        }
    }

    public Response deleteBook(String userBookID){
        try{
            if(data.dropBook(userBookID)) return Response.status(204).build();
            else return Response.status(404).build();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    private StringBuilder convertIOStreamToStringBuilder(ClientResponse clientResponse){
        StringBuilder builder=null;
        String line;
        try {
            builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientResponse.getEntityInputStream()));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e){ e.printStackTrace(); }
        return builder;
    }
}
