package com.seoplog.domain.user.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seoplog.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {

    private final String account;
    private final String name;
    private final String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime createDateTime;

    @Builder
    private UserResponse(String account, String name, String password, LocalDateTime createDateTime) {
        this.account = account;
        this.name = name;
        this.password = password;
        this.createDateTime = createDateTime;
    }

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .account(user.getAccount())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }
}
