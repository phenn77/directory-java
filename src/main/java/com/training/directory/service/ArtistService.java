package com.training.directory.service;

import com.training.directory.dao.request.AddArtistRequest;
import com.training.directory.dao.response.ResponseBody;

public interface ArtistService {

    ResponseBody add(AddArtistRequest request);

    ResponseBody get(String id);
}
