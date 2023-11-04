package com.training.directory.repository;

import com.training.directory.constant.ModelType;
import com.training.directory.model.Audit;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, String> {

    @Query("SELECT * FROM audit WHERE model = :model AND modelId = :modelId ORDER BY CREATED_AT DESC")
    List<Audit> findByModelAndModelId(ModelType model, String modelId);
}
