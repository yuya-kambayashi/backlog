package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT i FROM Project i WHERE i.projectKey = :projectKey")
    Project findByProjectKey(String projectKey);

    @Query("SELECT p FROM SiteUser u JOIN u.projects p WHERE u.id = :userId")
    List<Project> findProjectIdsByUserId(@Param("userId") Long userId);
}
