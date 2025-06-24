package com.daol.logbuffer.image.domain;

import com.daol.logbuffer._common.exception.UnauthorizedException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Image {

    @EmbeddedId
    private ImageId id;

    @Column(name = "file_name")
    private String fileName;

    @Embedded
    private UploaderId uploaderId;

    @Column(name = "Created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    protected Image(UploaderId uploaderId) {
        this.uploaderId = uploaderId;
    }

    public void verifyUploader(UploaderId uploaderId) {
        if (!uploaderId.equals(this.uploaderId)) {
            throw new UnauthorizedException("해당 이미지를 삭제할 권한이 없습니다.");
        }
    }

    public void changeFileName(String fileName) {
        this.fileName = fileName;
    }
}