package com.example.demo.config.security;

import com.example.demo.auth.jwt.JwtConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtConfigurer jwtConfigurer;

    private final List<AuthenticationProvider> authenticationProviders;

    public WebSecurityConfig(JwtConfigurer jwtConfigurer, List<AuthenticationProvider> authenticationProviders) {
        this.jwtConfigurer = jwtConfigurer;
        this.authenticationProviders = authenticationProviders;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = http
                .cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        return httpSecurity.authorizeHttpRequests()
                .anyRequest()
                .permitAll()
                .and()
                .apply(jwtConfigurer)
                .and().build();
    }

    @Bean
    protected AuthenticationManager configure() {
        return new ProviderManager(authenticationProviders);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final var configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(false);
        configuration.setExposedHeaders(Collections.singletonList("fileName"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Origin", "origin", "accept"));

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
