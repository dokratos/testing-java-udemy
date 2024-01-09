package com.appdeveloper.estore.service;

import com.appdeveloper.estore.model.User;

public interface UserService {
    User createUser(String firstName, String lastName, String email, String password, String repeatPassword);
}
