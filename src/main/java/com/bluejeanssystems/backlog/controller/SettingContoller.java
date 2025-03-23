package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class SettingContoller {
    private final SiteUserRepository siteUserRepository;


    @GetMapping("/setting")
    public String view(Model model) {
        var users = siteUserRepository.findAll();

        model.addAttribute("users", siteUserRepository.findAll());

        return "layout/setting";
    }
}
