package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.MilestoneRepository;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import com.bluejeanssystems.backlog.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectKey}")
public class SettingContoller {
    private final SiteUserRepository siteUserRepository;
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;


    @GetMapping("/setting")
    public String view(@PathVariable("projectKey") String projectKey,
                       Model model) {
        var project = projectRepository.findByProjectKey(projectKey);

        model.addAttribute("users", siteUserRepository.findAll());
        model.addAttribute("milestones", milestoneRepository.findAllBy(project.getId()));

        return "layout/setting";
    }
}
