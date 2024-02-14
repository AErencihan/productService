package com.example.productservice.service;


import com.example.productservice.dto.ProductDto;
import com.example.productservice.exception.GlobalException;
import com.example.productservice.model.Image;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ImageService imageService;


    private final ProductRepository productRepository;


    public ProductService(ImageService imageService, ProductRepository productRepository) {
        this.imageService = imageService;
        this.productRepository = productRepository;
    }


//    public ProductDto createProduct(Product product){
//         Product saveproduct = productRepository.save(product);
//            return ProductDto.builder()
//                    .productName(saveproduct.getProductName())
//                    .createTime(saveproduct.getCreateTime())
//                    .status(saveproduct.getStatus())
//                    .build();
//
//    }


    public ProductDto createProduct(Product product, MultipartFile file) {

        String imageUrl = imageService.uploadFile(file.getOriginalFilename(), file);
        Image image = Image.builder()
                .url(imageUrl)
                .build();

        if (product.getImages() == null) {
            product.setImages(List.of(image));
        } else {
            product.getImages().add(image);
        }

        Product saveproduct = productRepository.save(product);
        return ProductDto.builder()
                .productName(saveproduct.getProductName())
                .createTime(saveproduct.getCreateTime())
                .status(saveproduct.getStatus())
                .build();

    }


    public ProductDto updateProduct(Product product, MultipartFile file) {
        Optional<Product> optionalProduct = productRepository.findByUserName(product.getProductName());
        if (optionalProduct.isEmpty()) {
            throw GlobalException.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("No product found")
                    .build();
        }
        Product existingProduct = optionalProduct.get();

        if (file != null) {
            String imageUrl = imageService.uploadFile(file.getOriginalFilename(), file);
            Image newImage = Image.builder().url(imageUrl).build();

            if (existingProduct.getImages() == null || existingProduct.getImages().isEmpty()) {
                existingProduct.setImages(List.of(newImage));
            } else {
                existingProduct.getImages().get(0).setUrl(imageUrl);
            }

        }

        existingProduct.setProductName(product.getProductName());
        existingProduct.setStatus(product.getStatus());
        Product saveProduct = productRepository.save(existingProduct);

        return ProductDto.builder()
                .productName(saveProduct.getProductName())
                .status(saveProduct.getStatus())
                .build();

    }

    public void deleteProduct(Long id) {
        boolean exists = productRepository.existsById(id);
        if (!exists) {
            throw GlobalException.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("No product found")
                    .build();
        }
        productRepository.deleteById(id);
    }



}


