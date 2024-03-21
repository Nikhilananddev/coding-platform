package com.nikhilanand.dev.codingplatform.exceptions;

import com.nikhilanand.dev.codingplatform.dto.UserDto;
import com.nikhilanand.dev.codingplatform.exchanges.AddUserRequest;
import com.nikhilanand.dev.codingplatform.exchanges.AddUserResponse;
import com.nikhilanand.dev.codingplatform.model.UserEntity;
import com.nikhilanand.dev.codingplatform.repositories.UserRepository;
import com.nikhilanand.dev.codingplatform.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class GlobalExceptionHandlerTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;


    @Test
    void handleUserNotFoundException() {

        when(userRepository.findByUserId("userId123")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getUser("userId123");
        });


    }


    @Test
    void handleUserNameAlreadyExistException() {
        UserDto userDto = new UserDto();
        userDto.setUserId("userId123");
        userDto.setUsername("username");
        userDto.setScore(0);
        userDto.setBadges(new HashSet<>());

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("userId123");
        userEntity.setUsername("username");
        userEntity.setScore(0);
        userEntity.setBadges(new HashSet<>());


        when(userRepository.save(any())).thenThrow(new RuntimeException("userId123 userName is already exist"));

        AddUserRequest userRequest = new AddUserRequest(userDto);
        AddUserResponse addUserResponse = new AddUserResponse(userEntity);
        assertThrows(RuntimeException.class, () -> {
            userService.addUser(userRequest);
        });


    }


}