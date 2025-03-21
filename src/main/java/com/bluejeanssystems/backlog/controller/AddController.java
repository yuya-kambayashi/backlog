package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.repository.CategoryRepository;
import com.bluejeanssystems.backlog.repository.IssueRepository;
import com.bluejeanssystems.backlog.repository.MilestoneRepository;
import com.bluejeanssystems.backlog.util.Priority;
import com.bluejeanssystems.backlog.util.Status;
import com.bluejeanssystems.backlog.util.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects")
public class AddController {
    private final IssueRepository issueRepository;
    private final MilestoneRepository milestoneRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/add")
    public String add(@ModelAttribute("issue") Issue issue, Model model) {
        model.addAttribute("statuses", Status.values());
        model.addAttribute("types", Type.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("milestones", milestoneRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());

        return "layout/add";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("issue") Issue issue,
                      BindingResult result) {
        if (result.hasErrors()) {
            return "layout/add";
        }

        issueRepository.save(issue);

        return "redirect:/projects/view/" + issue.getId();
    }
}
