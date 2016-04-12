package com.brain.rest.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Aaron Woods on 31/03/2016.
 */
@XmlRootElement
public abstract class Interest {
    private String name;
    private String username;

    public Interest(){}

    public Interest(String name, String username){
        this.name=name;
        this.username=username;
    }

    public Interest(String name){
        this.name = name;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
