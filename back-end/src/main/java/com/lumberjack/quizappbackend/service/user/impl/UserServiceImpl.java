package com.lumberjack.quizappbackend.service.user.impl;

import com.lumberjack.quizappbackend.repository.UserRepository;
import com.lumberjack.quizappbackend.model.User;
import com.lumberjack.quizappbackend.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        log.info("Getting user by username:{}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(String id) {
        log.info("Getting user by id:{}", id);
        return userRepository.findUserById(id);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user: {}", user);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        log.info("Updating user: {}", user);
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(String id) {
        log.info("Deleting user with id: {}", id);
        return userRepository.deleteUserById(id);
    }

    @Override
    public List<User> searchUsers(String searchTerm) {
        return null;
    }
}
