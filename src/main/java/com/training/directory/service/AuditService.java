package com.training.directory.service;

import com.training.directory.constant.ModelType;
import com.training.directory.dao.response.ResponseBody;
import org.springframework.http.HttpMethod;

public interface AuditService {

    void auditData(String user, HttpMethod method, ModelType model, String modelId, Object previousData, Object currentData);

    ResponseBody retrieveAuditData(ModelType model, String modelId);
}
