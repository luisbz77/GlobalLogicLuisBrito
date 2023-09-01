package com.globalLogic.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globalLogic.domain.gateway.AuthenticationGateway;
import com.globalLogic.domain.gateway.UserGateway;
import com.globalLogic.domain.usescase.login.LoginCase;
import com.globalLogic.domain.usescase.user.UserCase;
import com.globalLogic.structure.adapter.JwtAuthenticationAdapter;
import com.globalLogic.structure.adapter.UserMapperAdapter;
import com.globalLogic.structure.data.UserRepository;
import com.globalLogic.structure.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UseCaseConfig {

    @Bean
    public AuthenticationGateway authenticationGateway(@Value("${jwt.secret}") String secret){
        return new JwtAuthenticationAdapter(secret);
    }

    @Bean
    public UserCase userCase(UserGateway userGateway, UserRepository userRepository, UserMapperAdapter userMapper){

        return new UserCase(userGateway, userRepository, userMapper);
    }

    @Bean
    public LoginCase loginCase(UserGateway userGateway){

        return new LoginCase(userGateway);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserMapper userMapper(ObjectMapper objectMapper){
        return (UserMapper) new UserMapperAdapter(objectMapper);
    }
}
