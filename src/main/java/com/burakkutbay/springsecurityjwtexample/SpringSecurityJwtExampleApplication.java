package com.burakkutbay.springsecurityjwtexample;

// Spring Boot tətbiqləri üçün əsas komponentləri təmin edən paket
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bu sinif Spring Boot tətbiqinin başlanğıc nöqtəsidir.
 * @SpringBootApplication annotasiyası:
 * - @Configuration: Tətbiqin konfiqurasiya sinfi olduğunu bildirir.
 * - @EnableAutoConfiguration: Lazımi konfiqurasiyaları avtomatik yükləyir.
 * - @ComponentScan: Komponentləri (Controller, Service, Repository və s.) avtomatik axtarıb qeydiyyata alır.
 */
@SpringBootApplication
public class SpringSecurityJwtExampleApplication {

    /**
     * Proqramın giriş nöqtəsi (main metodu).
     * SpringApplication.run() metodu tətbiqi işə salır.
     *
     * @param args Komanda sətri arqumentləri
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtExampleApplication.class, args);
    }

}
