package com.seoplog.service.user;

import com.seoplog.crypto.PasswordEncoder;
import com.seoplog.domain.user.User;
import com.seoplog.domain.user.request.Login;
import com.seoplog.domain.user.request.Signup;
import com.seoplog.domain.user.response.UserResponse;
import com.seoplog.exception.ExistsAccountException;
import com.seoplog.exception.InvalidSigningInformation;
import com.seoplog.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        Signup signup = Signup.builder()
                .account("wss3325")
                .name("seop")
                .password("1234")
                .build();

        //when
        userService.signup(signup);
        User user = userRepository.findAll().iterator().next();

        //then
        assertThat(userRepository.count()).isEqualTo(1);
        assertThat(user.getAccount()).isEqualTo("wss3325");
        assertThat(user.getName()).isEqualTo("seop");
        assertThat(user.getPassword()).isNotEqualTo("1234");
        assertThat(user.getPassword()).isNotNull();

    }

    @DisplayName("회원가입시 중복된 아이디는 예외처리한다.")
    @Test
    void existsAccount() throws Exception {
        //given
        User user = User.builder()
                .account("wss3325")
                .name("seop")
                .password("1234")
                .build();
        userRepository.save(user);

        Signup signup = Signup.builder()
                .account("wss3325")
                .name("seop")
                .password("1234")
                .build();

        //expected
        assertThatThrownBy(() -> userService.signup(signup))
                .isInstanceOf(ExistsAccountException.class);
    }

    @DisplayName("비밀번호를 암호화 해서 로그인한다.")
    @Test
    void signInUser() throws Exception {
        //given
        PasswordEncoder encoder = new PasswordEncoder();
        String encryptedPassword = encoder.encrpyt("1234");

        User user = User.builder()
                .account("wss3325")
                .name("seop")
                .password(encryptedPassword)
                .build();
        userRepository.save(user);

        Login login = Login.builder()
                .account(user.getAccount())
                .password("1234")
                .build();

        //when
        UserResponse userId = userService.signIn(login);

        //then
        assertThat(userId).isNotNull();
    }

    @DisplayName("비밀번호가 틀리면 로그인되지 않는다.")
    @Test
    void notEqualsPassword() throws Exception {
        //given
        Signup signup = Signup.builder()
                .account("wss3325")
                .name("seop")
                .password("1234")
                .build();
        userService.signup(signup);

        Login login = Login.builder()
                .account(signup.getAccount())
                .password("3454")
                .build();

        // expected
        assertThatThrownBy(() -> userService.signIn(login))
                .isInstanceOf(InvalidSigningInformation.class);

    }
}