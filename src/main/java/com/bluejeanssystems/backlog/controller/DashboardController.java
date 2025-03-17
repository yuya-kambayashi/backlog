package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class DashboardController {
    private final ProjectRepository projectRepository;

    @GetMapping("/dashboard")
    public String showList(Model model){
        model.addAttribute("projects", projectRepository.findAll());
        return "dashboard";
    }

}
