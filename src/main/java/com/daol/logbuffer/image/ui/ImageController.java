package com.daol.logbuffer.image.ui;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer._common.argresolver.Auth;
import com.daol.logbuffer.image.application.ImageResponse;
import com.daol.logbuffer.image.application.ImageServiceFactory;
import com.daol.logbuffer.image.common.ImageType;
import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.member.auth.AuthMember;
import com.daol.logbuffer.member.common.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageServiceFactory imageServiceFactory;

    @GetMapping(value = "/{type}/{fileName}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(
        @PathVariable String fileName,
        @PathVariable ImageType type
    ) {
        return ApiResponse.ok(imageServiceFactory.get(type).readImage(fileName));
    }

    @PostMapping("/{type}")
    public ResponseEntity<ImageResponse> uploadImage(
        @RequestParam MultipartFile image,
        @PathVariable ImageType type,
        @Auth(Grade.NORMAL) AuthMember member
    ) {
        return ApiResponse.ok(imageServiceFactory.get(type).createImage(new UploaderId(member.memberId()), image));
    }
}