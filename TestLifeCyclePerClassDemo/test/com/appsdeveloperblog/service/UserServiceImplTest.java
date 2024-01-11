package com.appsdeveloperblog.service;

import com.appsdeveloperblog.io.UsersDatabase;
import com.appsdeveloperblog.io.UsersDatabaseMapImpl;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    UsersDatabase userDB;
    UserService service;
    String createdUserId = "";

    @BeforeAll
    void setup() {
        // Create & initialize database
        userDB = new UsersDatabaseMapImpl();
        userDB.init();
        service = new UserServiceImpl(userDB);
    }

    @AfterAll
    void cleanup() {
        // Close connection
        // Delete database
        userDB.close();
    }

    @Test
    @Order(1)
    @DisplayName("Create User works")
    void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {
//        Arrange
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "Iliana");
        user.put("lastName", "Scalco");

//         Act
        createdUserId = service.createUser(user);

//        Assert
        assertNotNull(createdUserId, "User id should not be null");
    }


    @Test
    @Order(2)
    @DisplayName("Update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {
//        Arrange
        Map<String, String> newUserDetails = new HashMap<>();
        newUserDetails.put("firstName", "Lorenzo");
        newUserDetails.put("lastName", "Scalco");

//        Act
        Map updateUserDetails = service.updateUser(createdUserId, newUserDetails);
//        Assert
        assertEquals(newUserDetails.get("firstName"), updateUserDetails.get("firstName"), "Returned value for firstName is incorrect");
        assertEquals(newUserDetails.get("lastName"), updateUserDetails.get("lastName"), "Returned value for lastName is incorrect");
    }

    @Test
    @Order(3)
    @DisplayName("Find user works")
    void testGetUserDetails_whenProvidedWithValidUserId_returnsUserDetails() {
//        Arrange

//        Act
        Map userDetails = service.getUserDetails(createdUserId);
//        Assert
        assertNotNull(userDetails, "User details should not be null");
        assertEquals(createdUserId, userDetails.get("userId"), "Incorrect user id");
    }

    @Test
    @Order(4)
    @DisplayName("Delete user works")
    void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {
//        Arrange
//        Act

        service.deleteUser(createdUserId);
//        Assert
        assertNull(service.getUserDetails(createdUserId), "User should be null!");
    }

}
