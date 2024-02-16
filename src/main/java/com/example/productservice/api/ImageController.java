package com.example.productservice.api;


import com.example.productservice.service.ImageService;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping(path = "/buckets")
    public List<Bucket> bucketsList(){
        return imageService.getAllBuckets();
    }

    @PostMapping(path = "/upload",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Map<String, String> uploadFile(@RequestParam(name = "file") MultipartFile files) throws IOException {
        imageService.uploadFile(files.getOriginalFilename(), files);
        Map<String, String> result = new HashMap<>();

        System.out.println("askdfnvşkangşkljnasşkdjvnşakjsndbv");

        result.put("key", files.getOriginalFilename());
        return result;
    }





}
