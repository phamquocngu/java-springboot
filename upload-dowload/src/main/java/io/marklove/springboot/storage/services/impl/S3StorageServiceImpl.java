package io.marklove.springboot.storage.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import io.marklove.springboot.storage.services.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author ngupq
 */
@Service
//@Profile("prod")
@Slf4j
public class S3StorageServiceImpl implements StorageService {
    @Value("${cloud.aws.bucket.name}")
    private String bucketName;
    private static final String SLASH = "/";

    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile file, String prefixPath) {
        File fileObj = convertMultiPartFileToFile(file);
        final String fileName = prefixPath + System.currentTimeMillis() +
                SLASH + file.getOriginalFilename();

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();

        return  fileName;
    }


    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            log.error("Download File error: " + e.getMessage());
        }
        return null;
    }


    public boolean deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return true;
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
