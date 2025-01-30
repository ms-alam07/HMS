package com.hms.Config;

import com.hms.Entity.User;
import com.hms.Repository.UserRepository;
import com.hms.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    // It is an abstract class in java which has an incomplete method
    private JWTService jwtService;
    private final UserRepository userRepository;

    public JwtFilter(JWTService jwtService,
                     UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Allow the request to pass for endpoints not requiring auth
            return;
        }

        try {
            String jwtToken = token.substring(8,token.length() -1);
            String username = jwtService.getUsername(jwtToken);
            Optional<User> opUser = userRepository.findByUsername(username);
            if(opUser.isPresent()) {
                User user = opUser.get();

            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired JWT token");
        }
    }



}
