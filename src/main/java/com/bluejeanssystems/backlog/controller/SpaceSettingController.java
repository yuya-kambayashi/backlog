package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.SiteUser;
import com.bluejeanssystems.backlog.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SpaceSettingController {
    private final SiteUserRepository siteUserRepository;

    @GetMapping("/global/setting")
    public String view(Model model) {
        var users = siteUserRepository.findAll().stream().collect(Collectors.groupingBy(SiteUser::getTeam))
                .values().stream().flatMap(List::stream).toList();

        model.addAttribute("users", users);

        return "layout/space-setting";
    }
}