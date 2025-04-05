package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Comment;
import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.model.Milestone;
import com.bluejeanssystems.backlog.model.SiteUser;
import com.bluejeanssystems.backlog.repository.*;
import com.bluejeanssystems.backlog.service.MailService;
import com.bluejeanssystems.backlog.util.Resolution;
import com.bluejeanssystems.backlog.util.SecurityUtil;
import com.bluejeanssystems.backlog.util.Status;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectKey}/view")
public class ViewController {
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final SiteUserRepository userRepository;
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    private final MailService mailService;


    @GetMapping("/{issueId}")
    public String view(@PathVariable("projectKey") String projectKey,
                       Model model,
                       @PathVariable("issueId") long issueId,
                       HttpServletRequest request) throws Exception {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Issue not found: " + issueId));

        model.addAttribute("issue", issue);
        model.addAttribute("newComment", new Comment());
        model.addAttribute("comments", commentRepository.findByIssueId(issueId));
        model.addAttribute("editUrl", "edit/" + issueId);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("milestones", milestoneRepository.findAll());
        model.addAttribute("resolutions", Resolution.values());
        model.addAttribute("project", projectRepository.findByProjectKey(projectKey));

        return "layout/view";
    }

    @PostMapping("/addComment/{issueId}")
    public String comment(@PathVariable("projectKey") String projectKey,
                          @PathVariable("issueId") long issueId,
                          @Validated @ModelAttribute("newComment") Comment comment,
                          @RequestParam(name = "status", required = false) Status status,
                          @RequestParam(name = "assigner", required = false) SiteUser assigner,
                          @RequestParam(name = "milestone", required = false) Milestone milestone,
                          @RequestParam(name = "limitDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate limitDate, // 期限日
                          @RequestParam(name = "resolution", required = false) Resolution resolution,
                          BindingResult result,
                          Model model) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Issue not found: " + issueId));

        if (result.hasErrors()) {
            model.addAttribute("issue", issue);
            model.addAttribute("comments", commentRepository.findByIssueId(issueId));
            return "layout/view";
        }

        if (!comment.getComment().isEmpty()) {
            comment.setIssue(issue);
            comment.setCommenter(SecurityUtil.getCurrentUser());
            commentRepository.save(comment);
        }

        boolean updated = false;
        if (status != null) {
            issue.setStatus(status);
            updated = true;
        }
        if (assigner != null) {
            issue.setAssigner(assigner);
            updated = true;
        }
        if (milestone != null) {
            issue.setMilestone(milestone);
            updated = true;
        }
        if (limitDate != null) {
            issue.setLimitDate(limitDate);
            updated = true;
        }
        if (resolution != null) {
            issue.setResolution(resolution);
            updated = true;
        }

        if (updated) {
            issueRepository.save(issue);
        }

        try {
            mailService.sendEmail("kambayashi73@gmail.com", "sampleSubject", "sampleBody");
        } catch (Exception e) {

        }

        model.addAttribute("project", projectRepository.findByProjectKey(projectKey));


        return "redirect:/projects/" + projectKey + "/view/" + issueId;
    }
}