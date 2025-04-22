package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.form.UserForm;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import com.bluejeanssystems.backlog.repository.SiteUserRepository;
import com.bluejeanssystems.backlog.repository.TeamRepository;
import com.bluejeanssystems.backlog.util.Authority;
import com.bluejeanssystems.backlog.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ProfileController {

    private final SiteUserRepository siteUserRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;

    @GetMapping("/global/profile")
    public String view(Model model, Authentication loginUser) {

        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("authorities", Authority.values());

        var userForm = new UserForm();
        var user = SecurityUtil.getCurrentUser();
        userForm.setUsername(user.getUsername());
        userForm.setPassword(user.getPassword());
        userForm.setAuthority(user.getAuthority());
        userForm.setTeamId(user.getTeam().getId());
        model.addAttribute("userForm", userForm);
        model.addAttribute("email", user.getEmail());


        return "layout/profile";
    }

    @PostMapping("/global/profile/save")
    public String save(@ModelAttribute("userForm") @Valid UserForm userForm,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            return "layout/profile";
        }

        if (userForm.getUsername() != null && !userForm.getPassword().isBlank()) {
            if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
//            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "パスワードが一致しません");
//            bindingResult.rejectValue("passwordConfirm", "error.userForm", "パスワードが一致しません");
                return "layout/profile";

            }
        }

        var user = SecurityUtil.getCurrentUser();
        user.setUsername(userForm.getUsername());
        if (userForm.getUsername() != null && !userForm.getPassword().isBlank()) {
            user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
        }
        user.setAuthority(userForm.getAuthority());
        user.setTeam(teamRepository.findById(userForm.getTeamId()).orElseThrow());

        siteUserRepository.save(user);

        return "redirect:/global/profile";
    }
}