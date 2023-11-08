package com.training.directory.service.impl;

import com.training.directory.constant.ModelType;
import com.training.directory.constant.Status;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.exception.BusinessException;
import com.training.directory.repository.ImageRepository;
import com.training.directory.service.ImageService;
import com.training.directory.service.impl.image.UploadServiceImpl;
import com.training.directory.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Log4j2
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final UploadServiceImpl uploadService;

    @Override
    public ResponseBody upload(ModelType model, String modelId, MultipartFile file) {
        return uploadService.process(model, modelId, file);
    }

    @Override
    public ResponseBody info(String id) {
        var image = imageRepository.findById(id)
                .orElseThrow(() -> {
                   log.error("Image not found. ID: {}", id);

                   return new BusinessException("Image not found.");
                });

        return new ResponseBody(Status.SUCCESS, StringUtils.EMPTY, image);
    }

    @Override
    public ResponseEntity<byte[]> retrieveImage(String id) {
        var image = imageRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Image not found. ID: {}", id);

                    return new BusinessException("Image not found.");
                });

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(image.getType()))
                .body(ImageUtil.decompressImage(image.getImageData()));
    }
}
