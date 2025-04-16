package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    @Query("SELECT m FROM Milestone m WHERE m.project.id = :projectId")
    List<Milestone> findAllBy(Long projectId);
}
