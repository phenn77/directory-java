package com.training.directory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "tracklist")
public class Tracklist extends BaseEntity {

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
}
