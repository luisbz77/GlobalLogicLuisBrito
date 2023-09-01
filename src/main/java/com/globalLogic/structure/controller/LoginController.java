package com.globalLogic.structure.controller;

import com.globalLogic.domain.model.user.LoginRequest;
import com.globalLogic.domain.model.user.User;
import com.globalLogic.domain.usescase.login.LoginCase;
import com.globalLogic.structure.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final LoginCase loginCase;

    @PostMapping
    public UserResponse doLogin(@RequestBody LoginRequest loginRequest){
        User user = loginCase.login(loginRequest);
        return mapToUserResponse(user);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId().toString());
        userResponse.setCreated(user.getCreated().toString());
        userResponse.setLastLogin(user.getLastLogin().toString());
        userResponse.setToken(user.getToken());
        userResponse.setActive(user.isActive());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setPhones(user.getPhones());
        return userResponse;
    }
}

