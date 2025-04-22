package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.UserForm;
import com.bluejeanssystems.backlog.repository.SiteUserRepository;
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

    @GetMapping("/global/profile")
    public String view(Model model, Authentication loginUser) {

        model.addAttribute("username", loginUser.getName());
        model.addAttribute("authority", loginUser.getAuthorities());

        var userForm = new UserForm();
        var user = SecurityUtil.getCurrentUser();
        userForm.setUsername(user.getUsername());
        userForm.setEmail(user.getEmail());
        userForm.setPassword(user.getPassword());

        model.addAttribute("userForm", userForm);


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
        user.setEmail(userForm.getEmail());
        if (userForm.getUsername() != null && !userForm.getPassword().isBlank()) {
            user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
        }
        siteUserRepository.save(user);

        return "redirect:/global/profile";
    }
}
