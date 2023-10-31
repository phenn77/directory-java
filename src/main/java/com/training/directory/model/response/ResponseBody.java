package com.training.directory.model.response;

import com.training.directory.constant.Status;

public record ResponseBody(Status status, Object data) {
}
