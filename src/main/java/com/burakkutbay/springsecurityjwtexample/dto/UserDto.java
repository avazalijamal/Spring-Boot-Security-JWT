package com.burakkutbay.springsecurityjwtexample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDto — yeni istifadəçi qeydiyyatdan keçərkən daxil etdiyi məlumatları saxlayır.
 * AuthenticationService-dəki save() metodu vasitəsilə istifadə olunur.
 */
@Data // Lombok - getter/setter, equals, hashCode, toString metodlarını avtomatik yaradır
@Builder // Lombok - builder pattern istifadə etmək üçün
@AllArgsConstructor // Bütün sahələri daxil edən konstruktor
@NoArgsConstructor // Parametrsiz konstruktor
public class UserDto {

    private String nameSurname; // İstifadəçinin adı və soyadı
    private String username;    // İstifadəçi adı (sistemə giriş üçün unikal olmalıdır)
    private String password;    // İstifadəçinin parolu (şifrələnəcək və DB-də saxlanacaq)
}
