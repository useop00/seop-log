package com.seoplog.service;

import com.seoplog.domain.post.Post;
import com.seoplog.domain.post.request.PostCreateRequest;
import com.seoplog.domain.post.response.PostResponse;
import com.seoplog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostResponse write(PostCreateRequest request) {

        Post post = request.toEntity();
        Post savedPost = postRepository.save(post);

        return PostResponse.of(savedPost);
    }

    public PostResponse findById(Long id) {
        return PostResponse.of(postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다.")));
    }

    public List<PostResponse> findAll() {
        return postRepository.findAll()
                .stream()
                .map(PostResponse::of)
                .toList();
    }

    public void update(Long id, PostCreateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        post.update(request.getTitle(), request.getContent());
        postRepository.save(post);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}


