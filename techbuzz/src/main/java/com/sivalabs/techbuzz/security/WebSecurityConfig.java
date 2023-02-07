package com.sivalabs.techbuzz.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.sivalabs.techbuzz.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] PUBLIC_RESOURCES = {
        "/webjars/**",
        "/resources/**",
        "/static/**",
        "/assets/**",
        "/favicon.ico",
        "/",
        "/error",
        "/403",
        "/404",
        "/actuator/health",
        "/actuator/loggers",
        "/actuator/info",
        "/actuator/metrics",
        "/api/categories",
        "/login",
        "/registration",
        "/registrationStatus",
        "/verifyEmail",
        "/c/**"
    };

    private final ApplicationProperties properties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf()
                .disable()
                // .cors()
                // .configurationSource(request -> new
                // CorsConfiguration().applyPermitDefaultValues())
                // .and()
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers(PUBLIC_RESOURCES)
                                        .permitAll()
                                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                                        .permitAll()
                                        // .requestMatchers(HttpMethod.POST,
                                        // "/api/posts").authenticated()
                                        .anyRequest()
                                        .permitAll())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(properties.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk =
                new RSAKey.Builder(properties.publicKey())
                        .privateKey(properties.privateKey())
                        .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
