package com.alejandroLopez.UTILS;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;

public class YourJwtUtil {
    // Define el tiempo de expiración del token
    private static final long EXPIRATION_TIME = 3600000; // 1 hora en milisegundos
    private String jwtSecret;

    public YourJwtUtil(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String generateToken(String username) {
        // Decodificar la clave desde Base64
        byte[] decodedKey = Base64.getDecoder().decode(this.jwtSecret);
        SecretKey secretKey = Keys.hmacShaKeyFor(decodedKey); // Crear la clave usando Keys

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Define el tiempo de expiración
                .signWith(SignatureAlgorithm.HS256, secretKey) // Usa la clave segura
                .compact();
    }
}

