package com.training.directory.middleware;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.training.directory.dao.ApiContext;
import com.training.directory.model.Audit;
import com.training.directory.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

@Aspect
@Component
@Log4j2
@RequiredArgsConstructor
public class AuditManager {
//    https://medium.com/trendyol-tech/audit-log-approach-with-spring-aop-and-fluentbit-1df53840a9eb

    private final AuditRepository auditRepository;
    private final ApiContext apiContext;

    private static final ThreadLocal<Audit> auditLogThreadLocal = new ThreadLocal<>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    @Pointcut("@annotation(com.training.directory.annotation.Auditable)")
    public void auditable() {
    }

    @Pointcut("execution(* org.springframework.data.repository.CrudRepository.findById(..))")
    public void findById() {
    }

    @AfterReturning(value = "auditable()", returning = "dto")
    public void loggableMethodCall(JoinPoint jp, Object dto) {
        try {
            var auditLog = auditLogThreadLocal.get();

            var previousData = String.valueOf(auditLog.getPreviousData());
            var currentData = String.valueOf(auditLog.getCurrentData());

            auditLog.setPayload(gson.toJson(jp.getArgs()));
            auditLog.setCurrentData(gson.toJson(dto));
            auditLog.setDifference(getDifference(previousData, currentData));
            auditLog.setUpdatedBy(apiContext.getUserId());

            auditRepository.saveAndFlush(auditLog);
        } catch (Exception exception) {
            log.warn("Error occurred while loggableMethodCall logging ! {}", exception.getMessage());
        }
    }

    @AfterReturning(value = "findById()", returning = "entity")
    public void findByIdCall(JoinPoint jp, Optional<?> entity) {
        try {
            var auditLog = Audit.builder().build();
            entity.ifPresent(o -> {
                auditLog.setPreviousData(gson.toJson(o));
            });

            auditLogThreadLocal.set(auditLog);
        } catch (Exception exception) {
            log.warn("Error occurred while findByIdCall logging ! {}", exception.getMessage());
        }
    }

    private String getDifference(String before, String after) {
        return StringUtils.hasText(before) ? extractDifferences(before, after) : null;
    }

    private String extractDifferences(String before, String after) {
        Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> firstMap = gson.fromJson(before, mapType);
        Map<String, Object> secondMap = gson.fromJson(after, mapType);

        if (firstMap.isEmpty() || secondMap.isEmpty()) {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }

        final MapDifference<String, Object> difference = Maps.difference(firstMap, secondMap);

        return gson.toJson(Map.of("diff", difference.entriesDiffering(), "added", difference.entriesOnlyOnRight()));
    }
}
