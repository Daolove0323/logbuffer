package com.daol.logbuffer.image.application;

import com.daol.logbuffer.image.domain.ImageId;
import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.image.ui.ImageType;

public interface ImageService {

    ImageType getImageType();

    ImageId createImage(UploaderId uploaderId);

    void deleteImage(ImageId imageId, UploaderId uploaderId);
}