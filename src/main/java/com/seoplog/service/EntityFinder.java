package com.seoplog.service;

import com.seoplog.domain.post.Post;
import com.seoplog.exception.PostNotFound;
import com.seoplog.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityFinder {

    private final PostRepository postRepository;

    public Post getPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);
    }
}
