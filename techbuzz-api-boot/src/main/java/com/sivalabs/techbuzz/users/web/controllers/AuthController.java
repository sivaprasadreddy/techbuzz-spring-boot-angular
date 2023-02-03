package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.security.SecurityUser;
import com.sivalabs.techbuzz.security.TokenService;
import com.sivalabs.techbuzz.users.models.AuthenticationRequest;
import com.sivalabs.techbuzz.users.models.AuthenticationResponse;
import jakarta.validation.Valid;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest credentials) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(), credentials.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            String accessToken = tokenService.generateToken(authentication);
            // log.debug("Token granted: {}", accessToken);
            return ResponseEntity.ok(getAuthenticationResponse(securityUser, accessToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private AuthenticationResponse getAuthenticationResponse(SecurityUser user, String token) {
        return AuthenticationResponse.builder()
                .name(user.getUser().getName())
                .email(user.getUser().getEmail())
                .role(user.getUser().getRole().name())
                .accessToken(token)
                .expiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                .build();
    }
}
