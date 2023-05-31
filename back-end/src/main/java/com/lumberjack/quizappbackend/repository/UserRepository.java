package com.lumberjack.quizappbackend.repository;

import com.lumberjack.quizappbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    Optional<User> findUserById(String id);
    boolean deleteUserById(String id);
}
