package com.seoplog.config;

import com.seoplog.config.data.UserSession;
import com.seoplog.exception.Unauthorized;
import com.seoplog.repository.user.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.crypto.SecretKey;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String jws = webRequest.getHeader("Authorization");
        if (jws == null || jws.isEmpty()) {
            throw new Unauthorized();
        }

        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(appConfig.getJwtKey());

            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jws);
            String userId = claims.getPayload().getSubject();
            return new UserSession(Long.parseLong(userId));

        } catch (JwtException e) {
            throw new Unauthorized();
        }
    }
}
