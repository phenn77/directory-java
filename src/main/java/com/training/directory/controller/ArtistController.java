package com.training.directory.controller;

import com.training.directory.dao.request.AddArtistRequest;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping("/admin/add")
    public ResponseBody add(@RequestBody AddArtistRequest request) {
        return artistService.add(request);
    }
}
