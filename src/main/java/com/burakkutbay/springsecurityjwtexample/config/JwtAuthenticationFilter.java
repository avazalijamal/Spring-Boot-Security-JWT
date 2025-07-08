package com.burakkutbay.springsecurityjwtexample.config;

import com.burakkutbay.springsecurityjwtexample.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Hər HTTP sorğusundan əvvəl işləyən JWT yoxlama filtiri.
 * JWT tokenini oxuyur, doğrulayır və istifadəçi autentifikasiyasını SecurityContext-ə əlavə edir.
 */
@Component
@RequiredArgsConstructor // Final sahələr üçün konstruktor avtomatik yaranır
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService; // JWT token ilə işləyən servis
    private final UserDetailsService userDetailsService; // İstifadəçi məlumatlarını yükləyən servis

    /**
     * HTTP sorğusunu filtrdən keçirir.
     * Əgər Authorization başlığında "Bearer " ilə başlayan JWT varsa, onu yoxlayır və autentifikasiya yaradır.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Authorization başlığını oxuyuruq
        final String header = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Əgər başlıq mövcud deyilsə və ya "Bearer " ilə başlamırsa, filtr növbəti mərhələyə keçsin
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " prefiksini çıxararaq tokeni alırıq
        jwt = header.substring(7);

        // Token içindən istifadəçi adını çıxarırıq
        username = jwtService.findUsername(jwt);

        // İstifadəçi adı mövcuddursa və SecurityContext-də hələ autentifikasiya yoxdursa
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // İstifadəçi məlumatlarını DB-dən yükləyirik
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Tokenin etibarlılığını və istifadəçi məlumatı ilə uyğunluğunu yoxlayırıq
            if (jwtService.tokenControl(jwt, userDetails)) {
                // İstifadəçi üçün autentifikasiya tokeni yaradırıq
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Sorğu haqqında əlavə məlumatları tokenə əlavə edirik
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext-ə autentifikasiya məlumatını əlavə edirik
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Filtri növbəti mərhələyə ötürürük
        filterChain.doFilter(request, response);
    }
}
