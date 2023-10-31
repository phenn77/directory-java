package com.training.directory.model.request;

import com.training.directory.model.response.LoginResponse;

import java.util.Objects;

public record LoginRequest(String username, String password) {

    public LoginRequest {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
    }
}
