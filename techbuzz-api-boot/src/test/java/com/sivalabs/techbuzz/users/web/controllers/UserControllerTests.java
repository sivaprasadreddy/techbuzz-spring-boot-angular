package com.sivalabs.techbuzz.users.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import com.sivalabs.techbuzz.notifications.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

class UserControllerTests extends AbstractIntegrationTest {

    @MockBean EmailService emailService;

    @Test
    void shouldRegisterSuccessfully() throws Exception {
        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                    {
                                        "name": "dummy",
                                        "email": "dummy@mail.com",
                                        "password": "admin1234"
                                    }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldFailRegistrationWhenEmailAlreadyExists() throws Exception {
        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                    {
                                        "name": "dummy",
                                        "email": "%s",
                                        "password": "admin1234"
                                    }
                                """
                                                .formatted(ADMIN_EMAIL)))
                .andExpect(status().isConflict());
    }
}
