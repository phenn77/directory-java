package com.training.directory.dao.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.training.directory.constant.Status;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ResponseBody(Status status, String message, Object data) {

    public ResponseBody {
        Objects.requireNonNull(status);
        Objects.requireNonNull(data);
    }
}
