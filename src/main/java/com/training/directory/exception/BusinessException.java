package com.training.directory.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BusinessException extends RuntimeException {

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(HttpStatus httpStatus, String message) {
        this(message);

        this.status = httpStatus;
    }
}
