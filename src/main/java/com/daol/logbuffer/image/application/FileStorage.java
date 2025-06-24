package com.daol.logbuffer.image.application;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

    byte[] readFile(String... paths);

    String writeFile(MultipartFile file, String... paths);

    void deleteImageFile(String... paths);
}