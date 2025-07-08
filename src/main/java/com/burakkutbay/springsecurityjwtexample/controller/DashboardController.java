package com.burakkutbay.springsecurityjwtexample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DashboardController — sadə bir REST API controller-dir.
 * "/dashboard" URL ünvanına GET sorğusu gələndə "Welcome Dashboard" mesajı qaytarır.
 */
@RestController // Bu annotasiya sinifin REST endpointləri təmin etməsini bildirir
@RequestMapping("/dashboard") // Bütün metodlar üçün əsas yol prefiksi "/dashboard" olur
public class DashboardController {

    /**
     * GET sorğusu ilə "/dashboard" endpointinə müraciət ediləndə işə düşür.
     * Sadəcə "Welcome Dashboard" mətnini HTTP 200 OK statusu ilə geri qaytarır.
     */
    @GetMapping
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Welcome Dashboard");
    }
}
