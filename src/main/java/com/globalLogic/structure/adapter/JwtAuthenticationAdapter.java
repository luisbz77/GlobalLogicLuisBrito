package com.globalLogic.structure.adapter;

import com.globalLogic.domain.gateway.AuthenticationGateway;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtAuthenticationAdapter implements AuthenticationGateway {

    private final String secret;

    public JwtAuthenticationAdapter(String secret) {
        this.secret = secret;
    }

    @Override
    public String authorize(String email) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder().setSubject(email).signWith(key).compact();
    }

}
