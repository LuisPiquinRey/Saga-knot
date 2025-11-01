package com.luispiquinrey.user.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;

@Service
public class S3CloudService {
    private final S3Template s3Template;

    @Value("${aws.bucket-name}")
    private String bucketName;
    
    public S3CloudService(S3Template s3Template) {
        this.s3Template = s3Template;
    }

    public String uploadFile(String key, MultipartFile file) throws IOException {
        S3Resource resource=s3Template.upload(bucketName, key, file.getInputStream());
        return resource.getURL().getPath();
    }

    public InputStream downloadFile(String key) {
        S3Resource resource = s3Template.download(bucketName, key);
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Error downloading file", e);
        }
    }

    public List<String> listFiles() {
        return s3Template.listObjects(bucketName, "")
                .stream()
                .map(s3Resource -> s3Resource.getFilename())
                .collect(Collectors.toList());
    }

    public String deleteFile(String key) {
        s3Template.deleteObject(bucketName, key);
        return "File deleted: " + key;
    }

    public boolean fileExists(String key) {
        return s3Template.objectExists(bucketName, key);
    }

    public String getFileUrl(String key) {
        return s3Template.createSignedGetURL(bucketName, key, 
                                            java.time.Duration.ofHours(1)).getPath();
    }
}

