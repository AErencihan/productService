package com.example.productservice.api;


import com.example.productservice.dto.CartEvent;
import com.example.productservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/cart")
public class CartController {


    private final CartService cartService;


    @PostMapping("/addProductToCart")
    public ResponseEntity<CartEvent> addProduct(@RequestBody CartEvent cartEvent, @RequestHeader("userName") String userName) {


        cartService.addProductToCart(cartEvent, userName);

        return ResponseEntity
                .status(HttpStatus.CREATED).build();
    }


}
