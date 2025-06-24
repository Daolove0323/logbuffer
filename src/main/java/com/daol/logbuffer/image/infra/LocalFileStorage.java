package com.daol.logbuffer.image.infra;

import com.daol.logbuffer._common.exception.FileIOException;
import com.daol.logbuffer.image.application.FileStorage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class LocalFileStorage implements FileStorage {

    public byte[] readFile(String... paths) {
        try {
            return Files.readAllBytes(Path.of(String.join("/", paths)));
        } catch (IOException e) {
            throw new FileIOException("파일을 읽을 수 없습니다");
        }
    }

    public String writeFile(MultipartFile file, String... paths) {
        String fileName = generateFileName(getExtension(file));
        try {
            String filePath = String.join("/", paths);
            Files.createDirectories(Path.of(filePath));
            Files.write(Path.of(filePath, fileName), file.getBytes());
        } catch (IOException e) {
            throw new FileIOException("파일 저장 중 오류가 발생했습니다");
        }
        return fileName;
    }

    public void deleteImageFile(String... paths) {
        try {
            Files.deleteIfExists(Path.of(String.join("/", paths)));
        } catch (IOException e) {
            throw new FileIOException("파일 삭제 중 오류가 발생했습니다");
        }
    }

    private String getExtension(MultipartFile file) {
        try {
            return file.getContentType().split("/")[1];
        } catch (NullPointerException e) {
            throw new FileIOException("파일 확장자를 가져오는 중 오류가 발생했습니다");
        }
    }

    private String generateFileName(String extension) {
        return String.format("%s.%s", UUID.randomUUID().toString().replace("-", ""), extension);
    }
}