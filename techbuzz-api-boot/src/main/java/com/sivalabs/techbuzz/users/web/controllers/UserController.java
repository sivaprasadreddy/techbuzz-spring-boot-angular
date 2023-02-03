package com.sivalabs.techbuzz.users.web.controllers;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.common.exceptions.ResourceAlreadyExistsException;
import com.sivalabs.techbuzz.notifications.EmailService;
import com.sivalabs.techbuzz.users.domain.UserDTO;
import com.sivalabs.techbuzz.users.usecases.registration.CreateUserHandler;
import com.sivalabs.techbuzz.users.usecases.registration.CreateUserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final CreateUserHandler createUserHandler;
    private final EmailService emailService;
    private final ApplicationProperties properties;

    @PostMapping("/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(
            @Valid @RequestBody CreateUserRequest createUserRequest) {
        try {
            UserDTO userDTO = createUserHandler.createUser(createUserRequest);
            this.sendVerificationEmail(userDTO);
        } catch (ResourceAlreadyExistsException e) {
            log.error("Failed to create error", e);
            throw e;
        }
    }

    private void sendVerificationEmail(UserDTO userDTO) {
        String baseUrl = properties.domain();
        String params =
                "email="
                        + encode(userDTO.getEmail(), UTF_8)
                        + "&token="
                        + encode(userDTO.getVerificationToken(), UTF_8);
        String verificationUrl = baseUrl + "/verify-email?" + params;
        String to = userDTO.getEmail();
        String subject = "TechBuzz - Email verification";
        Map<String, Object> paramsMap =
                Map.of("", userDTO.getName(), "verificationUrl", verificationUrl);
        emailService.sendEmail("email/verifyEmail", paramsMap, to, subject);
    }
}
