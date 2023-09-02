package com.example.grabbs.service;

import com.example.grabbs.dto.UserDto;
import com.example.grabbs.model.Role;
import com.example.grabbs.model.User;
import com.example.grabbs.repository.RoleRepository;
import com.example.grabbs.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setEmail(userDto.getEmail());
        user.setState(userDto.getState());
        user.setPhone(userDto.getPhone());
        user.setCreatedDate(LocalDateTime.now());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName(userDto.getRole());
        if(role == null){
            role = createRole(userDto.getRole());
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public void editProfile(UserDto userDto) {

        User existingUser = userRepository.findByEmail(userDto.getEmail());
        //existingUser.setPhone();

    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        boolean isAuthenticated = authentication.isAuthenticated();

        if (isAuthenticated) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void enableUser(User user) {
        user.setState("active");
        userRepository.save(user);
    }

    @Override
    public void disableUser(User user) {
        user.setState("inactive");
        userRepository.save(user);
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setState(user.getState());
        userDto.setRole(user.getRoles().get(0).getName());
        userDto.setPhone(user.getPhone());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role createRole(String newRole){
        Role role = new Role();
        role.setName(newRole);
        return roleRepository.save(role);
    }
}