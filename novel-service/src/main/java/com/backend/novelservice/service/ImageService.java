package com.backend.novelservice.service;

import com.backend.exception.AppException;
import com.backend.exception.ErrorCode;
import com.backend.novelservice.service.httpclient.ImgurClient;
import com.backend.novelservice.service.httpclient.ImgurResponse;
import com.backend.novelservice.entity.Image;
import com.backend.novelservice.repository.ImageRepository;
import com.backend.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {
    ImageRepository imageRepository;
    FileUtils fileUtils;
    ImgurClient imgurClient;

    @Value("${imgur.client-id}")
    @NonFinal
    protected String clientId;

    public Image getImage(String id) {
        return imageRepository.findById(id).orElseThrow(() -> {
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        });
    }

    public Image uploadImage(MultipartFile file) {
        fileUtils.validateFile(file);

        try {
            String authorization = "Client-ID " + clientId;
            ImgurResponse response = imgurClient.uploadImage(authorization, file);
            ImgurResponse.Data data = response.getData();

            Image image = new Image();
            image.setName(data.getId());
            image.setType(data.getType());
            image.setPath(data.getLink());
            image.setCreatedAt(Instant.now());
            return imageRepository.save(image);
        } catch (Exception e) {
            throw new RuntimeException("Upload image error", e);
        }
    }

    public void deleteImage(String id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> {
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        });

        imageRepository.delete(image);
    }
}