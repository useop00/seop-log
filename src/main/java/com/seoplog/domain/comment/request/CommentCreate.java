package com.seoplog.domain.comment.request;

import com.seoplog.domain.comment.Comment;
import com.seoplog.domain.post.Post;
import com.seoplog.domain.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreate {

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

    private Long postId;

    @Builder
    private CommentCreate(String content, Long postId) {
        this.content = content;
        this.postId = postId;
    }

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .content(this.content)
                .post(post)
                .user(user)
                .build();
    }
}
