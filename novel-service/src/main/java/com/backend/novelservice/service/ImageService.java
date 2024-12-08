package com.backend.novelservice.service;
import com.backend.exception.AppException;
import com.backend.exception.ErrorCode;
import com.backend.novelservice.entity.Image;
import com.backend.novelservice.repository.ImageRepository;
import com.backend.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {
    private final ImageRepository imageRepository;
    private final FileUtils fileUtils;


    public Image getImage(String id) {
        return imageRepository.findById(id).orElseThrow(() -> {
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        });
    }

    public Image uploadImage(String novelId, MultipartFile file) {
        fileUtils.validateFile(file);

        try {
            String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            Image image = new Image();
            image.setName(fileName);
            image.setType(file.getContentType());
            image.setData(file.getBytes());
            image.setCreatedAt(Instant.now());
            return imageRepository.save(image);
        } catch (Exception e) {
            throw new RuntimeException("Upload image error");
        }
    }

    public void deleteImage(String id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> {
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        });

        imageRepository.delete(image);
    }
}