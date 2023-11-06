package com.training.directory.dao.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.directory.constant.Status;

import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ResponseBody(Status status, String message, Object data) {

    public ResponseBody {
        Objects.requireNonNull(status);

        if (Objects.nonNull(data)) {
            data = new ObjectMapper().convertValue(data, Map.class);
        }
    }
}
