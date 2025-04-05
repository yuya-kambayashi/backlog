package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.IssueRepository;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;

    @GetMapping("/{projectKey}/home")
    public String home(@PathVariable("projectKey") String projectKey, Model model) {
        var issues = issueRepository.findAllBy(projectKey);
        model.addAttribute("issues", issues);

        var project = projectRepository.findByProjectKey(projectKey);
        model.addAttribute("project", project);

        return "layout/home";
    }


}