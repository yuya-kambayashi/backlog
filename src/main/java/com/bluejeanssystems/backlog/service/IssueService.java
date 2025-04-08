package com.bluejeanssystems.backlog.service;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.model.IssueId;
import com.bluejeanssystems.backlog.model.Project;
import com.bluejeanssystems.backlog.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;

    @Transactional
    public Issue createIssue(Project project, Issue issue) {
        Long nextNumber = issueRepository
                .findMaxIssueNumberByProject(project.getId())
                .orElse(0L) + 1;

        IssueId id = new IssueId();
        id.setProjectId(project.getId());
        id.setIssueNumber(nextNumber);

        issue.setId(id);
        issue.setProject(project);

        return issueRepository.save(issue);
    }
}