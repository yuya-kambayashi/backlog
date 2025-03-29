package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByUsername(String username);

    Optional<SiteUser> findByEmail(String email);
}
