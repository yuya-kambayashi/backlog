package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/find")
public class FindController {
    private final IssueRepository issueRepository;

    @GetMapping("/")
    public String find(Model model,
                       @RequestParam(name = "type")Integer type) {
        if (type == 1) {
            model.addAttribute("issues", issueRepository.findAll());
        }
        if (type == 2) {
            model.addAttribute("issues", issueRepository.findById(1L));
        }

        return "layout/find";
    }
}
