package com.training.directory.service.impl.image;

import com.training.directory.constant.ModelType;
import com.training.directory.constant.Status;
import com.training.directory.dao.ApiContext;
import com.training.directory.dao.response.ResponseBody;
import com.training.directory.exception.BusinessException;
import com.training.directory.model.*;
import com.training.directory.repository.*;
import com.training.directory.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class UploadServiceImpl {

    private final ApiContext apiContext;
    private final ImageRepository imageRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final MemberRepository memberRepository;
    private final SingleRepository singleRepository;
    private final UserRepository userRepository;

    public ResponseBody process(ModelType model, String id, MultipartFile file) {
        try {
            var image = Image.builder()
                    .filename(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes()))
                    .build();
            image.setCreatedBy(apiContext.getUserId());

            retrieveModelData(model, id, image);

            image = imageRepository.saveAndFlush(image);

            return new ResponseBody(
                    Status.SUCCESS,
                    "Image upload successful.",
                    Map.of("imageId", image.getId())
            );
        } catch (IOException ex) {
            log.error("Image upload failed. Error: {}", (Object) ExceptionUtils.getRootCauseStackTrace(ex));

            throw new BusinessException("Image upload failed.");
        }
    }

    private void retrieveModelData(ModelType model, String id, Image image) {
        switch (model) {
            case ALBUM -> {
                var albumData = albumRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error("Album data not found for image upload.");

                            return new BusinessException("Album Data invalid.");
                        });

                image.setAlbum(albumData);
            }
            case ARTIST -> {
                var artistData = artistRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error("Artist data not found for image upload.");

                            return new BusinessException("Artist Data invalid.");
                        });

                image.setArtist(artistData);
            }
            case MEMBER -> {
                var memberData = memberRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error("Member data not found for image upload.");

                            return new BusinessException("Member Data invalid.");
                        });

                image.setMember(memberData);
            }
            case SINGLE -> {
                var singleData = singleRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error("Single data not found for image upload.");

                            return new BusinessException("Single Data invalid.");
                        });

                image.setSingle(singleData);
            }
            case USER -> {
                var userData = userRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error("User data not found for image upload.");

                            return new BusinessException("User Data invalid.");
                        });

                image.setUser(userData);
            }
            default -> throw new BusinessException("Model not found.");
        }
    }
}
