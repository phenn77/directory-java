package com.training.directory.service.impl;

import com.training.directory.constant.Status;
import com.training.directory.dao.request.AddArtistRequest;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.repository.ArtistRepository;
import com.training.directory.service.ArtistService;
import com.training.directory.service.impl.artist.AddArtistServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ArtistServiceImpl implements ArtistService {

    private final AddArtistServiceImpl addArtistService;
    private final ArtistRepository artistRepository;

    @Override
    public ResponseBody add(AddArtistRequest request) {
        return addArtistService.process(request);
    }

    @Override
    public ResponseBody get(String id) {
        var data = artistRepository.findById(id);

        return new ResponseBody(Status.SUCCESS, StringUtils.EMPTY, data);
    }
}
