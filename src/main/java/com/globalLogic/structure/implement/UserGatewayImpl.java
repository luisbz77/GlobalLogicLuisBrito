package com.globalLogic.structure.implement;

import com.globalLogic.domain.gateway.UserGateway;
import com.globalLogic.domain.model.user.User;
import com.globalLogic.structure.data.UserRepository;
import com.globalLogic.structure.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserGatewayImpl implements UserGateway {

    private final UserRepository userRepository; // Asegúrate de inyectar el repositorio aquí

    @Autowired
    public UserGatewayImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User authenticate(String email, String password) {
        return null;
    }

    @Override
    public boolean validEmail(String email) {
        return false;
    }

    @Override
    public boolean validPassword(String password) {
        return false;
    }

    @Override
    public User createdUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User getUserByToken(String token) {
        return null;
    }
}

