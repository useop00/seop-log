package com.seoplog.service.user;

import com.seoplog.domain.user.User;
import com.seoplog.domain.user.request.Login;
import com.seoplog.domain.user.request.Signup;
import com.seoplog.domain.user.response.LoginResponse;
import com.seoplog.exception.ExistsUsernameException;
import com.seoplog.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원가입 성공")
    @Test
    void signupUser() throws Exception {
        //given
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        Signup signup = Signup.builder()
                .username("wss3325")
                .name("seop")
                .password("1234")
                .build();

        //when
        userService.signup(signup);
        User user = userRepository.findAll().iterator().next();

        //then
        assertThat(userRepository.count()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("wss3325");
        assertThat(user.getName()).isEqualTo("seop");
        assertThat(encode.matches("1234", user.getPassword())).isTrue();
    }

    @DisplayName("회원가입시 중복된 아이디는 예외처리한다.")
    @Test
    void existsUsername() throws Exception {
        //given
        User user = User.builder()
                .username("wss3325")
                .name("seop")
                .password("1234")
                .build();
        userRepository.save(user);

        Signup signup = Signup.builder()
                .username("wss3325")
                .name("seop")
                .password("1234")
                .build();

        //expected
        assertThatThrownBy(() -> userService.signup(signup))
                .isInstanceOf(ExistsUsernameException.class);
    }

    @DisplayName("로그인 성공")
    @Test
    void loginSuccess() throws Exception {
        //given
        Signup signup = Signup.builder()
                .username("wss3325")
                .name("seop")
                .password("1234")
                .build();
        userService.signup(signup);

        Login login = Login.builder()
                .username("wss3325")
                .password("1234")
                .build();

        //when
        LoginResponse response = userService.login(login);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isNotEmpty();
        assertThat(response.getRefreshToken()).isNotEmpty();
    }

    @DisplayName("비밀번호가 일치하지 않으면 로그인이 실패한다.")
    @Test
    void loginFail() throws Exception {
        //given
        Signup signup = Signup.builder()
                .username("wss3325")
                .name("seop")
                .password("1234")
                .build();
        userService.signup(signup);

        Login login = Login.builder()
                .username("wss3325")
                .password("333333")
                .build();

        //expected
        assertThatThrownBy(() -> userService.login(login))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}