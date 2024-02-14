package com.example.productservice.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;

}
