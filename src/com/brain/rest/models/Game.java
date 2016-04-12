package com.brain.rest.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Aaron Woods on 31/03/2016.
 */
@XmlRootElement
public class Game extends Interest {

    private String id, platform, overview, genre, boxarturl, publisher, developer, userGameID;

    public Game(){}

    public Game(String name, String username, String userGameID){
        super(name, username);
        this.userGameID = userGameID;
    }

    public Game(String name, String username, String id, String platform, String overview, String genre, String boxarturl, String publisher, String developer) {
        super(name, username);
        this.id = id;
        this.platform = platform;
        this.overview = overview;
        this.genre = genre;
        this.boxarturl = boxarturl;
        this.publisher = publisher;
        this.developer = developer;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getBoxarturl() {
        return boxarturl;
    }
    public void setBoxarturl(String boxarturl) {
        this.boxarturl = boxarturl;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getDeveloper() {
        return developer;
    }
    public void setDeveloper(String developer) {
        this.developer = developer;
    }
}
