package com.training.directory.middleware;

import com.training.directory.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@RequiredArgsConstructor
@Log4j2
public class AuthorizationValidator {

    @Value("${token.secret-key}")
    private String jwtSecretKey;

    private final CredentialManager credentialManager;

    @Before("@annotation(com.training.directory.annotation.Authorized)")
    public void checkToken() {
        var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        var token = request.getHeader("token");

        if (StringUtils.isBlank(token)) {
            log.error("Token not provided. Header: {}", request.getHeader("token"));

            throw new BusinessException(HttpStatus.UNAUTHORIZED, "Token not provided.");
        }

        var claims = credentialManager.extractAllClaims(token);

        if (Objects.nonNull(claims)) {
            var tokenSecretKey = claims.get("secretKey", String.class);

            if (!StringUtils.equalsIgnoreCase(jwtSecretKey, tokenSecretKey)) {
                log.error("Invalid token. Token: {}", token);

                throw new BusinessException(HttpStatus.UNAUTHORIZED, "Token invalid.");
            }
        }
    }
}
