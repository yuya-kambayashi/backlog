package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.repository.*;
import com.bluejeanssystems.backlog.util.Priority;
import com.bluejeanssystems.backlog.util.SecurityUtil;
import com.bluejeanssystems.backlog.util.Status;
import com.bluejeanssystems.backlog.util.Type;
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

    @GetMapping("/add")
    public String add(
            @PathVariable("projectKey") String projectKey,
            Model model) {
        model.addAttribute("statuses", Status.values());
        model.addAttribute("types", Type.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("milestones", milestoneRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("issue", new Issue());
        model.addAttribute("versions", milestoneRepository.findAll());
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
        issue.setProject(projectRepository.findByProjectKey(projectKey));

        issueRepository.save(issue);

        return "redirect:/projects/" + projectKey + "/view/" + issue.getId();
    }
}
