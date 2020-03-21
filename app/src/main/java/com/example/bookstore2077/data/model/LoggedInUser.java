package com.example.bookstore2077.data.model;

import com.example.bookstore2077.data.UserAvatar;

import java.util.List;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String id;
    private String login;
    private String password;
    private String email;
    private String name;
    private String surname;
    private List<String> readBooks;
    private UserAvatar avatar;

    public LoggedInUser(String id, String login, String password, String email, String name, String surname) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getId() {
        return id;
    }
}
