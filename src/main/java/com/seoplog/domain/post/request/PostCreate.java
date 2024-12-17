package com.seoplog.domain.post.request;

import com.seoplog.domain.post.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostCreate {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 20, message = "제목은 20글자 이하로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    private PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
