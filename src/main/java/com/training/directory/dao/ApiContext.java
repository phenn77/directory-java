package com.training.directory.dao;

import com.training.directory.exception.BusinessException;
import com.training.directory.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ApiContext implements Serializable {

    private User user;

    public User getUser() {
        if (Objects.isNull(user)) {
            var auth = SecurityContextHolder.getContext().getAuthentication();

            if (StringUtils.equalsIgnoreCase("anonymousUser", String.valueOf(auth.getPrincipal()))) {
                throw new BusinessException(HttpStatus.UNAUTHORIZED, "Token not provided.");
            }

            user = (User) auth.getPrincipal();
        }

        return user;
    }

    public String getUserId() {
        return getUser().getId();
    }

    public String getUsername() {
        return getUser().getUsername();
    }
}
