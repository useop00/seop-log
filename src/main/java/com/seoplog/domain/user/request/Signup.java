package com.seoplog.domain.user.request;

import com.seoplog.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Signup {

    @NotBlank(message = "아이디는 필수입니다.")
    private String account;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @Builder
    private Signup(String account, String name, String password) {
        this.account = account;
        this.name = name;
        this.password = password;
    }

    public User toEntity(String encodedPassword) {
        return User.builder()
                .account(account)
                .name(name)
                .password(encodedPassword)
                .build();
    }
}
