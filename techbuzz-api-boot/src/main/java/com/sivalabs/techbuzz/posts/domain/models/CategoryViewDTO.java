package com.sivalabs.techbuzz.posts.domain.models;

import com.sivalabs.techbuzz.common.model.PagedResult;

public record CategoryViewDTO(
        Long id,
        String name,
        String slug,
        String description,
        String image,
        PagedResult<PostUserViewDTO> posts) {}
