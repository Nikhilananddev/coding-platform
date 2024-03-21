package com.nikhilanand.dev.codingplatform.services;

import com.nikhilanand.dev.codingplatform.exchanges.AddUserRequest;
import com.nikhilanand.dev.codingplatform.exchanges.AddUserResponse;
import com.nikhilanand.dev.codingplatform.exchanges.GetUserResponse;
import com.nikhilanand.dev.codingplatform.exchanges.GetUsersResponse;


public interface UserService {


    GetUsersResponse getUsers();

    GetUserResponse getUser(String userId);

    AddUserResponse addUser(AddUserRequest addUserRequest);

    GetUserResponse updateUserScore(String userId, Integer score);

    String unregisterFromContest(String userId);
}
