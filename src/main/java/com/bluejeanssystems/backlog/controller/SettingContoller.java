package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Milestone;
import com.bluejeanssystems.backlog.repository.IssueRepository;
import com.bluejeanssystems.backlog.repository.MilestoneRepository;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import com.bluejeanssystems.backlog.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectKey}/setting")
public class SettingContoller {
    private final SiteUserRepository siteUserRepository;
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;


    @GetMapping
    public String view(@PathVariable("projectKey") String projectKey,
                       Model model) {
        var project = projectRepository.findByProjectKey(projectKey);

        model.addAttribute("projectKey", projectKey);
        model.addAttribute("users", siteUserRepository.findAll());
        model.addAttribute("milestones", milestoneRepository.findAllBy(project.getId()));

        return "layout/setting";
    }

    @GetMapping("/milestone/new")
    public String createForm(@PathVariable("projectKey") String projectKey,
                             Model model) {
        model.addAttribute("milestone", new Milestone());
        model.addAttribute("projectKey", projectKey);
        return "layout/setting-milestone-form";
    }

    @PostMapping("/milestone/save")
    public String save(@PathVariable("projectKey") String projectKey,
                       @ModelAttribute Milestone milestone) {
        var project = projectRepository.findByProjectKey(projectKey);
        milestone.setProject(project);
        milestoneRepository.save(milestone);
        return "redirect:/projects/" + projectKey + "/setting";
    }

    @GetMapping("/milestone/edit/{id}")
    public String editForm(@PathVariable("projectKey") String projectKey,
                           @PathVariable Long id, Model model) {
        model.addAttribute("milestone", milestoneRepository.findById(id).orElseThrow());
        model.addAttribute("projectKey", projectKey);

        return "layout/setting-milestone-form";
    }

    @PostMapping("/milestone/delete/{id}")
    public String delete(RedirectAttributes redirectAttributes,
                         @PathVariable("projectKey") String projectKey,
                         @PathVariable Long id) {

        var issues = issueRepository.findAllBy(projectKey);

        // 削除対象が設定済みの課題があるかをチェックする
        if (issues.stream().anyMatch(issue -> issue.getMilestone().getId().equals(id))) {
            redirectAttributes.addFlashAttribute("message", "マイルストーンが設定されている課題があるため削除できません。");
            redirectAttributes.addFlashAttribute("alertType", "warning");
            return "redirect:/projects/" + projectKey + "/setting";
        }

        milestoneRepository.deleteById(id);
        return "redirect:/projects/" + projectKey + "/setting";
    }
}
