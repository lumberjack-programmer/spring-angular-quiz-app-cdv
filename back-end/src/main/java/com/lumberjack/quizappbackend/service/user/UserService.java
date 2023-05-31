package com.lumberjack.quizappbackend.service.user;

import com.lumberjack.quizappbackend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(String id);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    User saveUser(User user);
    User updateUser(User user);
    boolean deleteUser(String id);
    List<User> searchUsers(String searchTerm);
}
