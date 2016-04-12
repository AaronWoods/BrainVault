package com.brain.rest.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Aaron Woods on 31/03/2016.
 */
@XmlRootElement
public class Book extends Interest {
    private String author;
    private String description;
    private String id;
    private String publisher;
    private String thumbnailImg;
    private String userBookID;

    public Book(){}

    public Book(String userBookID, String name, String username,  String author, String publisher, String description, String thumbnailImg, String id) {
        super(name, username);
        this.author = author;
        this.description = description;
        this.id = id;
        this.publisher = publisher;
        this.thumbnailImg = thumbnailImg;
        this.id = id;
        this.userBookID = userBookID;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getThumbnailImg() {
        return thumbnailImg;
    }
    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }
    public String getUserBookID() {
        return userBookID;
    }
    public void setUserBookID(String userBookID) {
        this.userBookID = userBookID;
    }
}
