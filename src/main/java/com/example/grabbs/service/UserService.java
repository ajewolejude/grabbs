package com.example.grabbs.service;

import com.example.grabbs.dto.UserDto;
import com.example.grabbs.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    void editProfile(UserDto userDto);

    User findUserByEmail(String email);

    User findUserById(Long id);

    List<UserDto> findAllUsers();

    List<User> getAllUsers();

    boolean isUserAuthenticated();

    void enableUser(User user);

    void disableUser(User user);
}