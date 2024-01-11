package com.appdeveloper.estore.service;

import com.appdeveloper.estore.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User createUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String repeatPassword) {
        if (firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException("user's first name is empty");
        }

        return new User(firstName, UUID.randomUUID().toString());
    }
}
