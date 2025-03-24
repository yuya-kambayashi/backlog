package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Comment;
import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/view")
public class ViewController {
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final MilestoneRepository milestoneRepository;
    private final CategoryRepository categoryRepository;
    private final SiteUserRepository userRepository;

    @GetMapping("/{issueId}")
    public String view(Model model,
                       @PathVariable("issueId") long issueId,
                       HttpServletRequest request) throws Exception {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Issue not found: " + issueId));

        model.addAttribute("issue", issue);
        model.addAttribute("newComment", new Comment());
        model.addAttribute("comments", commentRepository.findByIssueId(issueId));
        String uri = request.getRequestURI();
        uri += "/edit";
        uri = "edit/" + issueId;
        model.addAttribute("editUrl", uri);

        return "layout/view";
    }

    @PostMapping("/addComment/{issueId}")
    public String comment(@PathVariable("issueId") long issueId,
                          @Validated @ModelAttribute("newComment") Comment comment,
                          BindingResult result,
                          Model model) throws Exception {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Issue not found: " + issueId));

        if (result.hasErrors()) {
            model.addAttribute("issue", issue);
            model.addAttribute("comments", commentRepository.findByIssueId(issueId));
            return "layout/view";
        }

        comment.setIssue(issue);
        commentRepository.save(comment);

        return "redirect:/projects/view/" + issueId;
    }
}