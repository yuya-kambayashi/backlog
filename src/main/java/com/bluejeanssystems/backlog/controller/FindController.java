package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.IssueRepository;
import com.bluejeanssystems.backlog.util.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/find")
public class FindController {
    private final IssueRepository issueRepository;

    @GetMapping("/")
    public String find(Model model,
                       @RequestParam(name = "statusId")Integer type) {
        // statusId
        // 1：すべて
        // 2：未対応
        // 3：処理中
        // 4：処理中
        // 5：完了
        // 6：完了以外
        switch (type) {
            case 1:
                model.addAttribute("issues", issueRepository.findAll());
                break;
            case 2:
                model.addAttribute("issues", issueRepository.findByStatus(Status.未対応));
                break;
            case 3:
                model.addAttribute("issues", issueRepository.findByStatus(Status.処理中));
                break;
            case 4:
                model.addAttribute("issues", issueRepository.findByStatus(Status.処理済み));
                break;
            case 5:
                model.addAttribute("issues", issueRepository.findByStatus(Status.完了));
                break;
            case 6:
                var issues = issueRepository.findByStatus(Status.未対応);
                issues.addAll(issueRepository.findByStatus(Status.処理中));
                issues.addAll(issueRepository.findByStatus(Status.処理済み));
                model.addAttribute("issues", issues);
                break;
        }

        return "layout/find";
    }
}
