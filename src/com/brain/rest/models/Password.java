package com.brain.rest.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Aaron Woods on 01/04/2016.
 */

@XmlRootElement
public class Password {
    private String password, newPassword;

    public Password(){}

    public Password(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
