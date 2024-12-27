package com.seoplog.domain.user.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {

    private String username;
    private String password;

    @Builder
    private Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
