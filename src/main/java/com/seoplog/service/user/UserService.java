package com.seoplog.service.user;

import com.seoplog.crypto.PasswordEncoder;
import com.seoplog.domain.user.User;
import com.seoplog.domain.user.request.Login;
import com.seoplog.domain.user.request.Signup;
import com.seoplog.domain.user.response.UserResponse;
import com.seoplog.exception.ExistsAccountException;
import com.seoplog.exception.InvalidSigningInformation;
import com.seoplog.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserResponse signIn(Login login) {
        User user = userRepository.findByAccount(login.getAccount())
                .orElseThrow(InvalidSigningInformation::new);

        PasswordEncoder encoder = new PasswordEncoder();
        boolean matches = encoder.matches(login.getPassword(), user.getPassword());

        if (!matches) {
            throw new InvalidSigningInformation();
        }

        return UserResponse.of(user);
    }

    public UserResponse signup(Signup signup) {
        existsByAccount(signup);

        PasswordEncoder encoder = new PasswordEncoder();
        String encodedPassword = encoder.encrpyt(signup.getPassword());

        User user = signup.toEntity(encodedPassword);

        return UserResponse.of(userRepository.save(user));
    }

    private void existsByAccount(Signup signup) {
        if (userRepository.existsByAccount(signup.getAccount())) {
            throw new ExistsAccountException();
        }
    }
}
