package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final IssueRepository issueRepository;

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("issues", issueRepository.findAll());
        return "layout/home";
    }
    @GetMapping("/add")
    public String add(@ModelAttribute("issue")Issue issue) {
        return "layout/add";
    }
    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("issue")Issue issue,
                      BindingResult result) {
        if (result.hasErrors()) {
            return "layout/add";
        }

        issueRepository.save(issue);

        //return "redirect:/projects/view";
        return "redirect:/projects/home";
    }
    @GetMapping("/find")
    public String find(Model model) {
        model.addAttribute("issues", issueRepository.findAll());
        return "layout/find";
    }
    @GetMapping("/view")
    public String view(Model model) {
        return "layout/view";
    }
}