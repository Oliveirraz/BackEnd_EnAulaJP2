package com.example.enaula.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "enaula-chave-secreta-para-jwt-com-pelo-menos-256-bits";

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60; // 1 hora

    private final SecretKey key = Keys.hmacShaKeyFor(
            SECRET_KEY.getBytes(StandardCharsets.UTF_8)
    );

    public String gerarToken(String email, String tipo) {

        return Jwts.builder()
                .subject(email)
                .claim("tipo", tipo)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(key)
                .compact();
    }

    public String extrairEmail(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extrairTipo(String token) {

        return (String) Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("tipo");
    }

    public boolean tokenValido(String token) {

        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}