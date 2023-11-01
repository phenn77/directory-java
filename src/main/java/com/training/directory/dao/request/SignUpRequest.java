package com.training.directory.dao.request;

import java.sql.Date;
import java.util.Objects;

public record SignUpRequest(
        String firstName,
        String lastName,
        Date birthday,
        String username,
        String email,
        String password) {

    public SignUpRequest {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(birthday);
        Objects.requireNonNull(username);
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
    }
}
