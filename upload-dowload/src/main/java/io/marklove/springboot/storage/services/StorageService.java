package io.marklove.springboot.storage.services;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String uploadFile(MultipartFile file, String prefixPath);
    byte[] downloadFile(String file);
    boolean deleteFile(String fileName);
}
