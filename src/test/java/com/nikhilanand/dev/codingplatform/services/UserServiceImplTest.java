package com.nikhilanand.dev.codingplatform.services;

import com.nikhilanand.dev.codingplatform.exchanges.GetUserResponse;
import com.nikhilanand.dev.codingplatform.model.UserEntity;
import com.nikhilanand.dev.codingplatform.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    UserEntity userEntity = new UserEntity();

    @BeforeEach
    void beforeAll() {

        userEntity.setUserId("userId123");
        userEntity.setUsername("username");
        userEntity.setScore(0);
        userEntity.setBadges(new HashSet<>());
    }

    @Test
    void getUser() {

        when(userRepository.findByUserId("userId123")).thenReturn(Optional.of(userEntity));
        GetUserResponse getUserResponse = userService.getUser("userId123");
        UserEntity user = getUserResponse.getUserDetails();

        assertEquals("userId123", user.getUserId());
        assertEquals("username", user.getUsername());
        assertEquals(0, user.getScore());
    }

    @Test
    void updateUserScore() {

        when(userRepository.findByUserId("userId123")).thenReturn(Optional.of(userEntity));
        GetUserResponse getUserResponse = userService.getUser("userId123");
        UserEntity user = getUserResponse.getUserDetails();

        assertEquals(0, user.getScore());

        //after score update
        userEntity.setScore(60);
        when(userRepository.save(any())).thenReturn(userEntity);

        getUserResponse = userService.updateUserScore("userId123", 60);
        user = getUserResponse.getUserDetails();
        assertEquals(60, user.getScore());

    }
}