package com.daol.logbuffer._common.util;

import com.daol.logbuffer._common.exception.FileIOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    public static byte[] readFile(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new FileIOException("파일을 읽을 수 없습니다");
        }
    }

    public static void writeFile(MultipartFile file, Path path) {
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new FileIOException("파일 저장 중 오류가 발생했습니다");
        }
    }

    public static void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new FileIOException("파일 삭제 중 오류가 발생했습니다");
        }
    }

    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static String getExtension(MultipartFile file) {
        try {
            return file.getContentType().split("/")[1];
        } catch (NullPointerException e) {
            throw new FileIOException("파일 확장자를 가져오는 중 오류가 발생했습니다");
        }
    }

    public static String generateRandomName() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
