package com.seoplog.domain.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String account;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    private Login(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
