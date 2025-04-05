package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectKey}")
public class FindController {
    private final IssueRepository issueRepository;

    @GetMapping("/find")
    public String find(
            @PathVariable("projectKey") String projectKey,
            @RequestParam(required = false) List<Integer> status,
            @RequestParam(required = false) List<Integer> priority,
            Model model
    ) throws UnsupportedEncodingException {
        var issues = issueRepository.findAllBy(projectKey);

        var result = issues.stream()
                .filter(issue -> (status == null || status.isEmpty() || status.contains(issue.getStatus().ordinal())))
                .filter(issue -> (priority == null || priority.isEmpty() || priority.contains(issue.getPriority().ordinal())))
                .collect(Collectors.toList());

        model.addAttribute("issues", result);


        return "layout/find";
    }
}