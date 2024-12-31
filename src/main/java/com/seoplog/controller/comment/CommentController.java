package com.seoplog.controller.comment;

import com.seoplog.controller.ApiResponse;
import com.seoplog.domain.comment.request.CommentCreate;
import com.seoplog.domain.comment.response.CommentResponse;
import com.seoplog.service.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse<CommentResponse> writeComment(@Valid @RequestBody CommentCreate request, Principal pri) {
        return ApiResponse.ok(commentService.write(request, pri.getName()));
    }

    @GetMapping("/post/{postId}")
    public ApiResponse<List<CommentResponse>> getAllComments(@PathVariable Long postId) {
        return ApiResponse.ok(commentService.findAllComment(postId));
    }

    @PatchMapping("/{commentId}")
    public ApiResponse<Void> updateComment(@PathVariable Long commentId, @RequestBody String content) {
        commentService.updateComment(commentId, content);
        return ApiResponse.ok();
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.ok();
    }
}
