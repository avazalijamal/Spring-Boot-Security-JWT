package com.burakkutbay.springsecurityjwtexample.config;

import com.burakkutbay.springsecurityjwtexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Proqramın əsas konfiqurasiya sinfi.
 * Burada Spring Security üçün lazım olan Bean-lər yaradılır.
 */
@Configuration
@RequiredArgsConstructor // Final sahə userRepository üçün konstruktor yaradır
public class ApplicationConfig {

    private final UserRepository userRepository; // DB ilə istifadəçi məlumatlarına giriş üçün repo

    /**
     * UserDetailsService Bean-i: İstifadəçi adı ilə istifadəçi məlumatlarını yükləyir.
     * DB-də istifadəçi tapılmazsa, exception atır.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * AuthenticationProvider Bean-i:
     * DaoAuthenticationProvider istifadə edərək istifadəçi doğrulaması üçün provider təyin edilir.
     * UserDetailsService və PasswordEncoder set edilir.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // İstifadəçi məlumatlarını yükləyən servis təyin edilir
        authenticationProvider.setUserDetailsService(userDetailsService());
        // Şifrələrin necə yoxlanacağını göstərən encoder təyin edilir (BCrypt)
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * PasswordEncoder Bean-i:
     * Şifrəni şifrələmək və yoxlamaq üçün BCryptPasswordEncoder istifadə olunur.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager Bean-i:
     * Spring Security-nin AuthenticationManager obyektini konfiqurasiya obyektindən alırıq.
     * Bu, autentifikasiya əməliyyatlarını idarə edir.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
