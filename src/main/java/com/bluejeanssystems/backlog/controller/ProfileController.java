package com.bluejeanssystems.backlog.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/global/profile")
    public String view(Model model, Authentication loginUser) {

        model.addAttribute("username", loginUser.getName());
        model.addAttribute("authority", loginUser.getAuthorities());

        return "layout/profile";
    }
}
