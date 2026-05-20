package com.automationpractice.repository;

import com.automationpractice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByUsername(String username);
    List<AppUser> findByActiveTrue();
    List<AppUser> findByCountry(String country);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
