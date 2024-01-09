package com.appdeveloper.estore.model;

public class User {
    String firstName;
    private String id;

    public String getFirstName() {
        return firstName;
    }

    public User(String firstName, String id) {
        this.firstName = firstName;
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
