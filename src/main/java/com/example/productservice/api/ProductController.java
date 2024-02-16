package com.example.productservice.api;


import com.example.productservice.dto.FormWrapper;
import com.example.productservice.dto.ProductDto;
import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "", allowedHeaders = "")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDto> saveProduct(@ModelAttribute FormWrapper formWrapper) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(Product.builder()
                        .productName(formWrapper.getName())
                        .status(formWrapper.getStatus())
                        .build(), formWrapper.getFile()));
    }

    @PutMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ProductDto updateProduct(@ModelAttribute FormWrapper formWrapper) {
        return productService.updateProduct(Product.builder()
                .productName(formWrapper.getName())
                .status(formWrapper.getStatus())
                .build(), formWrapper.getFile());

    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
    

}
