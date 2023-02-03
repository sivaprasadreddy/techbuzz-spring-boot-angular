package com.sivalabs.techbuzz.posts.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import com.sivalabs.techbuzz.posts.domain.models.CategoryDTO;
import com.sivalabs.techbuzz.posts.domain.models.PostDTO;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostHandler;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostRequest;
import com.sivalabs.techbuzz.posts.usecases.getcategories.GetCategoriesHandler;
import com.sivalabs.techbuzz.security.SecurityService;
import com.sivalabs.techbuzz.users.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

class PostControllerTests extends AbstractIntegrationTest {
    @Autowired CreatePostHandler createPostHandler;
    @Autowired GetCategoriesHandler getCategoriesHandler;
    @Autowired SecurityService securityService;

    PostDTO post = null;

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldCreatePostSuccessfully() throws Exception {
        mockMvc.perform(
                        post("/api/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                    {
                                        "url": "https://sivalabs.in",
                                        "title": "SivaLabs",
                                        "content": "demo content",
                                        "categoryId": 1
                                    }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldFailToCreatePostIfDataIsInvalid() throws Exception {
        mockMvc.perform(
                        post("/api/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                    {
                                        "url": "",
                                        "title": "",
                                        "content": "",
                                        "categoryId": 1
                                    }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldDeletePost() throws Exception {
        CategoryDTO category = getCategoriesHandler.getCategory("java");
        User user = securityService.loginUser();
        CreatePostRequest request =
                new CreatePostRequest(
                        "title",
                        "https://sivalabs.in",
                        "test content",
                        category.id(),
                        user.getId());
        PostDTO post = createPostHandler.createPost(request);
        mockMvc.perform(delete("/api/posts/{id}", post.id())).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldUpdatePostSuccessfully() throws Exception {
        this.setUpPost();

        mockMvc.perform(
                        put("/api/posts/{id}", post.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                    {
                                        "url": "https://sivalabs.in",
                                        "title": "SivaLabs",
                                        "content": "demo content",
                                        "categoryId": 1
                                    }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldFailToUpdatePostIfDataIsInvalid() throws Exception {
        this.setUpPost();

        mockMvc.perform(
                        put("/api/posts/{id}", post.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                    {
                                        "url": "",
                                        "title": "",
                                        "content": "",
                                        "categoryId": 1
                                    }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldUpVotePost() throws Exception {
        this.setUpPost();

        User user = securityService.loginUser();
        mockMvc.perform(
                        post("/api/posts/{id}/votes", post.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                        {
                            "postId": "%d",
                            "userId": "%d",
                            "value": "1"
                        }
                        """
                                                .formatted(post.id(), user.getId())))
                .andExpect(status().isOk());
    }

    private void setUpPost() {
        CategoryDTO category = getCategoriesHandler.getCategory("java");
        User user = securityService.loginUser();
        CreatePostRequest request =
                new CreatePostRequest(
                        "title",
                        "https://sivalabs.in",
                        "test content",
                        category.id(),
                        user.getId());
        post = createPostHandler.createPost(request);
    }
}
