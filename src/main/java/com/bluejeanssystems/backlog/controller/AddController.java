package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.repository.*;
import com.bluejeanssystems.backlog.service.IssueService;
import com.bluejeanssystems.backlog.service.TransactionLogService;
import com.bluejeanssystems.backlog.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectKey}")
public class AddController {
    private final IssueRepository issueRepository;
    private final MilestoneRepository milestoneRepository;
    private final CategoryRepository categoryRepository;
    private final SiteUserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TransactionLogRepository transactionLogRepository;

    private final IssueService issueService;
    private final TransactionLogService transactionLogService;


    @GetMapping("/add")
    public String add(
            @PathVariable("projectKey") String projectKey,
            Model model) {
        var project = projectRepository.findByProjectKey(projectKey);

        model.addAttribute("statuses", Status.values());
        model.addAttribute("types", Type.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("milestones", milestoneRepository.findAllBy(project.getId()));
        model.addAttribute("categories", categoryRepository.findAllBy(project.getId()));
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("issue", new Issue());
        model.addAttribute("versions", milestoneRepository.findAllBy(project.getId()));
        model.addAttribute("project", projectRepository.findByProjectKey(projectKey));


        return "layout/add";
    }

    @PostMapping("/add")
    public String add(
            @PathVariable("projectKey") String projectKey,
            @Validated @ModelAttribute("issue") Issue issue,
            BindingResult result) {
        if (result.hasErrors()) {
            return "layout/add";
        }
        issue.setVoter(SecurityUtil.getCurrentUser());

        var project = projectRepository.findByProjectKey(projectKey);

        issueService.createIssue(project, issue);

        transactionLogService.createTransactionLog(
                issue.getId().getProjectId(),
                issue.getId().getIssueNumber(),
                TransactionType.課題を追加,
                issue.getDescription()
        );

        return "redirect:/projects/" + projectKey + "/view/" + issue.getId().getIssueNumber();
    }
}
