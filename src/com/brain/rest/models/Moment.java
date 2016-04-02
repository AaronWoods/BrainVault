package com.brain.rest.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Aaron Woods on 31/03/2016.
 */
@XmlRootElement
public class Moment extends Interest{
    private String description;

    public Moment(){}

    public Moment(String name, String username){
        super(name,username);
    }
}
