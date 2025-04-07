package com.bluejeanssystems.backlog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class IssueId implements Serializable {

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "issue_number")
    private Long issueNumber;

    // 必須：equals & hashCode の実装
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IssueId)) return false;
        IssueId issueId = (IssueId) o;
        return Objects.equals(projectId, issueId.projectId) &&
                Objects.equals(issueNumber, issueId.issueNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, issueNumber);
    }
}