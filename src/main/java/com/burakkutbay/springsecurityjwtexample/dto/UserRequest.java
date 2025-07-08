package com.burakkutbay.springsecurityjwtexample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRequest — istifadəçinin login olmaq üçün göndərdiyi məlumatları (username və password) saxlayır.
 * Bu DTO AuthenticationService-dəki auth() metodu vasitəsilə istifadə olunur.
 */
@Data // Lombok - getter/setter, toString və s. avtomatik yaradır
@Builder // Lombok - builder pattern üçün istifadə olunur
@AllArgsConstructor // Bütün sahələri daxil edən konstruktor
@NoArgsConstructor // Boş konstruktor
public class UserRequest {

    private String password;  // İstifadəçinin şifrəsi (login zamanı daxil etdiyi)
    private String username;  // İstifadəçinin istifadəçi adı (username)
}
