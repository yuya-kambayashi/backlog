package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
