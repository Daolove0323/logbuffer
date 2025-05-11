package com.daol.logbuffer.image.application;

import com.daol.logbuffer._common.util.FileUtil;
import com.daol.logbuffer.image.ui.ImageType;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageFileService {

    @Value("${image.storage-path}")
    private String storagePath;

    @Value("${image.post-directory}")
    private String postDirectory;

    @Value("${image.post-thumbnail-directory}")
    private String postThumbnailDirectory;

    @Value("${image.member-directory}")
    private String profileDirectory;

    public byte[] readImageFile(String fileName, ImageType type) {
        return FileUtil.readFile((Path.of(storagePath, getDirectory(type), fileName)));
    }

    public String uploadImageFile(MultipartFile image, ImageType type) {
        String fileName = FileUtil.generateRandomName() + "." + FileUtil.getExtension(image);
        FileUtil.writeFile(image, Path.of(storagePath, getDirectory(type), fileName));
        return fileName;
    }

    public void deleteImageFile(String fileName, ImageType type) {
        FileUtil.deleteFile(Path.of(storagePath, getDirectory(type), fileName));
    }

    public String getDirectory(ImageType type) {
        return switch (type) {
            case ImageType.POST -> postDirectory;
            case ImageType.POST_THUMBNAIL -> postThumbnailDirectory;
            case PROFILE -> profileDirectory;
        };
    }
}