package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ProjectController {
    private final IssueRepository issueRepository;

    @GetMapping("/project")
    public String showList(Model model){
        model.addAttribute("issues", issueRepository.findAll());
        return "projects";
    }
    @GetMapping("/page1")
    public String get1(Model model) {
        model.addAttribute("issues", issueRepository.findAll());
        return "layout/page1";
    }
    @GetMapping("/page2")
    public String get2(Model model) {
        model.addAttribute("issues", issueRepository.findAll());
        return "layout/page2";
    }
}