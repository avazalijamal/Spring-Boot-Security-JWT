package com.burakkutbay.springsecurityjwtexample.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/**
 * JwtService sinfi JWT tokenlərinin yaradılması, yoxlanması və içindən məlumat çıxarılması üçün cavabdehdir.
 */
@Service
public class JwtService {

    // application.properties faylında təyin olunan gizli açar (secret key)
    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

    /**
     * Verilmiş JWT tokenindən istifadəçi adını çıxarır.
     *
     * @param token JWT token
     * @return İstifadəçi adı (subject)
     */
    public String findUsername(String token) {
        return exportToken(token, Claims::getSubject);
    }

    /**
     * Verilmiş tokeni parçalayıb ondan lazımi məlumatı çıxarır (subject, expiration və s.)
     *
     * @param token JWT token
     * @param claimsTFunction Claims obyektindən çıxarılacaq məlumatı müəyyən edən funksiya
     * @param <T> Qayıdacaq məlumatın tipi
     * @return Claims obyektindən çıxarılmış məlumat
     */
    private <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey()) // Tokeni imzalayan açarı təyin edir
                .build()
                .parseClaimsJws(token) // Tokeni parse edir və doğruluğunu yoxlayır
                .getBody(); // Tokenin içindəki əsas məlumat hissəsi (claims)

        return claimsTFunction.apply(claims); // Claims-dən istənilən məlumatı çıxarır
    }

    /**
     * Base64 ilə kodlanmış SECRET_KEY-i real açara çevirir.
     *
     * @return JWT tokenləri imzalamaq üçün istifadə olunan açar (HMAC SHA)
     */
    private Key getKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY); // Base64 kodunu deşifrə edir
        return Keys.hmacShaKeyFor(key); // HMAC SHA açarı yaradır
    }

    /**
     * Tokenin keçərli olub-olmadığını yoxlayır.
     * Yəni: Tokenin içindəki istifadəçi adı verilən UserDetails ilə eynidir və tokenin vaxtı bitməyib.
     *
     * @param jwt JWT token
     * @param userDetails Sistemə daxil olmuş istifadəçi detalları
     * @return Əgər token keçərlidirsə true, əks halda false
     */
    public boolean tokenControl(String jwt, UserDetails userDetails) {
        final String username = findUsername(jwt);
        return (username.equals(userDetails.getUsername()) // İstifadəçi adı eyni olmalıdır
                && !exportToken(jwt, Claims::getExpiration).before(new Date())); // Token vaxtı keçməməlidir
    }

    /**
     * Yeni JWT token yaradır.
     *
     * @param user Sistemdəki istifadəçi obyekti
     * @return İmzanmış JWT token
     */
    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .setClaims(new HashMap<>()) // Əlavə claims əlavə etmirik (boş map)
                .setSubject(user.getUsername()) // Tokenin istifadəçi adı (subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Token yaradılma vaxtı
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Token 24 dəqiqəlik keçərlidir
                .signWith(getKey(), SignatureAlgorithm.HS256) // Token HMAC SHA-256 ilə imzalanır
                .compact(); // Tokeni string şəklinə çevirir
    }
}
