package com.daol.logbuffer.image.application;

import com.daol.logbuffer.image.domain.Image;
import com.daol.logbuffer.image.domain.ImageId;
import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.image.ui.ImageType;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageFacadeService {

    private final ConcurrentHashMap<ImageType, ImageService<? extends ImageId>> imageServiceMap;
    private final ImageFileService imageFileService;

    @Value("${image.api-url}")
    private String imageApiUrl;

    public ImageFacadeService(
        ImageFileService imageFileService,
        List<ImageService<? extends ImageId>> imageServices
    ) {
        this.imageFileService = imageFileService;
        imageServiceMap = new ConcurrentHashMap<>(
            imageServices.stream()
                .collect(
                    Collectors.toConcurrentMap(ImageService::getImageType, Function.identity()))
        );
    }

    public byte[] readImage(String fileName, ImageType type) {
        return imageFileService.readImageFile(fileName, type);
    }

    @Transactional
    public String uploadAndSaveImage(MultipartFile image, ImageType type, UploaderId uploaderId) {
        String fileName = imageFileService.uploadImageFile(image, type);
        getImageService(type).createImage(uploaderId);
        return imageApiUrl + "/" + imageFileService.getDirectory(type) + "/" + fileName;
    }

    private ImageService<? extends Image> getImageService(ImageType imageType) {
        return imageServiceMap.get(imageType);
    }

    @Transactional(readOnly = true)
    public <T extends Image> String getImageUrl(T image, ImageType type) {
        return imageApiUrl + "/" + imageFileService.getDirectory(type) + "/" + image.getFileName();
    }
}