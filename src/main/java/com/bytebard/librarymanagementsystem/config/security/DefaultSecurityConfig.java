package com.bytebard.librarymanagementsystem.config.security;

import com.bytebard.librarymanagementsystem.filters.JwtTokenFilter;
import com.bytebard.librarymanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity(debug = true)
public class DefaultSecurityConfig {

    @Value("${spring.jwt.secret.key}")
    public String secretKey;

    @Bean
    public JwtTokenFilter jwtTokenFilter(
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver,
            JwtUtil jwtUtil
    ) {
        return new JwtTokenFilter(jwtUtil, handlerExceptionResolver, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtTokenFilter jwtTokenFilter,
            DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint
    ) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/auth/login", "/api/auth/signup")
                        .permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(e -> e.authenticationEntryPoint(delegatedAuthenticationEntryPoint));

        return http.build();
    }

    @Bean
    public SecretKey secretKey() {
        return new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
