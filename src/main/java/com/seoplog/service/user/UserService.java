package com.seoplog.service.user;

import com.seoplog.config.jwt.JwtProvider;
import com.seoplog.domain.user.User;
import com.seoplog.domain.user.request.Login;
import com.seoplog.domain.user.request.Signup;
import com.seoplog.domain.user.response.LoginResponse;
import com.seoplog.domain.user.response.UserResponse;
import com.seoplog.exception.ExistsUsernameException;
import com.seoplog.repository.user.UserRepository;
import com.seoplog.service.EntityFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EntityFinder entityFinder;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public UserResponse signup(Signup signup) {
        existsByUsername(signup);

        String encodedPassword = passwordEncoder.encode(signup.getPassword());
        User user = signup.toEntity(encodedPassword);

        return UserResponse.of(userRepository.save(user));
    }

    public LoginResponse login(Login login) {
        User user = entityFinder.getLoginUser(login.getUsername());

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.generateAccessToken(user.getUsername());
        String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());

        saveRefreshTokenInRedis(user.getUsername(), refreshToken);

        return new LoginResponse(accessToken, refreshToken);
    }

    private void saveRefreshTokenInRedis(String username, String refreshToken) {
        redisTemplate.opsForValue().set(
                username,
                refreshToken,
                JwtProvider.REFRESH_TOKEN_EXPIRATION,
                TimeUnit.MILLISECONDS
        );
    }

    private void existsByUsername(Signup signup) {
        if (userRepository.existsByUsername(signup.getUsername())) {
            throw new ExistsUsernameException();
        }
    }
}
