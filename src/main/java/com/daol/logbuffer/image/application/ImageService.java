package com.daol.logbuffer.image.application;

import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer.image.common.ImageType;
import com.daol.logbuffer.image.domain.Image;
import com.daol.logbuffer.image.domain.ImageRepository;
import com.daol.logbuffer.image.domain.UploaderId;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public abstract class ImageService<T extends Image> {

    protected final FileStorage fileStorage;
    private final ImagePathService imagePathService;

    abstract ImageType getImageType();

    abstract ImageRepository<T> getRepository();

    abstract T createImageEntity(UploaderId uploaderId);

    public byte[] readImage(String fileName) {
        return fileStorage.readFile(imagePathService.getImagePath(getImageType()), fileName);
    }

    @Transactional
    public ImageResponse createImage(UploaderId uploaderId, MultipartFile file) {
        T image = getRepository().save(createImageEntity(uploaderId));
        String fileName = fileStorage.writeFile(file, imagePathService.getImagePath(getImageType()));
        image.changeFileName(fileName);
        return new ImageResponse(imagePathService.getImageUrl(getImageType(), fileName));
    }

    @Transactional
    public void deleteImage(String fileName, UploaderId uploaderId) {
        T image = getRepository().findByFileName(fileName)
            .orElseThrow(() -> new EntityNotFoundException("파일명에 해당하는 이미지를 찾을 수 없습니다."));
        image.verifyUploader(uploaderId);
        getRepository().delete(image);
        fileStorage.deleteImageFile(imagePathService.getImagePath(getImageType()), fileName);
    }
}