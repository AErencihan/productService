package com.example.productservice.service;

import com.example.productservice.dto.CartEvent;
import com.example.productservice.dto.UserDto;
import com.example.productservice.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CartService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaSerializeService kafkaSerializeService;
    private final RestTemplate restTemplate;


    public void addProductToCart(CartEvent cartEvent, String userName) {

        UserDto response = restTemplate.getForObject("http://localhost:8083/api/user/get/" + userName, UserDto.class);
        if (response == null) {
            throw GlobalException.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("UserId not found")
                    .build();
        }
        kafkaTemplate.send("topicProduct", kafkaSerializeService.serializedData(CartEvent.builder()
                .productId(cartEvent.getProductId())
                .userId(cartEvent.getUserId())
                .quantity(cartEvent.getQuantity())
                .build()));


    }
}
