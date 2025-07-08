package com.burakkutbay.springsecurityjwtexample.repository;

import com.burakkutbay.springsecurityjwtexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository — Spring Data JPA interfeysi vasitəsilə User entity-si üçün
 * verilənlər bazasında CRUD əməliyyatlarını həyata keçirir.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * İstifadəçi adını (username) əsas götürərək DB-dən istifadəçi tapır.
     * Spring Data JPA bu metodu avtomatik implementasiya edir.
     *
     * @param username istifadəçi adı
     * @return Tapılmış istifadəçi Optional içində qaytarılır.
     */
    Optional<User> findByUsername(String username);
}
