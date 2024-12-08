package com.backend.service;
import com.backend.entity.Image;
import com.backend.exception.AppException;
import com.backend.exception.ErrorCode;
import com.backend.repository.ImageRepository;
import com.backend.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {
    private final ImageRepository imageRepository;
    private final FileUtils fileUtils;

    public List<Image> getAllImage() {
        return imageRepository.findByOrderByCreatedAtDesc();
    }

    public Image getImage(String id) {
        return imageRepository.findById(id).orElseThrow(() -> {
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        });
    }

    public Image uploadImage(MultipartFile file) {
        fileUtils.validateFile(file);

        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Image image = new Image(fileName, file.getContentType(), file.getBytes());
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