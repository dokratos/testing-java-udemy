package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


//this will not load the whole context, but use mock object:::
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//the properties value can be added to the springboottest annotation
@TestPropertySource(locations = "/application-test.properties", properties = "server.port=8080")
public class UsersControllerIntegrationTest {

    @Value("${server.port}")
    private int serverPort;

    @Test
    void contextLoads() {
        System.out.println("server.port " + serverPort);
    }


}
