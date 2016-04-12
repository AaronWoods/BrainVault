package com.brain.rest.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Aaron Woods on 31/03/2016.
 */
@XmlRootElement
public class Book extends Interest {
    private String author, description, id, publisher, subtitle, thumbnailImg, userBookID;

    public Book(){}

    public Book(String name, String username, String userBookID){
        super(name, username);
        this.userBookID = userBookID;
    }

    public Book(String name, String username, String author, String description, String id, String publisher, String subtitle, String thumbnailImg) {
        super(name, username);
        this.author = author;
        this.description = description;
        this.id = id;
        this.publisher = publisher;
        this.subtitle = subtitle;
        this.thumbnailImg = thumbnailImg;
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
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public String getThumbnailImg() {
        return thumbnailImg;
    }
    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }
}
