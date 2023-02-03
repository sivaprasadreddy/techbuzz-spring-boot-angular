package com.sivalabs.techbuzz.posts.usecases.getposts;

import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.common.model.PagedResult;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.models.CategoryDTO;
import com.sivalabs.techbuzz.posts.domain.models.CategoryViewDTO;
import com.sivalabs.techbuzz.posts.domain.models.PostDTO;
import com.sivalabs.techbuzz.posts.domain.models.PostUserViewDTO;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.posts.mappers.CategoryDTOMapper;
import com.sivalabs.techbuzz.posts.mappers.PostDTOMapper;
import com.sivalabs.techbuzz.security.SecurityService;
import com.sivalabs.techbuzz.users.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class GetPostsHandler {

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    private final PostDTOMapper postDtoMapper;

    private final CategoryDTOMapper categoryDTOMapper;

    private final SecurityService securityService;

    private final ApplicationProperties properties;

    public PostDTO getPost(Long postId) {
        return postDtoMapper.toDTO(
                postRepository
                        .findById(postId)
                        .orElseThrow(() -> new ResourceNotFoundException("Post not found")));
    }

    public CategoryViewDTO getPostsByCategorySlug(String category, Integer page) {
        log.debug("process=get_posts_by_category_slug, category={}, page={}", category, page);
        Pageable pageable = getPageable(page);
        CategoryDTO categoryDTO =
                categoryRepository.findBySlug(category).map(categoryDTOMapper::toDTO).orElseThrow();
        Page<Long> postIds = postRepository.findPostIdsByCategorySlug(category, pageable);
        List<Post> posts = postRepository.findPosts(postIds.getContent());
        Page<Post> postsPage = new PageImpl<>(posts, pageable, postIds.getTotalElements());
        PagedResult<PostUserViewDTO> postsData = convert(postsPage);
        return new CategoryViewDTO(
                categoryDTO.id(),
                categoryDTO.name(),
                categoryDTO.slug(),
                categoryDTO.description(),
                categoryDTO.image(),
                postsData);
    }

    private Pageable getPageable(Integer page) {
        int pageNo = page > 0 ? page - 1 : 0;
        return PageRequest.of(pageNo, properties.postsPerPage());
    }

    private PagedResult<PostUserViewDTO> convert(Page<Post> postsPage) {
        User loginUser = securityService.loginUser();
        Page<PostUserViewDTO> postDTOPage =
                postsPage.map(post -> postDtoMapper.toPostUserViewDTO(loginUser, post));
        return new PagedResult<>(postDTOPage);
    }
}
