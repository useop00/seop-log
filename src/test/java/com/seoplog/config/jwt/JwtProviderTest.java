package com.seoplog.config.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    void generateAccessToken() throws Exception {
        //given
        String username = "seop";
        String token = jwtProvider.generateAccessToken(username);

        //when & then
        assertThat(token).isNotNull();
        System.out.println("Access Token: " + token);
    }

    @Test
    void generateRefreshToken() throws Exception {
        //given
        String username = "seop";
        String token = jwtProvider.generateRefreshToken(username);

        //when & then
        assertThat(token).isNotNull();
        System.out.println("Refresh Token: " + token);
    }
}