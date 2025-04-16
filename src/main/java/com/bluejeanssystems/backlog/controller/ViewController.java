package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.*;
import com.bluejeanssystems.backlog.repository.*;
import com.bluejeanssystems.backlog.service.MailService;
import com.bluejeanssystems.backlog.service.TransactionLogService;
import com.bluejeanssystems.backlog.util.Resolution;
import com.bluejeanssystems.backlog.util.SecurityUtil;
import com.bluejeanssystems.backlog.util.Status;
import com.bluejeanssystems.backlog.util.TransactionType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private final TransactionLogService transactionLogService;

    @GetMapping("/{issueNumber}")
    public String view(@PathVariable("projectKey") String projectKey,
                       Model model,
                       @PathVariable("issueNumber") long issueNumber,
                       HttpServletRequest request) throws Exception {
        Issue issue = issueRepository.findByIssueNumber(projectKey, issueNumber);
        var project = projectRepository.findByProjectKey(projectKey);

        model.addAttribute("issue", issue);
        model.addAttribute("newComment", new Comment());
        model.addAttribute("comments", commentRepository.findByIssue(issue.getId().getProjectId(), issueNumber));
        model.addAttribute("editUrl", "edit/" + issueNumber);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("milestones", milestoneRepository.findAllBy(project.getId()));
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

        String updatedComment = "";


        if (status != null) {
            if (issue.getStatus() == null) {
                updatedComment += "状態:" + "未設定" + "⇒" + status.name() + "\n";
                issue.setStatus(status);
            } else if (!status.equals(issue.getStatus())) {
                updatedComment += "状態:" + issue.getStatus().name() + "⇒" + status.name() + "\n";
                issue.setStatus(status);
            }
        }

        if (assigner != null) {
            if (issue.getAssigner() == null) {
                updatedComment += "担当者:" + "未設定" + "⇒" + assigner.getUsername() + "\n";
                issue.setAssigner(assigner);
            } else if (!assigner.equals(issue.getAssigner())) {
                updatedComment += "担当者:" + issue.getAssigner().getUsername() + "⇒" + assigner.getUsername() + "\n";
                issue.setAssigner(assigner);
            }
        }

        if (milestone != null) {
            if (issue.getMilestone() == null) {
                updatedComment += "マイルストーン:" + "未設定" + "⇒" + milestone.getName() + "\n";
                issue.setMilestone(milestone);
            } else if (!milestone.equals(issue.getMilestone())) {
                updatedComment += "マイルストーン:" + issue.getMilestone().getName() + "⇒" + milestone.getName() + "\n";
                issue.setMilestone(milestone);
            }
        }

        if (limitDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            if (issue.getLimitDate() == null) {
                updatedComment += "期限日:" + "未設定" + "⇒" + limitDate.format(formatter) + "\n";
                issue.setLimitDate(limitDate);
            } else if (!limitDate.equals(issue.getLimitDate())) {
                updatedComment += "期限日:" + issue.getLimitDate().format(formatter) + "⇒" + limitDate.format(formatter) + "\n";
                issue.setLimitDate(limitDate);
            }
        }

        if (resolution != null) {
            if (issue.getResolution() == null) {
                updatedComment += "完了理由:" + "未設定" + "⇒" + resolution.name() + "\n";
                issue.setResolution(resolution);
            } else if (!resolution.equals(issue.getResolution())) {
                updatedComment += "完了理由:" + issue.getResolution().name() + "⇒" + resolution.name() + "\n";
                issue.setResolution(resolution);
            }
        }

        if (!updatedComment.isEmpty()) {
            issueRepository.save(issue);
        }

        String t = updatedComment;
        if (!t.isEmpty()) {
            t += "\n" + comment.getComment();
        }

        comment.setComment(t);


        if (!comment.getComment().isEmpty()) {
            comment.setIssue(issue);
            comment.setCommenter(SecurityUtil.getCurrentUser());
            // 次のコメント番号を取得
            Long nextCommentId = commentRepository.findMaxCommentNumberByProjectAndIssue(issue.getId().getProjectId(), issueNumber) + 1;// ID 作成
            CommentId commentId = new CommentId(new IssueId(issue.getId().getProjectId(), issueNumber), nextCommentId);
            comment.setId(commentId);

            commentRepository.save(comment);
        }


        try {
            mailService.sendEmail("kambayashi73@gmail.com", "sampleSubject", "sampleBody");
        } catch (Exception e) {

        }

        model.addAttribute("project", projectRepository.findByProjectKey(projectKey));

        transactionLogService.createTransactionLog(
                issue.getId().getProjectId(),
                issue.getId().getIssueNumber(),
                TransactionType.課題にコメント,
                comment.getComment()
        );


        return "redirect:/projects/" + projectKey + "/view/" + issueNumber;
    }
}