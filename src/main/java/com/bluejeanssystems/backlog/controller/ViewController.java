package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.repository.IssueRepository;
import com.bluejeanssystems.backlog.repository.MilestoneRepository;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import com.bluejeanssystems.backlog.repository.SiteUserRepository;
import com.bluejeanssystems.backlog.service.MailService;
import com.bluejeanssystems.backlog.util.Resolution;
import com.bluejeanssystems.backlog.util.Status;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectKey}/view")
public class ViewController {
    private final IssueRepository issueRepository;
    private final SiteUserRepository userRepository;
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    private final MailService mailService;


    @GetMapping("/{issueId}")
    public String view(@PathVariable("projectKey") String projectKey,
                       Model model,
                       @PathVariable("issueId") long issueNumber,
                       HttpServletRequest request) throws Exception {
        Issue issue = issueRepository.findByIssueNumber(projectKey, issueNumber);

        model.addAttribute("issue", issue);
        model.addAttribute("editUrl", "edit/" + issueNumber);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("milestones", milestoneRepository.findAll());
        model.addAttribute("resolutions", Resolution.values());
        model.addAttribute("project", projectRepository.findByProjectKey(projectKey));

        return "layout/view";
    }
}