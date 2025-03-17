package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
