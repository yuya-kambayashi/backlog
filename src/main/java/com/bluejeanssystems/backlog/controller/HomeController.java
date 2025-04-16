package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.TransactionLog;
import com.bluejeanssystems.backlog.repository.*;
import com.bluejeanssystems.backlog.util.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectKey}")
public class HomeController {
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final MilestoneRepository milestoneRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionLogRepository transactionLogRepository;

    @GetMapping("/home")
    public String home(@PathVariable("projectKey") String projectKey, Model model) {
        var issues = issueRepository.findAllBy(projectKey);
        var project = projectRepository.findByProjectKey(projectKey);
        model.addAttribute("issues", issues);

        model.addAttribute("project", projectRepository.findByProjectKey(projectKey));


        model.addAttribute("未対応数", issues.stream().filter(issue -> issue.getStatus() == Status.未対応).count());
        model.addAttribute("処理中数", issues.stream().filter(issue -> issue.getStatus() == Status.処理中).count());
        model.addAttribute("処理済み数", issues.stream().filter(issue -> issue.getStatus() == Status.処理済み).count());
        model.addAttribute("完了数", issues.stream().filter(issue -> issue.getStatus() == Status.完了).count());

        // 最新の更新
        var logs = transactionLogRepository.findAll();
        var map = new LinkedHashMap<String, List<TransactionLog>>();
        logs = logs.stream().filter(log -> log.getIssue().getProject().getProjectKey().equals(projectKey)).toList();
        for (var log : logs) {
            if (map.containsKey(log.getDate())) {
                var list = map.get(log.getDate());
                list.add(log);
                map.put(log.getDate(), list);
            } else {
                var list = new ArrayList<TransactionLog>();
                list.add(log);
                map.put(log.getDate(), list);
            }
        }
        model.addAttribute("logMap", map);

        // 統計情報

        // 状態
        Map<String, Map<String, Integer>> statusMapMap = new LinkedHashMap<>();
        Map<String, Integer> statusMap = new HashMap<>();
        for (var issue : issues) {
            statusMap.put(issue.getStatus().name(), statusMap.getOrDefault(issue.getStatus().name(), 0) + 1);
        }
        statusMapMap.put("全体", statusMap);


        // マイルストーン
        Map<String, Map<String, Integer>> milestoneMapMap = new LinkedHashMap<>();
        for (var mileStone : milestoneRepository.findAllBy(project.getId())) {
            Map<String, Integer> milestoneMap = new HashMap<>();
            for (var issue : issues) {
                if (issue.getMilestone().equals(mileStone)) {
                    milestoneMap.put(issue.getStatus().name(), statusMap.getOrDefault(issue.getStatus().name(), 0) + 1);
                }
            }
            milestoneMapMap.put(mileStone.getName(), milestoneMap);
        }
        // カテゴリー
        Map<String, Map<String, Integer>> categoryMapMap = new LinkedHashMap<>();
        for (var c : categoryRepository.findAllBy(project.getId())) {
            Map<String, Integer> categoryMap = new HashMap<>();
            for (var issue : issues) {
                if (issue.getCategory().equals(c)) {
                    categoryMap.put(issue.getStatus().name(), statusMap.getOrDefault(issue.getStatus().name(), 0) + 1);
                }
            }
            categoryMapMap.put(c.getName(), categoryMap);
        }
        // JSON 文字列にして Thymeleaf に渡す
        try {
            model.addAttribute("statusMapMap", new ObjectMapper().writeValueAsString(statusMapMap));
            model.addAttribute("milestoneMapMap", new ObjectMapper().writeValueAsString(milestoneMapMap));
            model.addAttribute("categoryMapMap", new ObjectMapper().writeValueAsString(categoryMapMap));
        } catch (JsonProcessingException e) {
        }


        return "layout/home";
    }
}