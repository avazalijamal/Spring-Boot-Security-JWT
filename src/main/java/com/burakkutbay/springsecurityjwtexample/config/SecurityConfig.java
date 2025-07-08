package com.burakkutbay.springsecurityjwtexample.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security konfiqurasiya sinfi.
 * Burada təhlükəsizlik parametrləri təyin olunur.
 */
@Configuration // Bu sinif konfiqurasiya olaraq qeyd olunur (Bean-lər yaradır)
@EnableWebSecurity // Spring Security-ni aktivləşdirir
@RequiredArgsConstructor // Lombok ilə final sahələr üçün konstruktor yaradılır (dependency injection üçün)
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider; // İstifadəçi identifikasiyası və doğrulama üçün provayder
    private final JwtAuthenticationFilter jwtAuthenticationFilter; // JWT token yoxlama filtri

    /**
     * SecurityFilterChain Bean-i yaradır.
     * Burada HTTP təhlükəsizlik siyasəti konfiqurasiya olunur.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // CSRF qorunması deaktiv edilir (stateless API üçün uyğun)
                .csrf()
                .disable()

                // Authorization qaydaları başlanır
                .authorizeHttpRequests()
                // "/login/**" ilə başlayan endpointlər icazəsiz (permitAll) açıqdır
                .requestMatchers("/login/**")
                .permitAll()
                // Digər bütün sorğular autentifikasiya tələb edir
                .anyRequest()
                .authenticated()
                .and()

                // Sessiyaların idarəsi stateless olaraq təyin edilir
                // Yəni server sessiya saxlamır, hər sorğuda autentifikasiya aparılır (JWT üçün ideal)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // İstifadəçi autentifikasiyası üçün custom authenticationProvider təyin olunur
                .authenticationProvider(authenticationProvider)

                // JWT yoxlama filtiri UsernamePasswordAuthenticationFilter-dən əvvəl işə düşür
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Konfiqurasiya tamamlanır və SecurityFilterChain qaytarılır
        return httpSecurity.build();
    }
}
