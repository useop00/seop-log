package com.seoplog.service;

import com.seoplog.domain.post.Post;
import com.seoplog.domain.post.PostEditor;
import com.seoplog.domain.post.request.PostCreate;
import com.seoplog.domain.post.request.PostSearch;
import com.seoplog.domain.post.request.PostUpdate;
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
    private final EntityFinder entityFinder;

    public PostResponse write(PostCreate request) {

        Post post = request.toEntity();
        Post savedPost = postRepository.save(post);

        return PostResponse.of(savedPost);
    }

    @Transactional(readOnly = true)
    public PostResponse findPost(Long id) {
        return PostResponse.of(entityFinder.getPostId(id));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findAll(PostSearch postSearch) {
        return postRepository.getList(postSearch)
                .stream()
                .map(PostResponse::of)
                .toList();
    }

    public PostResponse update(Long id, PostUpdate request) {
        Post post = entityFinder.getPostId(id);
        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        post.update(postEditor);

        return PostResponse.of(post);
    }

    public void deletePost(Long id) {
        Post post = entityFinder.getPostId(id);

        postRepository.delete(post);
    }
}


