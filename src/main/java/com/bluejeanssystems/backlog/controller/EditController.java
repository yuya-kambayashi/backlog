package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.repository.*;
import com.bluejeanssystems.backlog.util.Priority;
import com.bluejeanssystems.backlog.util.Resolution;
import com.bluejeanssystems.backlog.util.Status;
import com.bluejeanssystems.backlog.util.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectKey}")
public class EditController {
    private final IssueRepository issueRepository;
    private final MilestoneRepository milestoneRepository;
    private final CategoryRepository categoryRepository;
    private final SiteUserRepository userRepository;
    private final ProjectRepository projectRepository;

    @GetMapping("/view/edit/{issueNumber}")
    public String edit(@PathVariable("projectKey") String projectKey,
                       @PathVariable("issueNumber") long issueNumber,
                       Model model) {
        var project = projectRepository.findByProjectKey(projectKey);

        model.addAttribute("statuses", Status.values());
        model.addAttribute("types", Type.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("milestones", milestoneRepository.findAllBy(project.getId()));
        model.addAttribute("categories", categoryRepository.findAllBy(project.getId()));
        model.addAttribute("users", userRepository.findByIdWithProjects(project.getId()));
        model.addAttribute("resolutions", Resolution.values());

        Issue issue = issueRepository.findByIssueNumber(projectKey, issueNumber);

        model.addAttribute("issue", issue);
        model.addAttribute("issueNumber", issueNumber);

        model.addAttribute("project", projectRepository.findByProjectKey(projectKey));

        return "layout/edit";
    }

    @PostMapping("/view/edit/{issueNumber}")
    public String save(@PathVariable("projectKey") String projectKey,
                       @PathVariable("issueNumber") Long issueNumber,
                       @ModelAttribute("issue") Issue issueMod,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "layout/add";
        }
        Issue issueOrg = issueRepository.findByIssueNumber(projectKey, issueNumber);

        issueOrg.setTitle(issueMod.getTitle());
        issueOrg.setDescription(issueMod.getDescription());
        issueOrg.setType(issueMod.getType());
        issueOrg.setPriority(issueMod.getPriority());
        issueOrg.setMilestone(issueMod.getMilestone());
        issueOrg.setCategory(issueMod.getCategory());
        issueOrg.setAssigner(issueMod.getAssigner());
        issueOrg.setVersions(issueMod.getVersions());
        issueOrg.setResolution(issueMod.getResolution());
        issueOrg.setPlannedStartDate(issueMod.getPlannedStartDate());
        issueOrg.setLimitDate(issueMod.getLimitDate());
        issueOrg.setAcutualStartDate(issueMod.getAcutualStartDate());
        issueOrg.setAcutualEndDate(issueMod.getAcutualEndDate());
        issueOrg.setUpdatedAt(issueMod.getCreatedAt());

        if (issueOrg != null) {
            issueRepository.save(issueOrg);
        }

        return "redirect:/projects/" + projectKey + "/view/" + issueNumber;
    }
}