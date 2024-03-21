package com.nikhilanand.dev.codingplatform.repositories;

import com.nikhilanand.dev.codingplatform.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<List<UserEntity>> findAllByOrderByScoreDesc();

    Optional<UserEntity> findByUserId(String userId);

    boolean existsByUsername(String username);

}
