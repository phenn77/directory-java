package com.training.directory.controller;

import com.training.directory.constant.ModelType;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
//    https://medium.com/shoutloudz/spring-boot-upload-and-download-images-using-jpa-b1c9ef174dc0

    private final ImageService imageService;

    @PostMapping("upload")
    public ResponseBody upload(
            @RequestParam("image") MultipartFile file,
            @RequestParam("modelType") ModelType modelType,
            @RequestParam("modelId") String modelId) {
        return imageService.upload(modelType, modelId, file);
    }

    @GetMapping("info/{imageId}")
    public ResponseBody getInfo(@PathVariable("imageId") String imageId) {
        return imageService.info(imageId);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageId") String imageId) {
        return imageService.retrieveImage(imageId);
    }
}
