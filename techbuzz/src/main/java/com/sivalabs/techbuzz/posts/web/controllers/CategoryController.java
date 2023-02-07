package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.posts.domain.models.CategoryDTO;
import com.sivalabs.techbuzz.posts.domain.models.CategoryViewDTO;
import com.sivalabs.techbuzz.posts.usecases.getcategories.GetCategoriesHandler;
import com.sivalabs.techbuzz.posts.usecases.getposts.GetPostsHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final GetPostsHandler getPostsHandler;
    private final GetCategoriesHandler getCategoriesHandler;

    @GetMapping
    public List<CategoryDTO> allCategories() {
        return getCategoriesHandler.getAllCategories();
    }

    @GetMapping("/{slug}")
    public CategoryViewDTO viewCategory(
            @PathVariable(name = "slug") String categorySlug,
            @RequestParam(name = "page", defaultValue = "1") Integer page) {
        log.info("Fetching posts for category {} with page: {}", categorySlug, page);
        return getPostsHandler.getPostsByCategorySlug(categorySlug, page);
    }
}
