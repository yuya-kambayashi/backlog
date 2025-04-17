package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SpaceSettingController {
    private final SiteUserRepository siteUserRepository;

    @GetMapping("/global/setting")
    public String view(Model model) {

        model.addAttribute("users", siteUserRepository.findAll());

        return "layout/space-setting";
    }
}