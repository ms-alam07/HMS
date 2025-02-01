package com.hms.Config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private  JwtFilter jwtFilter;
    public  SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()

                // URLs with token will come to jwtFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // URLs without token will come here to permitAll()
                .authorizeHttpRequests()

                .requestMatchers("/api/hms/user/signup", "/api/hms/user/login", "/api/hms/user/property-owner/signup").permitAll()
                .requestMatchers("api/hms/user/property-owner/addProperty").hasRole("PROPERTY_OWNER")
                .requestMatchers("api/v1/user/deleteProperty").hasAnyRole("PROPERTY_OWNER","ADMIN")
                .requestMatchers("api/v1/user/blog/signup").hasRole("ADMIN")
                .anyRequest().authenticated();
        return http.build();
    }
}
