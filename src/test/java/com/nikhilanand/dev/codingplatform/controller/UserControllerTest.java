package com.nikhilanand.dev.codingplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nikhilanand.dev.codingplatform.dto.UserDto;
import com.nikhilanand.dev.codingplatform.exchanges.AddUserRequest;
import com.nikhilanand.dev.codingplatform.exchanges.AddUserResponse;
import com.nikhilanand.dev.codingplatform.exchanges.GetUserResponse;
import com.nikhilanand.dev.codingplatform.exchanges.UpdateUserScoreRequest;
import com.nikhilanand.dev.codingplatform.model.UserEntity;
import com.nikhilanand.dev.codingplatform.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    UserController userController;

    UserEntity userEntity = new UserEntity();

    @BeforeEach
    void beforeAll() {

        userEntity.setUserId("userId123");
        userEntity.setUsername("username");
        userEntity.setScore(0);
        userEntity.setBadges(new HashSet<>());
    }

    @Test
    void getUserDetails() {

        when(userService.getUser("userId123")).thenReturn(new GetUserResponse(userEntity));

        ResponseEntity<GetUserResponse> getUserResponse = userController.getUserDetails("userId123");

        assertEquals(HttpStatus.OK, getUserResponse.getStatusCode());

        UserEntity user = getUserResponse.getBody().getUserDetails();

        assertEquals("userId123", user.getUserId());
        assertEquals("username", user.getUsername());
        assertEquals(0, user.getScore());


    }

    @Test
    void updateUsersScore() {

        userEntity.setScore(60);
        when(userService.updateUserScore("userId123", 60)).thenReturn(new GetUserResponse(userEntity));
        UpdateUserScoreRequest updateUserScoreRequest = new UpdateUserScoreRequest(60);


        ResponseEntity<GetUserResponse> getUserResponse = userController.updateUsersScore("userId123", updateUserScoreRequest);

        assertEquals(HttpStatus.OK, getUserResponse.getStatusCode());

        UserEntity user = getUserResponse.getBody().getUserDetails();

        assertEquals("userId123", user.getUserId());
        assertEquals("username", user.getUsername());
        assertEquals(60, user.getScore());


    }

    @Test
    void addUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId("user124");
        userDto.setUsername("postman2");
        userDto.setScore(0);
        userDto.setBadges(new HashSet<>());

        AddUserRequest addUserRequest = new AddUserRequest(userDto);
        AddUserResponse addUserResponse = new AddUserResponse(userEntity);
        when(userService.addUser(addUserRequest)).thenReturn(addUserResponse);

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode json = mapper.createObjectNode();
        ObjectNode jsonUser = mapper.createObjectNode();
        String userId = UUID.randomUUID().toString();
        String userName = UUID.randomUUID().toString();

        jsonUser.put("userId", userId);
        jsonUser.put("username", userName);
        json.put("userDetails", jsonUser);

        String jsonString = mapper.writeValueAsString(json);

        //live test
        mockMvc.perform(MockMvcRequestBuilders.post("https://localhost:8080/codingninja/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
        ).andExpect(MockMvcResultMatchers.status().isCreated());


        ResponseEntity<AddUserResponse> response = userController.addUser(addUserRequest);
        UserEntity user = response.getBody().getUserDetails();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("userId123", user.getUserId());
        assertEquals("username", user.getUsername());
        assertEquals(0, user.getScore());

    }

    @Test
    void deleteUser() throws Exception {

        when(userService.unregisterFromContest("userId123")).
                thenReturn("User is Deregister from contest");

        ResponseEntity<String> response = userController.deleteUser("userId123");

        String message = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User is Deregister from contest", message);
    }
}