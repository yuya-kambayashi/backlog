package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT i FROM Project i WHERE i.projectKey = :projectKey")
    Project findByProjectKey(String projectKey);
}
