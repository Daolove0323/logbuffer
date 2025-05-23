package com.daol.logbuffer.image.ui;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer.image.application.ImageFacadeService;
import java.util.UUID;
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

    private final ImageFacadeService facadePostImageService;

    @GetMapping(value = "/{image}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(
        @PathVariable String image,
        @RequestParam ImageType type
    ) {
        return ApiResponse.ok(facadePostImageService.readImage(image, type));
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(
        @RequestParam MultipartFile image,
        @RequestParam ImageType type,
        UUID memberId
    ) {
        return ApiResponse.ok(facadePostImageService.uploadAndSaveImage(image, type, UUID.randomUUID()));
    }
}