package com.bluejeanssystems.backlog.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CommentId implements Serializable {

    @Embedded
    private IssueId issueId;

    private Long commentNumber;

    public CommentId() {
    }

    public CommentId(IssueId issueId, Long commentNumber) {
        this.issueId = issueId;
        this.commentNumber = commentNumber;
    }


    // ここでequalsとhashCodeを実装（重要）
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentId)) return false;
        CommentId that = (CommentId) o;
        return Objects.equals(issueId, that.issueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueId, commentNumber);
    }
}