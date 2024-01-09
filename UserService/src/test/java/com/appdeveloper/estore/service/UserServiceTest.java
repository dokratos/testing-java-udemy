package com.appdeveloper.estore.service;

import com.appdeveloper.estore.model.User;
import com.appdeveloper.estore.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest {
    UserService userService;
    String lastName;
    String email;
    String password;
    String repeatPassword;
    @BeforeEach
    void init() {
        userService = new UserServiceImpl();
        lastName = "Scalco";
        email = "hello@gmail.com";
        password = "password";
        repeatPassword = "password";
    }

    @DisplayName("user object created")
    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {
//        Arrange
        String firstName = "Iliana";
//        Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);
//        Assert
        assertNotNull(user, "Should not return null");
        assertNotNull(user.getId(), "id should not be null");
        assertEquals(firstName, user.getFirstName(), "user firstname is incorrect");
    }

    @DisplayName("Throws exceptions")
    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwsIllegalArgumentException() {
        //        Arrange
        String firstName = "";
        String expectedMessage = "user's first name is empty";
//        Act
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email,
                    password, repeatPassword);
        }, "User must have firstname");
//        Assert
        assertEquals(expectedMessage, thrown.getMessage(), "Wrong exception message");
    }
}
