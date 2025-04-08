package com.bluejeanssystems.backlog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
public class Comment {
    @EmbeddedId
    private CommentId id;

    private String comment;

    @MapsId("issueId")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "project_id", referencedColumnName = "project_id"),
            @JoinColumn(name = "issue_number", referencedColumnName = "issue_number")
    })
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id")
    private SiteUser commenter;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    public ZonedDateTime createAtJST() {
        return createdAt.atZone(ZoneId.of("Asia/Tokyo"));
    }

    public ZonedDateTime createAtIST() {
        return createdAt.atZone(ZoneId.of("Asia/Kolkata"));
    }
}
