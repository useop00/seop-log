package com.seoplog.domain.post.response;

import com.seoplog.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createDateTime;

    @Builder
    private PostResponse(Long id, String title, String content, LocalDateTime createDateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDateTime = createDateTime;
    }

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createDateTime(post.getCreatedDateTime())
                .build();
    }
}
