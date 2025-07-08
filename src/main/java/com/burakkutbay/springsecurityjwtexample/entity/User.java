package com.burakkutbay.springsecurityjwtexample.entity;

import com.burakkutbay.springsecurityjwtexample.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User entity-si həm verilənlər bazasında istifadəçi məlumatlarını saxlayır,
 * həm də Spring Security üçün autentifikasiya məqsədilə istifadə olunur.
 */
@Data // Lombok - getter/setter/toString metodu avtomatik yaradılır
@Builder // Lombok - obyekt qurmaq üçün builder pattern
@NoArgsConstructor // Lombok - boş constructor
@AllArgsConstructor // Lombok - bütün sahələri daxil edən constructor
@Entity // JPA Entity kimi tanınır
@Table(name = "users") // Verilənlər bazasında bu cədvəlin adı "users" olacaq
public class User implements UserDetails { // Spring Security üçün UserDetails interfeysi implement olunur

    @Enumerated(EnumType.STRING)
    Role role; // İstifadəçinin rolu (USER və ya ADMIN) — enum olaraq saxlanılır

    @Id
    @GeneratedValue // Avtomatik ID yaradılır
    private Long id;

    private String nameSurname; // İstifadəçinin tam adı

    private String username; // Login üçün istifadə olunur

    private String password; // Şifrələnmiş şifrə

    /**
     * İstifadəçinin səlahiyyətlərini (authority/rol) qaytarır.
     * Burada sadəcə bir rol var və o da SimpleGrantedAuthority kimi qaytarılır.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Hesabın müddəti bitib-bitmədiyini bildirir.
     * Biz burada həmişə true qaytarırıq (aktivdir deməkdir).
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Hesabın kilidli olub-olmadığını bildirir.
     * Burada da default olaraq aktiv hesab qaytarılır.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Şifrənin müddəti bitib-bitmədiyini bildirir.
     * Bizim sistemdə belə bir məhdudiyyət yoxdur, true qaytarılır.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * İstifadəçi aktivdir ya yox — bu da true olaraq saxlanılır.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
