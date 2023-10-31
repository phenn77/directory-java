package com.training.directory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "social_media")
public class SocialMedia extends BaseEntity {

    private String type;

    private String url;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
}
