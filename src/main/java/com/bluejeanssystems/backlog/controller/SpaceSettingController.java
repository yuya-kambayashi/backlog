package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.SiteUser;
import com.bluejeanssystems.backlog.model.Team;
import com.bluejeanssystems.backlog.repository.SiteUserRepository;
import com.bluejeanssystems.backlog.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/global/setting")
public class SpaceSettingController {
    private final SiteUserRepository siteUserRepository;
    private final TeamRepository teamRepository;

    @GetMapping
    public String view(Model model) {
        var users = siteUserRepository.findAll().stream().collect(Collectors.groupingBy(SiteUser::getTeam))
                .values().stream().flatMap(List::stream).toList();

        model.addAttribute("users", users);
        model.addAttribute("teams", teamRepository.findAll());

        return "layout/space-setting";
    }

    @GetMapping("/{target}/new")
    public String createForm(@PathVariable("target") String target,
                             Model model) {
        if (target.equals("user")) {
            model.addAttribute("user", new SiteUser());
        } else if (target.equals("team")) {
            model.addAttribute("team", new Team());
        }

        return "layout/space-setting-" + target + "-form";
    }

    @PostMapping("/{target}/save")
    public String save(@PathVariable("target") String target,
                       @ModelAttribute SiteUser siteUser,
                       @ModelAttribute Team team
    ) {
        if (target.equals("user")) {
            siteUserRepository.save(siteUser);
        } else if (target.equals("team")) {
            teamRepository.save(team);
        }

        return "redirect:/global/setting";
    }

    @GetMapping("/{target}/edit/{id}")
    public String editForm(@PathVariable("target") String target,
                           @PathVariable Long id, Model model) {
        if (target.equals("user")) {
            model.addAttribute("user", siteUserRepository.findById(id).orElseThrow());
        } else if (target.equals("team")) {
            model.addAttribute("team", teamRepository.findById(id).orElseThrow());
        }

        return "layout/space-setting-" + target + "-form";
    }

    @PostMapping("/{target}/delete/{id}")
    public String delete(RedirectAttributes redirectAttributes,
                         @PathVariable("target") String target,
                         @PathVariable Long id) {

        if (target.equals("user")) {

            // 削除対象が設定済みの課題があるかをチェックする
//            if (issues.stream().anyMatch(issue -> issue.getMilestone().getId().equals(id))) {
//                redirectAttributes.addFlashAttribute("message1", "マイルストーンが設定されている課題があるため削除できません。");
//                redirectAttributes.addFlashAttribute("alertType1", "warning");
//                return "redirect:/projects/" + projectKey + "/setting";
//            }
            siteUserRepository.deleteById(id);

        } else if (target.equals("team")) {


            // 削除対象が設定済みのユーザがあるかをチェックする
            if (siteUserRepository.findAll().stream().anyMatch(user -> user.getTeam().getId().equals(id))) {
                redirectAttributes.addFlashAttribute("messageTeam", "チームに所属するユーザがいるため削除できません。");
                redirectAttributes.addFlashAttribute("alertTypeTeam", "warning");
                return "redirect:/global/setting";
            }
            teamRepository.deleteById(id);
        }
        return "redirect:/global/setting";
    }


}