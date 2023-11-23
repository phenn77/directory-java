package com.training.directory.service.impl.artist;

import com.training.directory.constant.Status;
import com.training.directory.dao.ApiContext;
import com.training.directory.dao.request.AddArtistRequest;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.model.Artist;
import com.training.directory.model.SocialMedia;
import com.training.directory.repository.ArtistRepository;
import com.training.directory.repository.SocialMediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AddArtistServiceImpl {

    private final ApiContext apiContext;
    private final ArtistRepository artistRepository;
    private final SocialMediaRepository socialMediaRepository;

    @Transactional
    public ResponseBody process(AddArtistRequest request) {
        var user = apiContext.getUserId();

        var artist = Artist.builder()
                .name(request.name())
                .origin(request.origin())
                .build();
        artist.setCreatedBy(user);

        var data = artistRepository.saveAndFlush(artist);

        if (!request.socialMedia().isEmpty()) {
            var socialMedia = request.socialMedia()
                    .stream()
                    .map(sm -> {
                        var socMedia = SocialMedia.builder()
                                .type(sm.type().name())
                                .url(sm.address())
                                .artist(data)
                                .build();
                        socMedia.setCreatedBy(user);

                        return socMedia;
                    })
                    .toList();

            socialMediaRepository.saveAllAndFlush(socialMedia);

            data.setSocialMedia(socialMedia);
        }

        return new ResponseBody(Status.SUCCESS, StringUtils.EMPTY, data);
    }
}
