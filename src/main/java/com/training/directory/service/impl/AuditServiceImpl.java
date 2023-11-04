package com.training.directory.service.impl;

import com.training.directory.constant.ModelType;
import com.training.directory.constant.Status;
import com.training.directory.dao.ApiContext;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.model.Audit;
import com.training.directory.repository.AuditRepository;
import com.training.directory.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final ApiContext apiContext;
    private final AuditRepository auditRepository;

    @Override
    public void auditData(HttpMethod method, ModelType model, String modelId, Object previousData, Object currentData) {
        var data = Audit.builder()
                .method(method.name())
                .model(model)
                .modelId(modelId)
                .previousData(String.valueOf(previousData))
                .currentData(String.valueOf(currentData))
                .build();

        var userId = apiContext.getUserId();

        if (StringUtils.equalsIgnoreCase(HttpMethod.POST.name(), method.name())) {
            data.setCreatedBy(userId);
        } else {
            data.setUpdatedBy(userId);
        }

        auditRepository.save(data);
    }

    @Override
    public ResponseBody retrieveAuditData(ModelType model, String modelId) {
        var auditData = auditRepository.findByModelAndModelId(model, modelId);

        return new ResponseBody(Status.SUCCESS, StringUtils.EMPTY, Map.of("data", auditData));
    }
}
