package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.IssueRepository;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectKey}/gantt")
public class GanttController {

    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;


    @GetMapping
    public String view(@PathVariable("projectKey") String projectKey,
                       Model model) {

        var project = projectRepository.findByProjectKey(projectKey);

        model.addAttribute("projectKey", projectKey);
        model.addAttribute("project", projectRepository.findByProjectKey(projectKey));

        var issues = issueRepository.findAllBy(projectKey);
        model.addAttribute("issues", issues);

        Map<String, Map<String, Integer>> issueMapMap = new LinkedHashMap<>();


        return "layout/gantt";
    }
}
