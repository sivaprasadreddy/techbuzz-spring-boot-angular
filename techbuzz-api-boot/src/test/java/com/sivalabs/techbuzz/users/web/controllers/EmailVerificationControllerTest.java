package com.sivalabs.techbuzz.users.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import com.sivalabs.techbuzz.users.domain.UserDTO;
import com.sivalabs.techbuzz.users.usecases.registration.CreateUserHandler;
import com.sivalabs.techbuzz.users.usecases.registration.CreateUserRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EmailVerificationControllerTest extends AbstractIntegrationTest {

    @Autowired CreateUserHandler createUserHandler;

    @Test
    void shouldVerifyEmailSuccessfully() throws Exception {
        String email = RandomStringUtils.random(15, true, false) + "@gmail.com";
        CreateUserRequest request = new CreateUserRequest("name", email, "secret");
        UserDTO user = createUserHandler.createUser(request);
        mockMvc.perform(
                        get("/api/verify-email")
                                .param("email", email)
                                .param("token", user.getVerificationToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", CoreMatchers.equalTo(true)));
    }

    @Test
    void emailVerificationShouldFailWhenEmailAndTokenNotMatched() throws Exception {
        mockMvc.perform(
                        get("/api/verify-email")
                                .param("email", "dummy@mail.com")
                                .param("token", "secretToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", CoreMatchers.equalTo(false)));
        ;
    }
}
