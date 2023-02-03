package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.TechBuzzException;
import com.sivalabs.techbuzz.users.usecases.verifyemail.EmailVerificationHandler;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationController {
    private final EmailVerificationHandler emailVerificationHandler;

    @GetMapping("/api/verify-email")
    public Map<String, Object> verifyEmail(
            @RequestParam("email") String email, @RequestParam("token") String token) {
        try {
            emailVerificationHandler.verify(email, token);
            return Map.of("success", true);
        } catch (TechBuzzException e) {
            return Map.of("success", false);
        }
    }
}
