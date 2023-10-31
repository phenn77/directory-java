package com.training.directory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "album")
public class Album extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private Date releasedDate;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "album")
    private List<Tracklist> tracklist;

    private Long rate;

    @OneToMany(mappedBy = "album")
    private List<Image> images;
}
