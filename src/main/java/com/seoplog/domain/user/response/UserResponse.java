package com.seoplog.domain.user.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seoplog.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {

    private final String username;
    private final String name;
    private final String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime createDateTime;

    @Builder
    private UserResponse(String username, String name, String password, LocalDateTime createDateTime) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.createDateTime = createDateTime;
    }

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .password(user.getPassword())
                .createDateTime(user.getCreatedDateTime())
                .build();
    }
}
