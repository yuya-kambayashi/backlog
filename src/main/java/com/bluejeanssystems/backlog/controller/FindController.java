package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class FindController {
    private final IssueRepository issueRepository;

    @GetMapping("/find")
    public String find(
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) List<String> type,
            Model model
    ) {
        var issues = issueRepository.findAll();

        var result = issues.stream()
                        .filter(issue -> (status == null  || status == issue.getStatus().ordinal()))
                        .collect(Collectors.toList());

        model.addAttribute("issues", result);


        return "layout/find";
    }
}