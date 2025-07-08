package com.burakkutbay.springsecurityjwtexample.controller;

import com.burakkutbay.springsecurityjwtexample.dto.UserDto;
import com.burakkutbay.springsecurityjwtexample.dto.UserRequest;
import com.burakkutbay.springsecurityjwtexample.dto.UserResponse;
import com.burakkutbay.springsecurityjwtexample.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController — istifadəçi qeydiyyatı və autentifikasiyası üçün REST controller.
 * "/login" prefiksi ilə iki POST endpoint təmin edir:
 *  - "/login/save" — yeni istifadəçi qeydiyyatı,
 *  - "/login/auth" — mövcud istifadəçi üçün login (authentifikasiya).
 */
@RestController
@RequestMapping("/login") // Bütün metodlar üçün URL prefiksi "/login"
@RequiredArgsConstructor // Lombok, final olan sahələr üçün konstruktor yaradır (dependency injection üçün)
public class AuthenticationController {

    private final AuthenticationService authenticationService; // Authentication biznes loqikasını idarə edən servis

    /**
     * POST sorğusu "/login/save"
     * Yeni istifadəçinin qeydiyyatı üçün endpoint.
     * @param userDto — istifadəçinin qeydiyyat məlumatları (ad, username, şifrə)
     * @return UserResponse — daxilində JWT token olan cavab
     */
    @PostMapping("/save")
    public ResponseEntity<UserResponse> save(@RequestBody UserDto userDto) {
        // AuthenticationService.save() metodu istifadəçini saxlayır və token yaradır
        return ResponseEntity.ok(authenticationService.save(userDto));
    }

    /**
     * POST sorğusu "/login/auth"
     * İstifadəçinin login (autentifikasiya) üçün endpoint.
     * @param userRequest — istifadəçinin giriş məlumatları (username, şifrə)
     * @return UserResponse — daxilində JWT token olan cavab
     */
    @PostMapping("/auth")
    public ResponseEntity<UserResponse> auth(@RequestBody UserRequest userRequest) {
        // AuthenticationService.auth() metodu istifadəçini doğrulayır və token yaradır
        return ResponseEntity.ok(authenticationService.auth(userRequest));
    }
}
