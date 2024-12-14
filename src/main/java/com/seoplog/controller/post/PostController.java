package com.seoplog.controller.post;

import com.seoplog.controller.ApiResponse;
import com.seoplog.domain.post.request.PostCreateRequest;
import com.seoplog.domain.post.response.PostResponse;
import com.seoplog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ApiResponse<PostResponse> post(@RequestBody @Valid PostCreateRequest request) {

        return ApiResponse.ok(postService.write(request));
    }

    @GetMapping("/posts/{postId}")
    public ApiResponse<PostResponse> findPost(@PathVariable Long postId) {
        return ApiResponse.ok(postService.findById(postId));
    }
}
