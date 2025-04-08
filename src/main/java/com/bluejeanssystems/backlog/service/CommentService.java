package com.bluejeanssystems.backlog.service;

import com.bluejeanssystems.backlog.model.Comment;
import com.bluejeanssystems.backlog.model.CommentId;
import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.model.IssueId;
import com.bluejeanssystems.backlog.repository.CommentRepository;
import com.bluejeanssystems.backlog.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;

    public Comment createComment(Long projectId, Long issueNumber, String comment) {
        // Issue を取得（存在チェック）
        IssueId issueId = new IssueId(projectId, issueNumber);
        Issue issue = issueRepository.findByIssueNumber(projectId, issueNumber);

        // 次のコメント番号を取得
        Long nextCommentId = commentRepository.findMaxCommentNumberByProjectAndIssue(projectId, issueNumber) + 1;

        // ID 作成
        CommentId commentId = new CommentId(issueId, nextCommentId);

        // Comment 作成
        Comment newComment = new Comment();
        newComment.setId(commentId);
        newComment.setIssue(issue);
        newComment.setComment(comment);

        return commentRepository.save(newComment);
    }
}
