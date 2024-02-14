package com.example.productservice.filter;


import com.example.productservice.exception.GlobalException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


@Component
@RequiredArgsConstructor
public class JwtUtil{

    private final SecretKey secretKey;

    public void extractClaims(String token){
        Jwts.parserBuilder().setSigningKey(secretKey).build().parse(token).getBody();
    }



    public void validateToken(final String token){
        try {
                extractClaims(token);
        }
        catch (SignatureException exception){
            throw GlobalException.builder()
                    .message("invalid JWT signature")
                    .build();
        }
        catch (MalformedJwtException e){
            throw GlobalException.builder()
                    .message("invalid JWT token")
                    .build();
        }
        catch (ExpiredJwtException e){
            throw GlobalException.builder()
                    .message("expired JWT token")
                    .build();
        }
        catch (UnsupportedJwtException e){
            throw GlobalException.builder()
                    .message("unsuppoerted JWT token")
                    .build();
        }
        catch (IllegalArgumentException e){
            throw GlobalException.builder()
                    .message("Jwt claims String is empty")
                    .build();
        }
    }

    public Claims getClaims(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

}

















