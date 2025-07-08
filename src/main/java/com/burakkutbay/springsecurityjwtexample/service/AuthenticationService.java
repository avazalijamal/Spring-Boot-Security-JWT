package com.burakkutbay.springsecurityjwtexample.service;

import com.burakkutbay.springsecurityjwtexample.dto.UserDto;
import com.burakkutbay.springsecurityjwtexample.dto.UserRequest;
import com.burakkutbay.springsecurityjwtexample.dto.UserResponse;
import com.burakkutbay.springsecurityjwtexample.entity.User;
import com.burakkutbay.springsecurityjwtexample.enums.Role;
import com.burakkutbay.springsecurityjwtexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthenticationService sinfi istifadəçi qeydiyyatı (register) və daxilolma (login) əməliyyatlarını icra edir.
 */
@Service
@RequiredArgsConstructor // final field-lər üçün avtomatik constructor yaradır (Lombok)
public class AuthenticationService {

    private final UserRepository userRepository; // İstifadəçi məlumatlarını DB-də saxlamaq üçün repository

    private final JwtService jwtService; // Token yaratmaq və yoxlamaq üçün service

    private final AuthenticationManager authenticationManager; // Spring Security ilə istifadəçi autentifikasiyası

    private final PasswordEncoder passwordEncoder; // Şifrəni şifrələmək üçün encoder (məsələn BCrypt)

    /**
     * Yeni istifadəçi qeydiyyatı üçün metod.
     * İstifadəçini DB-ə əlavə edir və JWT token qaytarır.
     *
     * @param userDto Qeydiyyat zamanı gələn məlumatlar
     * @return JWT tokeni ehtiva edən cavab obyekti
     */
    public UserResponse save(UserDto userDto) {
        // Yeni istifadəçi obyekti qurulur
        User user = User.builder()
                .username(userDto.getUsername()) // istifadəçi adı
                .password(passwordEncoder.encode(userDto.getPassword())) // şifrə şifrələnərək yazılır
                .nameSurname(userDto.getNameSurname()) // tam ad
                .role(Role.USER) // default rol USER verilir
                .build();

        // İstifadəçi verilənlər bazasına əlavə olunur
        userRepository.save(user);

        // İstifadəçi üçün JWT token yaradılır
        var token = jwtService.generateToken(user);

        // Tokeni istifadəçiyə göndərmək üçün response obyekt yaradılır
        return UserResponse.builder().token(token).build();
    }

    /**
     * İstifadəçi daxilolma (login) üçün metod.
     * Əgər username/password düzgün olarsa, JWT token qaytarılır.
     *
     * @param userRequest Login sorğusu (username və password)
     * @return JWT tokeni ehtiva edən cavab obyekti
     */
    public UserResponse auth(UserRequest userRequest) {
        // İstifadəçi adı və şifrə ilə autentifikasiya yoxlanılır
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getUsername(),
                        userRequest.getPassword()
                )
        );

        // İstifadəçi verilənlər bazasından tapılır
        User user = userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(); // Tapılmazsa exception atılır

        // Token yaradılır
        String token = jwtService.generateToken(user);

        // Token istifadəçiyə response olaraq verilir
        return UserResponse.builder().token(token).build();
    }
}
