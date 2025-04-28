package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Category;
import com.bluejeanssystems.backlog.model.Milestone;
import com.bluejeanssystems.backlog.model.SiteUser;
import com.bluejeanssystems.backlog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectKey}/setting")
public class SettingContoller {
    private final SiteUserRepository siteUserRepository;
    private final MilestoneRepository milestoneRepository;
    private final CategoryRepository categoryRepository;
    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;


    @GetMapping
    public String view(@PathVariable("projectKey") String projectKey,
                       Model model) {

        var project = projectRepository.findByProjectKey(projectKey);

        var users = siteUserRepository.findByIdWithProjects(project.getId()).stream().collect(Collectors.groupingBy(SiteUser::getTeam))
                .values().stream().flatMap(List::stream).toList();

        model.addAttribute("projectKey", projectKey);
        model.addAttribute("users", users);
        model.addAttribute("milestones", milestoneRepository.findAllBy(project.getId()));
        model.addAttribute("categories", categoryRepository.findAllBy(project.getId()));
        model.addAttribute("project", projectRepository.findByProjectKey(projectKey));

        return "layout/setting";
    }

    @GetMapping("/{target}/new")
    public String createForm(@PathVariable("projectKey") String projectKey,
                             @PathVariable("target") String target,
                             Model model) {
        if (target.equals("milestone")) {
            model.addAttribute("milestone", new Milestone());
        } else if (target.equals("category")) {
            model.addAttribute("category", new Category());
        }
        model.addAttribute("projectKey", projectKey);

        return "layout/setting-" + target + "-form";
    }

    @PostMapping("/{target}/save")
    public String save(@PathVariable("projectKey") String projectKey,
                       @PathVariable("target") String target,
                       @ModelAttribute Milestone milestone,
                       @ModelAttribute Category category
    ) {
        var project = projectRepository.findByProjectKey(projectKey);

        if (target.equals("milestone")) {
            milestone.setProject(project);
            milestoneRepository.save(milestone);
        } else if (target.equals("category")) {
            category.setProject(project);
            categoryRepository.save(category);
        }

        return "redirect:/projects/" + projectKey + "/setting";
    }

    @GetMapping("/{target}/edit/{id}")
    public String editForm(@PathVariable("projectKey") String projectKey,
                           @PathVariable("target") String target,
                           @PathVariable Long id, Model model) {
        if (target.equals("milestone")) {
            model.addAttribute("milestone", milestoneRepository.findById(id).orElseThrow());
        } else if (target.equals("category")) {
            model.addAttribute("category", categoryRepository.findById(id).orElseThrow());
        }
        model.addAttribute("projectKey", projectKey);

        return "layout/setting-" + target + "-form";
    }

    @PostMapping("/{target}/delete/{id}")
    public String delete(RedirectAttributes redirectAttributes,
                         @PathVariable("projectKey") String projectKey,
                         @PathVariable("target") String target,
                         @PathVariable Long id) {

        var issues = issueRepository.findAllBy(projectKey);
        if (target.equals("milestone")) {

            // 削除対象が設定済みの課題があるかをチェックする
            if (issues.stream().anyMatch(issue -> issue.getMilestone().getId().equals(id))) {
                redirectAttributes.addFlashAttribute("message1", "マイルストーンが設定されている課題があるため削除できません。");
                redirectAttributes.addFlashAttribute("alertType1", "warning");
                return "redirect:/projects/" + projectKey + "/setting";
            }
            milestoneRepository.deleteById(id);

        } else if (target.equals("category")) {

            // 削除対象が設定済みの課題があるかをチェックする
            if (issues.stream().anyMatch(issue -> issue.getCategory().getId().equals(id))) {
                redirectAttributes.addFlashAttribute("message2", "カテゴリーが設定されている課題があるため削除できません。");
                redirectAttributes.addFlashAttribute("alertType2", "warning");
                return "redirect:/projects/" + projectKey + "/setting";
            }
            categoryRepository.deleteById(id);
        }
        return "redirect:/projects/" + projectKey + "/setting";
    }
}
