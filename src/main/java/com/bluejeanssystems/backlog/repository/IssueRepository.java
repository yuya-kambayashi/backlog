package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("SELECT i FROM Issue i WHERE i.project.projectKey = :projectKey")
    List<Issue> findAllBy(String projectKey);

    @Query("SELECT i FROM Issue i WHERE i.project.projectKey = :projectKey AND i.status = :status")
    List<Issue> findByStatus(String projectKey, Status status);

    @Query("SELECT i FROM Issue i WHERE i.project.projectKey = :projectKey AND i.id.issueNumber = :issueNumber")
    Issue findByIssueNumber(String projectKey, Long issueNumber);

    @Query("SELECT i FROM Issue i WHERE i.project.id = :projectId AND i.id.issueNumber = :issueNumber")
    Issue findByIssueNumber(Long projectId, Long issueNumber);

    @Query("SELECT MAX(i.id.issueNumber) FROM Issue i WHERE i.project.id = :projectId")
    Optional<Long> findMaxIssueNumberByProject(@Param("projectId") Long projectId);
}