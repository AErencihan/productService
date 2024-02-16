package com.example.productservice.service;

import com.example.productservice.exception.GlobalException;
import com.example.productservice.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class KafkaService {

    ObjectMapper productMapper = new ObjectMapper();


    public String serializedData(Product product){
        String productConvertString = "";

        try {
            productConvertString = productMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            throw GlobalException.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("")
                    .build();
        }
        return productConvertString;
    }

}
