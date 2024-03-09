package com.example.productservice.service;


import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String defaultBucketName;

    @Value("${minio.default.folder}")
    private String defaultBaseFolder;

    @Value("${minio.url}")
    String minioUrl;


    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public String uploadFile(String name, MultipartFile multipartFile){

        try {
            PutObjectArgs putObjectArgs =  PutObjectArgs.builder()
                    .contentType(multipartFile.getContentType())
                    .bucket(defaultBucketName)
                    .stream(multipartFile.getInputStream(),multipartFile.getSize(), -1 )
                    .object( multipartFile.getName().length()+ "/" + multipartFile.getOriginalFilename())
                    .build();
            minioClient.putObject(putObjectArgs);
            String fileUrl = minioUrl + defaultBucketName + "/" + multipartFile.getName().length() + "/" + multipartFile.getOriginalFilename();
            return fileUrl;
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
    }



     
}
