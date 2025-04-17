package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Milestone;
import com.bluejeanssystems.backlog.repository.MilestoneRepository;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import com.bluejeanssystems.backlog.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectKey}/setting")
public class SettingContoller {
    private final SiteUserRepository siteUserRepository;
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;


    @GetMapping
    public String view(@PathVariable("projectKey") String projectKey,
                       Model model) {
        var project = projectRepository.findByProjectKey(projectKey);

        model.addAttribute("projectKey", projectKey);
        model.addAttribute("users", siteUserRepository.findAll());
        model.addAttribute("milestones", milestoneRepository.findAllBy(project.getId()));

        return "layout/setting";
    }

    @GetMapping("/new")
    public String createForm(@PathVariable("projectKey") String projectKey,
                             Model model) {
        model.addAttribute("milestone", new Milestone());
        model.addAttribute("projectKey", projectKey);
        return "layout/setting-milestone-form";
    }

    @PostMapping("/save")
    public String save(@PathVariable("projectKey") String projectKey,
                       @ModelAttribute Milestone milestone) {
        var project = projectRepository.findByProjectKey(projectKey);
        milestone.setProject(project);
        milestoneRepository.save(milestone);
        return "redirect:/projects/" + projectKey + "/setting";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("projectKey") String projectKey,
                           @PathVariable Long id, Model model) {
        model.addAttribute("milestone", milestoneRepository.findById(id).orElseThrow());
        model.addAttribute("projectKey", projectKey);

        return "layout/setting-milestone-form";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        milestoneRepository.deleteById(id);
        return "redirect:/setting";
    }
}
