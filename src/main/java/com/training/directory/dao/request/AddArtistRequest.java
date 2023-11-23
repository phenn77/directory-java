package com.training.directory.dao.request;

import com.training.directory.dao.SocialMedia;

import java.util.List;
import java.util.Objects;

public record AddArtistRequest(
        String name,
        String origin,
        List<SocialMedia> socialMedia) {

    public AddArtistRequest {
        Objects.requireNonNull(name);
    }
}
