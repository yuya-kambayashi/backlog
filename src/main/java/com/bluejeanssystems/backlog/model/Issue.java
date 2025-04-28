package com.bluejeanssystems.backlog.model;

import com.bluejeanssystems.backlog.util.Priority;
import com.bluejeanssystems.backlog.util.Resolution;
import com.bluejeanssystems.backlog.util.Status;
import com.bluejeanssystems.backlog.util.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
public class Issue {

    @EmbeddedId
    private IssueId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId") // ← 複合キー中の "projectId" にマッピング
    @JoinColumn(name = "project_id")
    private Project project;

    @NotBlank
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "versions_id")
    private Milestone versions;

    @Enumerated(EnumType.STRING)
    private Resolution resolution;

    @NotNull
    private LocalDate plannedStartDate;
    @NotNull
    private LocalDate limitDate;
    
    @Column(nullable = true)
    private LocalDate acutualStartDate;
    @Column(nullable = true)
    private LocalDate acutualEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigner_id")
    private SiteUser assigner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id")
    private SiteUser voter;


    public ZonedDateTime createAtJST() {
        return createdAt.atZone(ZoneId.of("Asia/Tokyo"));
    }

    public ZonedDateTime createAtIST() {
        return createdAt.atZone(ZoneId.of("Asia/Kolkata"));
    }

    public ZonedDateTime updatedAtJST() {
        return updatedAt.atZone(ZoneId.of("Asia/Tokyo"));
    }

    public ZonedDateTime updatedAtIST() {
        return updatedAt.atZone(ZoneId.of("Asia/Kolkata"));
    }

    public String getIssueKey() {
        return project.getProjectKey() + "-" + id.getIssueNumber();
    }
}
