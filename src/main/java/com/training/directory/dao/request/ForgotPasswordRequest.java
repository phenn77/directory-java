package com.training.directory.dao.request;

import java.util.Objects;

public record ForgotPasswordRequest(String username, String password) {
    public ForgotPasswordRequest {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
    }
}
