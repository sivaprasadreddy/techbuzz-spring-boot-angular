package com.sivalabs.techbuzz.posts.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CategoryControllerTests extends AbstractIntegrationTest {

    @Test
    void shouldGetCategoriesList() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").isNotEmpty());
    }

    @ParameterizedTest
    @CsvSource({"java", "go"})
    void shouldFetchPostsByCategory(String slug) throws Exception {
        mockMvc.perform(get("/api/categories/{slug}", slug)).andExpect(status().isOk());
    }
}
