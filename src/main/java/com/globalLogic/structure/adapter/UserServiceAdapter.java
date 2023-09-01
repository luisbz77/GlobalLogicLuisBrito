package com.globalLogic.structure.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globalLogic.domain.exception.*;
import com.globalLogic.domain.gateway.AuthenticationGateway;
import com.globalLogic.domain.gateway.UserGateway;
import com.globalLogic.domain.model.user.User;
import com.globalLogic.structure.data.UserRepository;
import com.globalLogic.structure.entity.user.UserEntity;
import com.globalLogic.structure.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserServiceAdapter implements UserGateway {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceAdapter.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationGateway authenticationGateway;
    private final ObjectMapper objectMapper;
    private final String secret;

    public UserServiceAdapter(UserRepository userRepository, UserMapper userMapper, AuthenticationGateway authenticationGateway, ObjectMapper objectMapper, String secret) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationGateway = authenticationGateway;
        this.objectMapper = objectMapper;
        this.secret = secret;
    }

    public User createdUser(User user){
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        if (Objects.nonNull(userEntity))
            throw new UserAlreadyExistsException("Usuario ya registrado con este correo electrónico.");
        Optional.ofNullable(user.getEmail())
                .map(authenticationGateway::authorize)
                .ifPresent(user::setToken);
        return userMapper.toModel(userRepository.save(userMapper.toEntity(user)));
    }

    @Transactional
    public User updateUser(User user){
        try {
            String phones = objectMapper.writeValueAsString(user.getPhones());
             int count =  userRepository.updateUser(user.getEmail(),
                                                    user.isActive(),
                                                    new Timestamp(System.currentTimeMillis()),
                                                    user.getName(),
                                                    user.getPassword(),phones);
             if ( count == 1 ) {
                 return userMapper.toModel(userRepository.findByEmail(user.getEmail()));
             }
            return null;
        }catch(Exception e){
            throw new PhoneException("No se pudo procesar el teléfono");
        }
    }

    @Override
    public User authenticate(String email, String password) {
        logger.info("correo: {}",email);
        logger.info("password: {}",password);
        UserEntity entity = userRepository.findByEmail(email);
        if(Objects.isNull(entity))
            throw new UserNotFoundException("Usuario no encontrado");
        if (!entity.getPassword().equals(password))
            throw new PasswordNotMatchException("Datos incorrectos");
        return userMapper.toModel(entity);
    }

    public boolean validEmail(String email) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validPassword(String password){
        Pattern pattern = Pattern.compile("(^[A-Z][a-z]+(\\d){2}$)|(^[a-z]+[A-Z](\\d){2}$)|(^(\\d){2}[A-Z]{1}[a-z]+$)|(^[a-z]+(\\d){2}[A-Z])");
        Matcher matcher = pattern.matcher(password);
        return  matcher.matches();
    }

    @Override
    public User getUserByToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            User user = User.builder()
                    .id(UUID.fromString(claims.getSubject())) // El ID debería estar en el subject del token
                    .created(new Timestamp(claims.getIssuedAt().getTime()))
                    .lastLogin(new Timestamp(claims.getIssuedAt().getTime())) // Usar el mismo tiempo para lastLogin por ahora
                    .token(token)
                    .isActive(Boolean.parseBoolean(claims.get("isActive").toString()))
                    .name(claims.get("name", String.class))
                    .email(claims.get("email", String.class))
                    .build();

            return user;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Token inválido");
        }
    }
}
