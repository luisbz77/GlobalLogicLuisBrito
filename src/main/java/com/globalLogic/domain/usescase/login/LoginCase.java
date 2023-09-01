package com.globalLogic.domain.usescase.login;

import com.globalLogic.domain.exception.BusinessException;
import com.globalLogic.domain.exception.InvalidEmailException;
import com.globalLogic.domain.exception.UserIsNullExeption;
import com.globalLogic.domain.gateway.UserGateway;
import com.globalLogic.domain.model.user.LoginRequest;
import com.globalLogic.domain.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class LoginCase {

    private static final Logger logger = LoggerFactory.getLogger(LoginCase.class);
    private final UserGateway userGateway;

    public LoginCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User login(LoginRequest loginRequest){
        validate(loginRequest);
        logger.info("Correo del Usuario: {}",loginRequest.getEmail());
        logger.info("Password del Usuario: {}",loginRequest.getPassword());
        return userGateway.authenticate(loginRequest.getEmail(),loginRequest.getPassword());
    }

    private void validate(LoginRequest loginRequest){
        if (Objects.isNull(loginRequest))
            throw new UserIsNullExeption("Usuario nulo");
        if (!userGateway.validEmail(loginRequest.getEmail()))
            throw new InvalidEmailException("Datos del correo invalidos");
        if(Objects.isNull(loginRequest.getPassword()) || loginRequest.getPassword().isEmpty())
            throw new BusinessException("Datos invalidos");
    }
}
