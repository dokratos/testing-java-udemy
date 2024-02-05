package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.security.SecurityConstants;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.security.Security;
import java.util.Arrays;
import java.util.List;


//this will not load the whole context, but use mock object:::
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//the properties value can be added to the springboottest annotation
//@TestPropertySource(locations = "/application-test.properties", properties = "server.port=8080")
public class UsersControllerIntegrationTest {

    @Value("${server.port}")
    private int serverPort;

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private TestRestTemplate testTemplate;
//
//    @Test
//    void contextLoads() {
//        System.out.println("server.port " + serverPort);
//        System.out.println("running server port " + localServerPort);
//    }

    @Test
    @DisplayName("User can be created")
    void restCreateUser_whenValidDetailsProvided_returnsUserDetails() {
        String createUserJSon = "{\n" +
                " \"firstName\":\"Sergey\",\n" +
                " \"lastName\":\"Kargopolov\",\n" +
                " \"email\":\"test@test.it\",\n" +
                " \"password\":\"123456789\",\n" +
                " \"repeatPassword\":\"123456789\",\n" +
                "}";

        JSONObject userDetailRequestJson = new JSONObject();
        userDetailRequestJson.put("firstName", "Sergey");
        userDetailRequestJson.put("lastName", "Kargopolov");
        userDetailRequestJson.put("email", "test@test.it");
        userDetailRequestJson.put("password", "123456789");
        userDetailRequestJson.put("repeatPassword", "123456789");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(userDetailRequestJson.toString(), headers);

        ResponseEntity<UserRest> createdUserDetailsEntity = testTemplate.postForEntity("/users",
                request,
                UserRest.class);

        UserRest createdUserDetails = createdUserDetailsEntity.getBody();

        Assertions.assertEquals(HttpStatus.OK, createdUserDetailsEntity.getStatusCode());
        Assertions.assertEquals(userDetailRequestJson.getAsString("firstName"), createdUserDetails.getFirstName());
        Assertions.assertEquals(userDetailRequestJson.getAsString("lastName"), createdUserDetails.getLastName());
        Assertions.assertEquals(userDetailRequestJson.getAsString("email"), createdUserDetails.getEmail());
        Assertions.assertFalse(createdUserDetails.getUserId().trim().isEmpty(), "User id should not be empty");

    }

    @Test
    @DisplayName("GET /users requires JWT")
    void testGetUsers_whenMissingJWT_returns403() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<String> request = new HttpEntity<>(headers);

//        Act
        ResponseEntity<List<UserRest>> response = testTemplate.exchange("/users",
                                    HttpMethod.GET,
                                    request,
                                    new ParameterizedTypeReference<List<UserRest>>() {
                                    });
//      Assert
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "message!");
    }

    @Test
    @DisplayName("/login works")
    void testUserLogin_whenValidCredentialsProvided_returnsJWT() {
//        Arrange
        String loginCredentialsJson = "";

        JSONObject loginCredentials = new JSONObject();
        loginCredentials.put("email", "test@test.it");
        loginCredentials.put("password", "123456789");

        HttpEntity<String> request = new HttpEntity<>(loginCredentials.toString());

        ResponseEntity response = testTemplate.postForEntity("/users/login", request, null);

//        Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(),
        "Http Status should be 200");
        Assertions.assertNotNull(response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0), "Response should contain auth header");
        Assertions.assertNotNull(response.getHeaders().getValuesAsList("UserId").get(0), "Response should contain user ID");
    }
}
