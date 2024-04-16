package com.example.productservice.filter;

import com.example.productservice.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

@Component
@RequiredArgsConstructor
@Aspect
public class AuthAspect {

    @Around("@annotation(PreAuth)")
    public Object preAuth(ProceedingJoinPoint joinPoint)  {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        String token = request.getAttribute("roles").toString();
        if (token == null){
            throw new RuntimeException("Token is missing");
        }

        var methotSignature = (MethodSignature) joinPoint.getSignature();
        Annotation[] annotations = methotSignature.getMethod().getAnnotations();

        for (Annotation annotation : annotations){
            if (annotation instanceof PreAuth){
                PreAuth preAuth = (PreAuth) annotation;
                String roles = preAuth.roles();
                if (!token.contains(roles)){
                    throw GlobalException.builder()
                            .httpStatus(HttpStatus.UNAUTHORIZED)
                            .message("You are not authorized")
                            .build();
                }

            }
        }

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("Internal server error");
        }

    }




}


















