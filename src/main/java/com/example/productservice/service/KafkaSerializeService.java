package com.example.productservice.service;

import com.example.productservice.exception.GlobalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class KafkaSerializeService {

    ObjectMapper productMapper = new ObjectMapper();


    public <T> String serializedData(T t){
        String productConvertString = "";

        try {
            productConvertString = productMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw GlobalException.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(e.getMessage())
                    .build();
        }
        return productConvertString;
    }

}
