package com.training.directory.middleware;

import com.training.directory.exception.BusinessException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@RequiredArgsConstructor
public class AuthorizationValidator {

    @Value("${token.secret-key}")
    private String jwtSecretKey;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CredentialManager credentialManager;

    @Before("@annotation(com.training.directory.annotation.Authorized)")
    public void checkToken() {
        var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        var token = request.getHeader("token");

        if (StringUtils.isBlank(token)) {
            logger.error("Token not provided. Header: {}", request.getHeader("token"));

            throw new BusinessException(HttpStatus.UNAUTHORIZED, "Token not provided.");
        }

        var claims = credentialManager.extractAllClaims(token);

        if (Objects.nonNull(claims)) {
            var tokenSecretKey = claims.get("secretKey", String.class);

            if (!StringUtils.equalsIgnoreCase(jwtSecretKey, tokenSecretKey)) {
                logger.error("Invalid token. Token: {}", token);

                throw new BusinessException(HttpStatus.UNAUTHORIZED, "Token invalid.");
            }
        }
    }
}
