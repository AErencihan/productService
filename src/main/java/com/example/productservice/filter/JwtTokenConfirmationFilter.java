package com.example.productservice.filter;

import com.example.productservice.exception.GlobalException;


import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Component
public class JwtTokenConfirmationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;


    public JwtTokenConfirmationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Origin,Content-Type, Accept, x-device-user-agent, Content-Type");

        //String authHeader = request.getHeader("Authorization");
        String authHeader = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlcm5uIiwiaXNzIjoiZXJuIiwiZXhwIjoxNzA4MjA3MTA4fQ.w6nxDLIn_Vqqdso1LOgv_w1CMiHCjrouyjv98-CSDcQ";

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw GlobalException.builder()
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .message("not authorize")
                    .build();
        }


        String token = authHeader.substring(7);

        try {
            jwtUtil.validateToken(token);
            Claims claims = jwtUtil.getClaims(token);
            request.setAttribute("jti", String.valueOf(claims.get("jti")));
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw GlobalException.builder()
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .message(e.getMessage())
                    .build();
        }
    }
}



















