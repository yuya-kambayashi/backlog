package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.repository.CommentRepository;
import com.bluejeanssystems.backlog.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects")
public class ViewController {
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;


    @GetMapping("/view/{issueId}")
    public String view(Model model,
                       @PathVariable("issueId") long issueId) {
        Optional<Issue> issue = issueRepository.findById(issueId);
        model.addAttribute("issue", issue.get());

        var comments = commentRepository.findByIssueId(issueId);
        model.addAttribute("comments", comments);

        return "layout/view";
    }
}
