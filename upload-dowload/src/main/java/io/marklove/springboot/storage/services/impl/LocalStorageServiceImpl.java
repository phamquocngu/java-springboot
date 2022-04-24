package io.marklove.springboot.storage.services.impl;

import io.marklove.springboot.storage.services.StorageService;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
//@Profile("dev")
public class LocalStorageServiceImpl implements StorageService {
    @Value("${spring.application.name}")
    private String APP_NAME;
    @Value("${storage.dir}")
    private String DIR;
    private static final String SLASH = "/";
    private static final String UNDERSCORE = "_";

    @PostConstruct
    void init() {
        if (DIR == null || DIR == "") {
            DIR = SystemUtils.JAVA_IO_TMPDIR;
        }
    }

    @Override
    public String uploadFile(MultipartFile file, String prefixPath) {
        final String fileName = prefixPath + System.currentTimeMillis() + UNDERSCORE +
                file.getOriginalFilename();

        final String absolutePath = DIR + SLASH + APP_NAME + fileName;

        try {
            File f = new File(absolutePath);
            f.getParentFile().mkdirs();
            f.createNewFile();
            var os = new FileOutputStream(f);

            os.write(file.getBytes());
            os.close();
        } catch (IOException ex) {

        }
        return fileName;
    }

    @Override
    public byte[] downloadFile(String file) {
        return new byte[0];
    }

    @Override
    public boolean deleteFile(String fileName) {
        return false;
    }
}
