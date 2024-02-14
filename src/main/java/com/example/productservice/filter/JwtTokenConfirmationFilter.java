package com.example.productservice.filter;

import com.example.productservice.exception.GlobalException;


import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
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

//    @NotNull
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
//
//        ServerHttpRequest request = exchange.getRequest();
//
//        String authHeader = request.getHeaders().getFirst("Authorization");
//
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw GlobalException.builder()
//                    .httpStatus(HttpStatus.UNAUTHORIZED)
//                    .message("not authorize")
//                    .build();
//        }
//        String token = authHeader.substring(7);
//
//        try {
//            jwtUtil.validateToken(token);
//            Claims claims = jwtUtil.getClaims(token);
//
//            exchange.getRequest().mutate().header("jti", String.valueOf(claims.get("jti"))).build();
//
//
//         // -------------------
//            return chain.filter(exchange);
//
//        } catch (Exception e) {
//            GlobalException.builder()
//                    .httpStatus(HttpStatus.UNAUTHORIZED)
//                    .message(e.getMessage())
//                    .build();
//        }
//        return chain.filter(exchange);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Origin,Content-Type, Accept, x-device-user-agent, Content-Type");

        //String authHeader = request.getHeader("Authorization");
        String authHeader = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlcm5uIiwiaXNzIjoiZXJuIiwiZXhwIjoxNzA3NjY0Mzc3fQ.yqxn7Bhyn7_SToAUub4RTvXMTpzQxrl3dOKsArnzPMA";


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



















