package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
