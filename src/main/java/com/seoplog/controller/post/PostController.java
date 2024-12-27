package com.seoplog.controller.post;

import com.seoplog.controller.ApiResponse;
import com.seoplog.domain.post.request.PostCreate;
import com.seoplog.domain.post.request.PostSearch;
import com.seoplog.domain.post.request.PostUpdate;
import com.seoplog.domain.post.response.PostResponse;
import com.seoplog.service.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ApiResponse<PostResponse> post(@RequestBody @Valid PostCreate request) {
        return ApiResponse.ok(postService.write(request));
    }

    @GetMapping("/posts/{postId}")
    public ApiResponse<PostResponse> findPost(@PathVariable Long postId) {
        return ApiResponse.ok(postService.findPost(postId));
    }

    @GetMapping("/posts")
    public ApiResponse<List<PostResponse>> findAll(@ModelAttribute PostSearch postSearch){
        return ApiResponse.ok(postService.findAll(postSearch));
    }

    @PatchMapping("/posts/{postId}")
    public ApiResponse<PostResponse> updatePost(@PathVariable Long postId, @RequestBody @Valid PostUpdate request) {
        return ApiResponse.ok(postService.update(postId, request));
    }

    @DeleteMapping("/posts/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.ok();
    }
}
