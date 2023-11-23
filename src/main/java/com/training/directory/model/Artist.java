package com.training.directory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Data
@Entity
@Table(name = "artist")
public class Artist  extends BaseEntity {

    @NotNull
    private String name;

    @Column(length = 4)
    private String activeYear;

    private String origin;

    private String status;

    @OneToMany(mappedBy = "artist")
    private List<SocialMedia> socialMedia;

    @OneToMany(mappedBy = "artist")
    private List<Member> members;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums;

    @OneToMany(mappedBy = "artist")
    private List<Single> singles;

    private Long rate;

    @OneToMany(mappedBy = "artist")
    private List<Image> images;
}
