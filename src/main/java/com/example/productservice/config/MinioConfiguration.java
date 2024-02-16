package com.example.productservice.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;




@Configuration
public class MinioConfiguration {


    @Value("${minio.access.name}")
    String accessKey;

    @Value("${minio.access.secret}")
    String accessSecret;

    @Value("${minio.url}")
    String minioUrl;


    @Bean
    public MinioClient generateMinioClient() {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(accessKey, accessSecret)
                    .build();
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket("test").build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket("test")
                        .build());
            }
            return minioClient;
        } catch (Exception e) {
            throw new RuntimeException("Minio client could not be created");
        }
    }

}
