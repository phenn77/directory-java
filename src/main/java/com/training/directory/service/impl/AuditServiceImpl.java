package com.training.directory.service.impl;

import com.training.directory.constant.ModelType;
import com.training.directory.constant.Status;
import com.training.directory.dao.ApiContext;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.model.Audit;
import com.training.directory.repository.AuditRepository;
import com.training.directory.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuditServiceImpl implements AuditService {

    private final ApiContext apiContext;
    private final AuditRepository auditRepository;

    @Override
    public void auditData(String user, HttpMethod method, ModelType model, String modelId, Object previousData, Object currentData) {
        var data = Audit.builder()
                .method(method.name())
                .model(model)
                .modelId(modelId)
                .previousData(String.valueOf(previousData))
                .currentData(String.valueOf(currentData))
                .build();

        if (StringUtils.isBlank(user)) {
            user = apiContext.getUserId();
        }

        if (StringUtils.equalsIgnoreCase(HttpMethod.POST.name(), method.name())) {
            data.setCreatedBy(user);
        } else {
            data.setUpdatedBy(user);
        }

        auditRepository.saveAndFlush(data);

        log.info("Successfully log data. Model Type: {}, ID: {}", model, modelId);
    }

    @Override
    public ResponseBody retrieveAuditData(ModelType model, String modelId) {
        var auditData = auditRepository.findByModelAndModelId(model, modelId);

        return new ResponseBody(Status.SUCCESS, StringUtils.EMPTY, auditData);
    }
}
