package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.issue.id.projectId = :projectId AND c.issue.id.issueNumber = :issueNumber")
    List<Comment> findByIssue(@Param("projectId") Long projectId,
                              @Param("issueNumber") Long issueNumber);

    @Query("SELECT COALESCE(MAX(c.id.commentNumber), 0) FROM Comment c " +
            "WHERE c.id.issueId.projectId = :projectId " +
            "AND c.id.issueId.issueNumber = :issueNumber")
    Long findMaxCommentNumberByProjectAndIssue(@Param("projectId") Long projectId,
                                               @Param("issueNumber") Long issueNumber);
}