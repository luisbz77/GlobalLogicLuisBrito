package com.globalLogic.structure.controller;

import com.globalLogic.domain.model.user.User;
import com.globalLogic.domain.usescase.user.UserCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserCase userCase;

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User createdUser = userCase.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping
    public User updateUser(@RequestBody User user){
        logger.info("Usuario para actualizar {}",user);
        return userCase.updateUser(user);
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userCase.getUserByEmail(email);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userCase.getAllUsers();
    }

    @GetMapping("/user")
    public User getUserByToken(@RequestHeader("Authorization") String tokenHeader) {
        String token = extractTokenFromHeader(tokenHeader);
        return userCase.getUserByToken(token);
    }

    private String extractTokenFromHeader(String tokenHeader) {
        if (tokenHeader != null && tokenHeader.startsWith("Bearer")) {
            return tokenHeader.substring(7);
        }
        return null;
    }

}
