package com.hms.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiration.duration}")
    private Long expiry;
    private Algorithm algorithm;


    @PostConstruct      // It is Jakarta Annotation. Automatically Load Any method.
    public void postConstruct() throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(algorithmKey); // HMAC throws UnsupportedEncodingException
    }
    public String generateToken(String username) {
        Date expirationTime = new Date(System.currentTimeMillis() + expiry);
        return JWT.create()
                .withClaim("name", username)
                .withExpiresAt(expirationTime)  // Set correct expiration time
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUsername(String token) {
        try {
            DecodedJWT decoded = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
            return decoded.getClaim("name").asString();

        } catch (Exception e) {
            throw new RuntimeException("Failed to decode or verify JWT: " + e.getMessage(), e);
        }
    }



}
