package com.daol.logbuffer.image.application;

import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer._common.exception.InvalidRequestException;
import com.daol.logbuffer.image.common.ImageType;
import com.daol.logbuffer.image.domain.ImageRepository;
import com.daol.logbuffer.image.domain.ProfileImage;
import com.daol.logbuffer.image.domain.ProfileImageRepository;
import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.member.domain.MemberId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileImageService extends ImageService<ProfileImage> {

    private final ProfileImageRepository imageRepository;

    public ProfileImageService(FileStorage fileStorage, ImagePathService imagePathService, ProfileImageRepository imageRepository) {
        super(fileStorage, imagePathService);
        this.imageRepository = imageRepository;
    }

    @Override
    @Transactional
    public void setImageReference(String fileName, Object id) {
        if (!(id instanceof MemberId memberId)) {
            throw new InvalidRequestException("ID는 MemberId 타입이어야 합니다.");
        }
        ProfileImage image = imageRepository.findByFileName(fileName)
            .orElseThrow(() -> new EntityNotFoundException("파일명에 해당하는 이미지를 찾을 수 없습니다."));
        image.setMemberReference(memberId);
        imageRepository.save(image);
    }

    @Override
    ImageType getImageType() {
        return ImageType.PROFILE;
    }

    @Override
    ImageRepository<ProfileImage> getRepository() {
        return imageRepository;
    }

    @Override
    ProfileImage createImageEntity(UploaderId uploaderId) {
        return ProfileImage.create(uploaderId);
    }
}