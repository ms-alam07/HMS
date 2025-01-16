package com.hms.Config;

import com.auth0.jwt.JWT;
import com.hms.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// URL with tokens stored in URL header will come to this particular class.
import java.io.IOException;
@Component
//OncePerRequestFilter is an abstract class that implements a filter that returns tokens that match the
// request pattern
public class JwtFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    public JwtFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         String token = request.getHeader("Authorization");
         String JwtToken = token.substring(7);
//        System.out.println(JwtToken);
        String username = jwtService.getUsername(JwtToken);
        System.out.println(username);
    }
}
