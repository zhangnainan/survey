package com.sg.survey.user;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;

/**
 * Created by jiuge on 2020/6/23.
 */
@Entity
@Component("com.sg.survey.user.model")
public class UserModel {

    private String id;
    private String username;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
