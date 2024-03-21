package com.nikhilanand.dev.codingplatform.services;

import com.nikhilanand.dev.codingplatform.dto.UserDto;
import com.nikhilanand.dev.codingplatform.exceptions.UserEntityNotExistException;
import com.nikhilanand.dev.codingplatform.exceptions.UserIdAlreadyExistsException;
import com.nikhilanand.dev.codingplatform.exceptions.UserNameAlreadyExistsException;
import com.nikhilanand.dev.codingplatform.exceptions.UserNotFoundException;
import com.nikhilanand.dev.codingplatform.exchanges.AddUserRequest;
import com.nikhilanand.dev.codingplatform.exchanges.AddUserResponse;
import com.nikhilanand.dev.codingplatform.exchanges.GetUserResponse;
import com.nikhilanand.dev.codingplatform.exchanges.GetUsersResponse;
import com.nikhilanand.dev.codingplatform.model.UserEntity;
import com.nikhilanand.dev.codingplatform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Override
    public GetUsersResponse getUsers() {

        Optional<List<UserEntity>> optionalUserEntities = userRepository.findAllByOrderByScoreDesc();

        if (optionalUserEntities.isPresent()) {
            List<UserEntity> userEntities = optionalUserEntities.get();
            GetUsersResponse getUsersResponse = new GetUsersResponse(userEntities);
            return getUsersResponse;
        } else try {
            throw new UserEntityNotExistException("No userEntity exist");
        } catch (UserEntityNotExistException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public GetUserResponse getUser(String userId) {
        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);

        if (userEntity.isPresent()) {
            GetUserResponse getUserResponse = new GetUserResponse(userEntity.get());

            return getUserResponse;
        } else {
            try {
                throw new UserNotFoundException("User Not Found with userId " + userId);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public AddUserResponse addUser(AddUserRequest addUserRequest) {

        UserDto userDto = addUserRequest.getUserDetails();
        String userName = userDto.getUsername();
        String userId = userDto.getUserId();

        boolean isUseIdExist = userRepository.existsById(userId);
        boolean isUserNameExist = userRepository.existsByUsername(userName);

        if (isUseIdExist) {
            try {
                throw new UserIdAlreadyExistsException(userId + " userId is already exist");
            } catch (UserIdAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }

        if (isUserNameExist) {
            try {
                throw new UserNameAlreadyExistsException(userName + " userName is already exist");
            } catch (UserNameAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDto.getUserId());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setScore(0);
        userEntity.setBadges(new HashSet<>());
        userEntity = userRepository.save(userEntity);
        AddUserResponse addUserResponse = new AddUserResponse(userEntity);
        return addUserResponse;
    }

    @Override
    public GetUserResponse updateUserScore(String userId, Integer score) {

        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);

        if (userEntity.isPresent()) {
            UserEntity user = userEntity.get();
            Set<String> userBadge = user.getBadges();

            if (score >= 1 && score <= 30) {
                userBadge.add("Code Ninja");
                user.setScore(score);
                user.setBadges(userBadge);
                user = userRepository.save(user);

            } else if (score >= 30 && score <= 60) {
                userBadge.add("Code Ninja");
                userBadge.add("Code Champ");
                user.setScore(score);
                user.setBadges(userBadge);
                user = userRepository.save(user);
            } else if (score >= 60 && score <= 100) {
                userBadge.add("Code Champ");
                user.setScore(score);
                userBadge.add("Code Master");
                user.setBadges(userBadge);
                user = userRepository.save(user);
            }
            System.out.println(new GetUserResponse(user));
            return new GetUserResponse(user);
        } else {
            try {
                throw new UserNotFoundException("User Not Found with userId " + userId);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public String unregisterFromContest(String userId) {

        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);

        if (userEntity.isPresent()) {

            userRepository.delete(userEntity.get());
            return "User is Deregister from contest";
        } else {
            try {
                throw new UserNotFoundException("User Not Found with userId " + userId);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
