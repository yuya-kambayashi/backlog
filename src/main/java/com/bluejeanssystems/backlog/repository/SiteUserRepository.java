package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.SiteUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByUsername(String username);

    Optional<SiteUser> findByEmail(String email);

    @EntityGraph(attributePaths = {"projects"})
    Optional<SiteUser> findById(Long id);

    @Query("SELECT u FROM SiteUser u LEFT JOIN FETCH u.projects p WHERE p.id = :Id")
    List<SiteUser> findByIdWithProjects(@Param("Id") Long Id);
}
