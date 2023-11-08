package com.training.directory.model;

import com.training.directory.constant.ModelType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Entity
@Table(name = "audit")
public class Audit extends BaseEntity {

    private String method;

    @Enumerated(EnumType.STRING)
    private ModelType model;

    private String modelId;

    private transient Object payload;

    private transient Object previousData;

    private transient Object currentData;

    private transient Object difference;
}
