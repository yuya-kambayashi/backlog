package com.bluejeanssystems.backlog.model;

import com.bluejeanssystems.backlog.util.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String message;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "project_id", referencedColumnName = "project_id"),
            @JoinColumn(name = "issue_number", referencedColumnName = "issue_number")
    })
    private Issue issue;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public ZonedDateTime createAtJST() {
        return createdAt.atZone(ZoneId.of("Asia/Tokyo"));
    }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return createAtJST().format(formatter);
    }

    public String getActionMessage() {
        return issue.getVoter().getUsername() + " さんが " + transactionType.name();
    }

    public String getIssueLink() {
        return "/projects/" + issue.getProject().getProjectKey() + "/view/" + issue.getId().getIssueNumber();
    }
}
