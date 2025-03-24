package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.repository.CategoryRepository;
import com.bluejeanssystems.backlog.repository.IssueRepository;
import com.bluejeanssystems.backlog.repository.MilestoneRepository;
import com.bluejeanssystems.backlog.repository.SiteUserRepository;
import com.bluejeanssystems.backlog.util.Priority;
import com.bluejeanssystems.backlog.util.Status;
import com.bluejeanssystems.backlog.util.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects")
public class EditController {
    private final IssueRepository issueRepository;
    private final MilestoneRepository milestoneRepository;
    private final CategoryRepository categoryRepository;
    private final SiteUserRepository userRepository;

    @GetMapping("/view/edit/{issueId}")
    public String edit(@PathVariable("issueId") long issueId,
                       Model model) {
        model.addAttribute("statuses", Status.values());
        model.addAttribute("types", Type.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("milestones", milestoneRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("users", userRepository.findAll());

        Issue issue = issueRepository.findById(issueId).orElse(null);

        model.addAttribute("issue", issue);
        model.addAttribute("issueId", issueId);

        return "layout/edit";
    }

    @PostMapping("/view/edit/{issueId}")
    public String save(@PathVariable("issueId") long issueId,
                       @ModelAttribute("issue") Issue issueMod,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "layout/add";
        }
        Issue issueOrg = issueRepository.findById(issueId).orElse(null);

        issueOrg.setTitle(issueMod.getTitle());
        issueOrg.setDescription(issueMod.getDescription());
        issueOrg.setType(issueMod.getType());
        issueOrg.setPriority(issueMod.getPriority());
        issueOrg.setMilestone(issueMod.getMilestone());
        issueOrg.setCategory(issueMod.getCategory());
        issueOrg.setAssigner(issueMod.getAssigner());

        if (issueOrg != null) {
            issueRepository.save(issueOrg);
        }

        return "redirect:/projects/view/" + issueId;
    }
}