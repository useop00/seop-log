package com.seoplog.controller.user;

import com.seoplog.controller.ApiResponse;
import com.seoplog.domain.user.request.Login;
import com.seoplog.domain.user.request.Signup;
import com.seoplog.domain.user.response.LoginResponse;
import com.seoplog.domain.user.response.UserResponse;
import com.seoplog.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserResponse> signup(@Valid @RequestBody Signup request) {
        return ApiResponse.ok(userService.signup(request));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody Login login) {
        return ApiResponse.ok(userService.login(login));
    }
}
