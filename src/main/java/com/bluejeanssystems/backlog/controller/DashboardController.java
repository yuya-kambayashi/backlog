package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RequiredArgsConstructor
@Controller
public class DashboardController {
    private final ProjectRepository projectRepository;

    @GetMapping("/dashboard")
    public String showList(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "layout/dashboard";
    }

    @PostMapping("/dashboard")
    public String handleMessage(@RequestParam(name = "message", required = false) String message, RedirectAttributes redirectAttributes) {
        try {
            if (message == null || message.trim().isEmpty()) {
                throw new IllegalArgumentException("メッセージを入力してください");
            }
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "エラーが発生しました: " + e.getMessage());
            return "redirect:/dashboard";
        }
    }

}
