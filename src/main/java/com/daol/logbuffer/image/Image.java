package com.daol.logbuffer.image;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
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

    @EmbeddedId
    private ImageId id;

    @Column(name = "url")
    @NotNull
    private String url;

    private UploaderId uploaderId;

    @Column(name = "Created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    protected Image() {
        this.id = ImageId.generate();
    }

    protected Image(String url, UploaderId uploaderId) {
        this.id = ImageId.generate();
        this.url = url;
        this.uploaderId = uploaderId;
    }
}