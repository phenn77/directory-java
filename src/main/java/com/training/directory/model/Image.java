package com.training.directory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "image")
public class Image extends BaseEntity {

    @NotNull
    @Lob
    @JsonIgnore
    private byte[] imageData;

    @NotNull
    private String filename;

    @NotNull
    private String type;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "single_id")
    private Single single;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
