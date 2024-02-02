package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;


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

    @Test
    void contextLoads() {
        System.out.println("server.port " + serverPort);
        System.out.println("running server port " + localServerPort);
    }


}
