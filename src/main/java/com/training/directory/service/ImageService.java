package com.training.directory.service;

import com.training.directory.constant.ModelType;
import com.training.directory.dao.response.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ResponseBody upload(ModelType model, String modelId, MultipartFile file);

    ResponseBody info(String id);

    ResponseEntity<byte[]> retrieveImage(String id);
}
