package com.seoplog.domain.post.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdate {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 20, message = "제목은 20글자 이하로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    private PostUpdate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
