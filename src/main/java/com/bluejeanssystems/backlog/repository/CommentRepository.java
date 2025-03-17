package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.issue.id = :issueId")
    List<Comment> findByIssueId(long issueId);
}