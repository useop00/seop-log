package com.seoplog.service;

import com.seoplog.domain.post.Post;
import com.seoplog.domain.user.User;
import com.seoplog.exception.PostNotFound;
import com.seoplog.exception.UserNotFound;
import com.seoplog.repository.post.PostRepository;
import com.seoplog.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityFinder {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post getPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);
    }

    public User getLoginUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFound::new);
    }
}
