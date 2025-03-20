package com.bluejeanssystems.backlog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class SettingContoller {

    @GetMapping("/setting")
    public String view(Model model) {

        return "layout/setting";
    }
}
