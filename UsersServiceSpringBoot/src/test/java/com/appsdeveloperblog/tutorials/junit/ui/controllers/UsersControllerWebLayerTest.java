package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.service.UsersService;
import com.appsdeveloperblog.tutorials.junit.service.UsersServiceImpl;
import com.appsdeveloperblog.tutorials.junit.shared.UserDto;
import com.appsdeveloperblog.tutorials.junit.ui.request.UserDetailsRequestModel;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UsersController.class,
excludeAutoConfiguration = {SecurityAutoConfiguration.class})
//@AutoConfigureMockMvc(addFilters = false)
//@MockBean({UsersServiceImpl.class})
public class UsersControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
//    @Autowired + The MockBean at the class level in case we need to specify which class is implemented
    UsersService usersService;

    @Test
    @DisplayName("User can be created")
    void testCreateUser_whenValidUserDetailsProvided_returnsCreatedUserDetails() throws Exception {
    //        Arrange
        UserDetailsRequestModel userDetailReq = new UserDetailsRequestModel();
        userDetailReq.setFirstName("Ili");
        userDetailReq.setLastName("Scalco");
        userDetailReq.setEmail("test@festiSounds.com");
        userDetailReq.setPassword("12345678");
        userDetailReq.setRepeatPassword("12345678");

        UserDto userDto = new ModelMapper().map(userDetailReq, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDetailReq));

    //        Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();

        UserRest createdUser = new ObjectMapper().readValue(responseBodyAsString, UserRest.class);

    //        Assert
        Assertions.assertEquals(userDetailReq.getFirstName(), createdUser.getFirstName(),
                () -> "The returned first name is incorrect");
        Assertions.assertEquals(userDetailReq.getLastName(), createdUser.getLastName(),
                () -> "The returned last name is incorrect");
        Assertions.assertEquals(userDetailReq.getEmail(), createdUser.getEmail(),
                () -> "The returned email is incorrect");
        Assertions.assertFalse(createdUser.getUserId().isEmpty(),
                () -> "UserId should not be empty");
    }

    @Test
    @DisplayName("First name is not empty")
    void testCreateUser_whenFirstNameIsNotProvided_Returns400() throws Exception {
        //        Arrange
        UserDetailsRequestModel userDetailReq = new UserDetailsRequestModel();
        userDetailReq.setFirstName("");
        userDetailReq.setLastName("Scalco");
        userDetailReq.setEmail("test@festiSounds.com");
        userDetailReq.setPassword("12345678");
        userDetailReq.setRepeatPassword("12345678");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDetailReq));
//        Act
       MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

//       Assert

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(),
                "Incorrect HTTP status");
    }
}
