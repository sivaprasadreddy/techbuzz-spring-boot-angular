package com.sivalabs.techbuzz.posts.usecases.createvote;

import jakarta.validation.constraints.NotNull;

public record CreateVoteRequest(Long postId, Long userId, @NotNull Integer value) {}
