package com.inspirit.service;

import com.inspirit.dto.CreateUserRequest;
import com.inspirit.dto.UpdateUserRequest;
import com.inspirit.exceptions.IncorrectUserId;
import com.inspirit.model.Role;
import com.inspirit.model.User;
import com.inspirit.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.*;

import static java.util.stream.Collectors.toSet;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public User saveUser(CreateUserRequest request) {
        log.info("In user_service save new user");

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ValidationException("Passwords don't match!");
        }
        if (request.getAuthorities() == null) {
            request.setAuthorities(new HashSet<>());
        }

        User user = new User().setUsername(request.getUsername())
                .setAuthorities(request.getAuthorities().stream().map(Role::new).collect(toSet()));

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);

        return user;
    }

    @Transactional
    public User update(String id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(IncorrectUserId::new);
        user.setUsername(request.getUsername());
        user.setAuthorities(request.getAuthorities().stream().map(Role::new).collect(toSet()));

        user = userRepository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        log.info("In user_service get all users");
        return userRepository.findAll();
    }

    public Optional<User> findById(String id) {
        log.info("In user_service get by id");
        return userRepository.findById(id);
    }


    public void deleteById(String id) {
        log.info("In user_service delete by id");
        userRepository.deleteById(id);
    }

}

