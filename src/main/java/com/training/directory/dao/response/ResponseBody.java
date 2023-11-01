package com.training.directory.dao.response;

import com.training.directory.constant.Status;

public record ResponseBody(Status status, Object data) {
}
