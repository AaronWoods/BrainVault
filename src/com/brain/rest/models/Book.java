package com.brain.rest.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Aaron Woods on 31/03/2016.
 */
@XmlRootElement
public class Book extends Interest {

    public Book(){}

    public Book(String username,String name){
        super(name,username);
    }
}
