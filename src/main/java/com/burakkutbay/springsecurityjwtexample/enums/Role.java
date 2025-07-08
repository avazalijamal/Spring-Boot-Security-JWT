package com.burakkutbay.springsecurityjwtexample.enums;

/**
 * Role - istifadəçilərin sistemdəki rol və səlahiyyətlərini müəyyən edən enum.
 * Bu enum Spring Security tərəfindən istifadəçi rollarını idarə etmək üçün istifadə olunur.
 */
public enum Role {

    USER,   // Standart istifadəçi rolu, məhdud səlahiyyətlərə malikdir
    ADMIN;  // Administrator rolu, geniş səlahiyyətlərə malikdir
}
