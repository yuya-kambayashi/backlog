package com.bluejeanssystems.backlog.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/projects/profile")
    public String view(Model model, Authentication loginUser) {

        model.addAttribute("loginUser", loginUser);

        return "layout/profile";
    }
}
