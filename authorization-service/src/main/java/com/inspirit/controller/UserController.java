package com.inspirit.controller;

import com.inspirit.config.JwtTokenUtil;
import com.inspirit.dto.AuthRequest;
import com.inspirit.dto.CreateUserRequest;
import com.inspirit.dto.UpdateUserRequest;
import com.inspirit.exceptions.IncorrectUserId;
import com.inspirit.model.User;
import com.inspirit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User user = (User) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user))
//                    .header("88","898") here I can set refresh token
                    .body(user);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/check")
    public ResponseEntity<User> check(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.split(" ")[1].trim();

            String id = jwtTokenUtil.getUserId(token);


            User user = userService.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(
                        format("User: %s, not found", id)
                        )
                );

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, bearerToken)
                    .body(user);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody CreateUserRequest createUserRequest){
        log.info("In user_controller save new user");
        return new ResponseEntity<> (userService.saveUser(createUserRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        log.info("In user_controller get all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        log.info("In user_controller get user by id");
        Optional<User> optionalUser = userService.findById(id);

        return optionalUser.map(ResponseEntity::ok).orElseThrow(IncorrectUserId::new);

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody CreateUserRequest createUserRequest) {
        log.info("In user_controller update user");
        Optional<User> optionalUser = userService.findById(id);

        if (!optionalUser.isPresent()) {
            throw new IncorrectUserId("There is no User with such id. Try to add them instead of update");
        }

        UpdateUserRequest updateUserRequest = new UpdateUserRequest()
                .setUsername(createUserRequest.getUsername()).setAuthorities(createUserRequest.getAuthorities());

        return new ResponseEntity<>(userService.update(id, updateUserRequest), HttpStatus.OK);
    }

}
