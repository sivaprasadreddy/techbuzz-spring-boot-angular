package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.UnauthorisedAccessException;
import com.sivalabs.techbuzz.config.annotations.AnyAuthenticatedUser;
import com.sivalabs.techbuzz.config.annotations.CurrentUser;
import com.sivalabs.techbuzz.posts.domain.models.PostDTO;
import com.sivalabs.techbuzz.posts.domain.models.VoteDTO;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostHandler;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostRequest;
import com.sivalabs.techbuzz.posts.usecases.createvote.CreateVoteRequest;
import com.sivalabs.techbuzz.posts.usecases.createvote.VoteHandler;
import com.sivalabs.techbuzz.posts.usecases.deletepost.DeletePostHandler;
import com.sivalabs.techbuzz.posts.usecases.getposts.GetPostsHandler;
import com.sivalabs.techbuzz.posts.usecases.updatepost.UpdatePostHandler;
import com.sivalabs.techbuzz.posts.usecases.updatepost.UpdatePostRequest;
import com.sivalabs.techbuzz.users.domain.User;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final CreatePostHandler createPostHandler;
    private final GetPostsHandler getPostsHandler;
    private final DeletePostHandler deletePostHandler;
    private final UpdatePostHandler updatePostHandler;

    @GetMapping("/posts/{id}")
    public PostDTO getPost(@PathVariable Long id) {
        return getPostsHandler.getPost(id);
    }

    @PutMapping("/posts/{id}")
    @AnyAuthenticatedUser
    public PostDTO updateBookmark(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request,
            @CurrentUser User loginUser) {
        PostDTO post = getPostsHandler.getPost(id);
        var updatePostRequest =
                new UpdatePostRequest(
                        id,
                        request.title(),
                        request.url(),
                        request.content(),
                        request.categoryId());
        this.checkPrivilege(post, loginUser);
        PostDTO updatedPost = updatePostHandler.updatePost(updatePostRequest);
        log.info("Post with id: {} updated successfully", updatedPost.id());
        return updatedPost;
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    @AnyAuthenticatedUser
    public PostDTO createPost(
            @Valid @RequestBody CreatePostRequest request, @CurrentUser User loginUser) {

        var createPostRequest =
                new CreatePostRequest(
                        request.title(),
                        request.url(),
                        request.content(),
                        request.categoryId(),
                        loginUser.getId());
        PostDTO post = createPostHandler.createPost(createPostRequest);
        log.info("Post saved successfully with id: {}", post.id());
        return post;
    }

    @DeleteMapping("/posts/{id}")
    @AnyAuthenticatedUser
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @CurrentUser User loginUser) {
        PostDTO post = getPostsHandler.getPost(id);
        this.checkPrivilege(post, loginUser);
        deletePostHandler.deletePost(id);
        return ResponseEntity.ok().build();
    }

    private final VoteHandler voteHandler;

    @PostMapping("/posts/{id}/votes")
    @AnyAuthenticatedUser
    public VoteDTO createVote(
            @PathVariable Long id,
            @Valid @RequestBody CreateVoteRequest request,
            @CurrentUser User loginUser) {
        var createVoteRequest = new CreateVoteRequest(id, loginUser.getId(), request.value());
        return voteHandler.addVote(createVoteRequest);
    }

    private void checkPrivilege(PostDTO post, User loginUser) {
        if (!(Objects.equals(post.createdBy().getId(), loginUser.getId())
                || loginUser.isAdminOrModerator())) {
            throw new UnauthorisedAccessException("Permission Denied");
        }
    }
}
