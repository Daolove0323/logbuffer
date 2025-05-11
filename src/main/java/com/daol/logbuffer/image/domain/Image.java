package com.daol.logbuffer.image.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class Image {

    @Embedded
    private UploaderId uploaderId;

    @Column(name = "Created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    protected Image() {
    }

    protected Image(UploaderId uploaderId) {
        this.uploaderId = uploaderId;
    }

    public void verifyUploader(UploaderId uploaderId) {
        if (!uploaderId.equals(this.uploaderId)) {
            throw new RuntimeException();
        }
    }
}