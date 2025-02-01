package com.hms.Config;

import com.hms.Config.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()

                // Public Endpoints (No Authentication Required)
                .requestMatchers("/api/hms/user/**").permitAll()

                // Role-based Protected Endpoints

                // Property Owner Endpoints
                .requestMatchers("/api/hms/user/property-owner/signup").hasRole("PROPERTY_OWNER")
                .requestMatchers("/api/hms/property/addProperty").hasRole("PROPERTY_OWNER")
                .requestMatchers("/api/hms/property/updateProperty/**").hasRole("PROPERTY_OWNER")
                .requestMatchers("/api/hms/property/deleteProperty/**").hasRole("PROPERTY_OWNER")
                .requestMatchers("/api/hms/property/addPropertyPhotos").hasRole("PROPERTY_OWNER")

                // Admin Endpoints
                .requestMatchers("/api/hms/user/blog/signup").hasRole("ADMIN")
                .requestMatchers("/api/hms/property/updateProperty/**").hasAnyRole("PROPERTY_OWNER", "ADMIN")
                .requestMatchers("/api/hms/property/deleteProperty/**").hasAnyRole("PROPERTY_OWNER", "ADMIN")
                .requestMatchers("/api/hms/country/**").hasRole("ADMIN")
                .requestMatchers("/api/hms/city/**").hasRole("ADMIN")
                .requestMatchers("/api/hms/areas/**").hasRole("ADMIN")

                // User Endpoints
                .requestMatchers("/api/hms/reviews/addReviews").hasRole("USER")
                .requestMatchers("/api/hms/reviews/deleteReviews/**").hasRole("USER")
                .requestMatchers("/api/hms/reviews/updateReviews/**").hasRole("USER")
                .requestMatchers("/api/hms/hotel/bookHotel").hasRole("USER")
                .requestMatchers("/api/hms/hotel/searchHotels").hasRole("USER")

                // Customer Executive Endpoints
                .requestMatchers("/api/hms/reviews/viewReviews").hasRole("CUSTOMER_EXECUTIVE")

                // Default: Any other requests must be authenticated
                .anyRequest().authenticated();

        return http.build();
    }
}
