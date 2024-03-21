package com.nikhilanand.dev.codingplatform.controller;


import com.nikhilanand.dev.codingplatform.exchanges.*;
import com.nikhilanand.dev.codingplatform.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(UserController.CODING_NINJA_API_ENDPOINT)
public class UserController {

    static final String CODING_NINJA_API_ENDPOINT = "codingninja/";
    @Autowired
    UserService userService;


    @GetMapping("/users")
    ResponseEntity<GetUsersResponse> getUsers() {

        GetUsersResponse usersResponse = userService.getUsers();


        if (usersResponse != null) {
            return ResponseEntity.ok().body(usersResponse);
        } else return ResponseEntity.badRequest().body(null);

    }


    @GetMapping("/users/{userId}")
    ResponseEntity<GetUserResponse> getUserDetails(@PathVariable String userId) {

        GetUserResponse userResponse = userService.getUser(userId);

        if (userResponse != null) {
            return ResponseEntity.ok().body(userResponse);
        } else return ResponseEntity.badRequest().body(null);

    }


    @PostMapping("/users")
    ResponseEntity<AddUserResponse> addUser(@Valid @RequestBody AddUserRequest addUserRequest) {

        if (addUserRequest == null || addUserRequest.getUserDetails() == null)
            return ResponseEntity.badRequest().body(null);

        AddUserResponse usersResponse = userService.addUser(addUserRequest);

        if (usersResponse != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(usersResponse);
        } else return ResponseEntity.badRequest().body(null);

    }

    @PutMapping("/users/{userId}")
    ResponseEntity<GetUserResponse> updateUsersScore(@PathVariable String userId, @Valid @RequestBody UpdateUserScoreRequest updateUserScoreRequest) {

        if (updateUserScoreRequest == null)
            return ResponseEntity.badRequest().body(null);

        GetUserResponse userResponse = userResponse = userService.updateUserScore(userId, updateUserScoreRequest.getScore());

        if (userResponse != null) {
            return ResponseEntity.ok().body(userResponse);
        } else return ResponseEntity.badRequest().body(null);

    }

    @DeleteMapping("/users/{userId}")
    ResponseEntity<String> deleteUser(@Valid @PathVariable String userId) {

        String response = null;
        response = userService.unregisterFromContest(userId);

        if (response != null) {
            return ResponseEntity.ok().body(response);
        } else return ResponseEntity.badRequest().body(null);

    }


}
