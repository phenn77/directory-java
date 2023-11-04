package com.training.directory.service;

import com.training.directory.constant.ModelType;
import com.training.directory.dao.response.ResponseBody;
import org.springframework.http.HttpMethod;

public interface AuditService {

    void auditData(HttpMethod method, ModelType model, String modelId, Object previousData, Object currentData);

    ResponseBody retrieveAuditData(String model, String modelId);
}
