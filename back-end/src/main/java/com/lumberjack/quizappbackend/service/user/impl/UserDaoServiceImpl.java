package com.lumberjack.quizappbackend.service.user.impl;

import com.lumberjack.quizappbackend.model.User;
import com.lumberjack.quizappbackend.service.user.UserService;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserDaoServiceImpl implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate;
    private static final String USERS_COLLECTION_NAME = "users";

    @Override
    public List<User> getAllUsers() {
        log.info("Getting all users");
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public Optional<User> getUserById(String id) {
        log.info("Getting user by id:{}", id);
        Query query = new Query(Criteria.where("id").is(id));
        return Optional.of(mongoTemplate.findOne(query, User.class));
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        log.info("Getting user by username:{}", username);
        Query query = new Query(Criteria.where("username").is(username));
        return Optional.of(mongoTemplate.findOne(query, User.class));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        log.info("Getting user by email:{}", email);
        Query query = new Query(Criteria.where("email").is(email));
        return Optional.of(mongoTemplate.findOne(query, User.class));
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user: {}", user);
        return mongoTemplate.save(user, USERS_COLLECTION_NAME);
    }

    @Override
    public User updateUser(User user) {
        log.info("Updating new user: {}", user);
        return mongoTemplate.save(user, USERS_COLLECTION_NAME);
    }

    @Override
    public boolean deleteUser(String id) {
        log.info("Deleting new user with id: {}", id);
        Optional<User> userOptional = getUserById(id);
        if (userOptional.isPresent()) {
            DeleteResult remove = mongoTemplate.remove(userOptional.get());
            return remove.wasAcknowledged();
        } else {
            throw new UsernameNotFoundException("Failed to delete user with id" + id + ". User was not found!");
        }
    }

    @Override
    public List<User> searchUsers(String searchTerm) {
        return null;
    }
}
