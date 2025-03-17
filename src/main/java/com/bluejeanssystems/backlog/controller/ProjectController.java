package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final IssueRepository issueRepository;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("issues", issueRepository.findAll());
        return "layout/home";
    }

    @GetMapping("/find")
    public String find(Model model) {
        model.addAttribute("issues", issueRepository.findAll());
        return "layout/find";
    }

}