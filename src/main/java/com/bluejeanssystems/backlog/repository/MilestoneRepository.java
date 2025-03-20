package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
}
