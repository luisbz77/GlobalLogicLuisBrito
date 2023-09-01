package com.globalLogic.domain.usescase.user;

import com.globalLogic.domain.exception.InvalidEmailException;
import com.globalLogic.domain.exception.InvalidFormatException;
import com.globalLogic.domain.exception.UserExistException;
import com.globalLogic.domain.exception.UserNotFoundException;
import com.globalLogic.domain.gateway.UserGateway;
import com.globalLogic.domain.model.user.User;
import com.globalLogic.structure.adapter.UserMapperAdapter;
import com.globalLogic.structure.data.UserRepository;
import com.globalLogic.structure.entity.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserCase {
    private static final Logger logger = LoggerFactory.getLogger(UserCase.class);
    private final UserGateway userGateway;
    private final UserRepository userRepository;
    private final UserMapperAdapter userMapper;

    public UserCase(UserGateway userGateway, UserRepository userRepository, UserMapperAdapter userMapper) {
        this.userGateway = userGateway;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User createUser(User user){
        validate(user);
        User response = userGateway.createdUser(user);
        if (Objects.isNull(response))
            throw new UserExistException("Usuario ya existe");
        logger.info("Usuario creado {}",response);
        return response;
    }

    public User updateUser(User user){
        validate(user);
        User response = userGateway.updateUser(user);
        if (Objects.isNull(response))
            throw new UserNotFoundException("Usuario no encontrado");
        logger.info("Usuario actualizado {}",response);
        return response;
    }

    private void validate(User user){
        logger.info("Correo de Usuario: {}",user.getEmail());
        if (!userGateway.validEmail(user.getEmail()))
            throw new InvalidEmailException("Datos del correo invalidos");
        logger.info("Password del Usuario: {}",user.getPassword());
        if (!userGateway.validPassword(user.getPassword()))
            throw new InvalidFormatException("Validación del password incorrectos");
    }

    public User getUserByEmail(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UserNotFoundException("User not found");
        }

        return userMapper.toModel(userEntity);
    }

    public List<User> getAllUsers() {
        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll();
        return userEntities.stream()
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    public User getUserByToken(String token) {
        return userGateway.getUserByToken(token);
    }

}
