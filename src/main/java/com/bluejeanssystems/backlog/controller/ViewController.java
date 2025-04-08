package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.*;
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


    @GetMapping("/{issueNumber}")
    public String view(@PathVariable("projectKey") String projectKey,
                       Model model,
                       @PathVariable("issueNumber") long issueNumber,
                       HttpServletRequest request) throws Exception {
        Issue issue = issueRepository.findByIssueNumber(projectKey, issueNumber);

        model.addAttribute("issue", issue);
        model.addAttribute("newComment", new Comment());
        model.addAttribute("comments", commentRepository.findByIssue(issue.getId().getProjectId(), issueNumber));
        model.addAttribute("editUrl", "edit/" + issueNumber);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("milestones", milestoneRepository.findAll());
        model.addAttribute("resolutions", Resolution.values());
        model.addAttribute("project", projectRepository.findByProjectKey(projectKey));

        return "layout/view";
    }

    @PostMapping("/addComment/{issueNumber}")
    public String comment(@PathVariable("projectKey") String projectKey,
                          @PathVariable("issueNumber") long issueNumber,
                          @Validated @ModelAttribute("newComment") Comment comment,
                          @RequestParam(name = "status", required = false) Status status,
                          @RequestParam(name = "assigner", required = false) SiteUser assigner,
                          @RequestParam(name = "milestone", required = false) Milestone milestone,
                          @RequestParam(name = "limitDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate limitDate, // 期限日
                          @RequestParam(name = "resolution", required = false) Resolution resolution,
                          BindingResult result,
                          Model model) {
        Issue issue = issueRepository.findByIssueNumber(projectKey, issueNumber);

        if (result.hasErrors()) {
            model.addAttribute("issue", issue);
            model.addAttribute("comments", commentRepository.findByIssue(issue.getId().getProjectId(), issue.getId().getIssueNumber()));
            return "layout/view";
        }

        if (!comment.getComment().isEmpty()) {
            comment.setIssue(issue);
            comment.setCommenter(SecurityUtil.getCurrentUser());
            // 次のコメント番号を取得
            Long nextCommentId = commentRepository.findMaxCommentNumberByProjectAndIssue(issue.getId().getProjectId(), issueNumber) + 1;// ID 作成
            CommentId commentId = new CommentId(new IssueId(issue.getId().getProjectId(), issueNumber), nextCommentId);
            comment.setId(commentId);

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


        return "redirect:/projects/" + projectKey + "/view/" + issueNumber;
    }
}