package com.burakkutbay.springsecurityjwtexample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserResponse — istifadəçi uğurla qeydiyyatdan keçdikdə və ya login olduqda,
 * ona göndərilən JWT token-i saxlamaq üçün istifadə olunur.
 */
@Data // Lombok - getter, setter, toString, equals, hashCode metodlarını avtomatik yaradır
@Builder // Lombok - builder pattern dəstəyi (UserResponse.builder().token("...").build())
@NoArgsConstructor // Lombok - parametrsiz konstruktor yaradır
@AllArgsConstructor // Lombok - bütün sahələr üçün konstruktor yaradır
public class UserResponse {

    private String token; // İstifadəçiyə göndərilən JWT token (autentifikasiya üçün)
}
