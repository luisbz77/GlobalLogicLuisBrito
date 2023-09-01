package com.globalLogic.domain.gateway;

import com.globalLogic.domain.model.user.User;


public interface UserGateway {

    User authenticate (String email, String password);

    boolean validEmail (String email);

    boolean validPassword(String password);

    User createdUser (User user);

    User updateUser (User user);

    User getUserByToken(String token);
}
