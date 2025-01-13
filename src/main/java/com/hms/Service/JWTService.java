package com.hms.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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
    private String expiry;
    private Algorithm algorithm;


    @PostConstruct      // It is Jakarta Annotation. Automatically Load Any method.
    public void postConstruct() throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(algorithmKey); // HMAC throws UnsupportedEncodingException
//        System.out.println(algorithmKey);
//        System.out.println(issuer);
//        System.out.println(expiry);
    }
    public String genrateToken(String username){
         return JWT.create().
                withClaim("name",username).
                withExpiresAt(new Date(System.currentTimeMillis()+expiry)).
                withIssuer(issuer).
                sign(algorithm);
    }

}
