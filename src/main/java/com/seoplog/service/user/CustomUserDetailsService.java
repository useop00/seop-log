package com.seoplog.service.user;

import com.seoplog.service.EntityFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EntityFinder entityFinder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return (UserDetails) entityFinder.getLoginUser(username);
    }
}
