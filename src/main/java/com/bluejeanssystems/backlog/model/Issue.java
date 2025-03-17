package com.bluejeanssystems.backlog.model;

import com.bluejeanssystems.backlog.util.IssueStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;
    @NotBlank
    private String description;

    @Enumerated(EnumType.STRING)
    private IssueStatus status;
}
