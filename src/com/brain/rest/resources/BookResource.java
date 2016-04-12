package com.brain.rest.resources;

import com.brain.rest.service.BookService;
import com.brain.rest.store.BookDataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Aaron Woods on 12/04/2016.
 */
@Path("/books")
public class BookResource {

    private BookService bookService = new BookService();

    //View logged books by username
    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies(@PathParam("username")String username){
        return bookService.getBooks(username);
    }

    //search find movie
    @GET
    @Path("/search/{searchTerm}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response bookSearch(@PathParam("searchTerm")String searchTerm){
        return bookService.searchBooks(searchTerm);
    }
    //fetch movie info and post
    @GET
    @Path("/{username}/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postBook(@PathParam("username")String username,@PathParam("bookId")String bookID){
        return bookService.postBook(username, bookID);
    }

    @DELETE
    @Path("/{userBookID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeMovie(@PathParam("userBookID") String userBookID){
        System.out.println("TRYING TO REMOVE BOOK");
        return bookService.deleteBook(userBookID);
    }



    //remove book


    //CLIENT SCRIPTS - TABLE MANIPULATION

    @GET
    @Path("/database")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeDB(){
        BookDataStore bds = new BookDataStore();
        return bds.manipDB();
    }
}
