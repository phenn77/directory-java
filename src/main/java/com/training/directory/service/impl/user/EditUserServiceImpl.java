package com.training.directory.service.impl.user;

import com.training.directory.annotation.Auditable;
import com.training.directory.constant.ModelType;
import com.training.directory.constant.Status;
import com.training.directory.dao.ApiContext;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.exception.BusinessException;
import com.training.directory.repository.ImageRepository;
import com.training.directory.repository.UserRepository;
import com.training.directory.service.impl.image.UploadServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class EditUserServiceImpl {

    private final ApiContext apiContext;
    private final ImageRepository imageRepository;
    private final UploadServiceImpl uploadService;
    private final UserRepository userRepository;

    @Auditable
    @Transactional
    public ResponseBody process(MultipartFile image) {
        var user = apiContext.getUser();

        if (Objects.nonNull(image)) {
            var currentImage = imageRepository.findById(user.getImage())
                    .orElseThrow(() -> {
                        log.error("Edit user error. Image not found. User ID: {}", user.getId());
                        return new BusinessException("Image not found.");
                    });

//            if (StringUtils.isBlank(currentImage)) {
//                    log.error("Edit user error. Image not found. User ID: {}", user.getId());
//                    throw new BusinessException("Image not found.");
//            }

            imageRepository.deleteById(currentImage.getId());
            uploadService.process(ModelType.USER, user.getId(), image);
        }

        return new ResponseBody(Status.SUCCESS, "Edit user success.", null);
    }
}
