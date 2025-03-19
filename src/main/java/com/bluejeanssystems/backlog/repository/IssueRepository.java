package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    @Query("SELECT i FROM Issue i WHERE i.status = :status")
    List<Issue> findByStatus(Status status);

}
