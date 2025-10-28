package com.Yua.FastDelivery.Delivery_App.Jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey secret= Keys.hmacShaKeyFor("thisIsMySecretKeyPleaseDoNotSeeItItsForSecurityPurpose".getBytes(StandardCharsets.UTF_8));
    private long expiretime=24*60*60*1000;

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiretime))
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsernameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public Boolean isTokenValid(String token){
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
