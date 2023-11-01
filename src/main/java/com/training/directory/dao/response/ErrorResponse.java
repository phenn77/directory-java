package com.training.directory.dao.response;

import com.training.directory.constant.Status;
import lombok.Data;

@Data
public class ErrorResponse {
    private Status status;
    private String message;

    public ErrorResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }
}