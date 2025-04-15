package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.TransactionLog;
import com.bluejeanssystems.backlog.repository.IssueRepository;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import com.bluejeanssystems.backlog.repository.TransactionLogRepository;
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
    private final TransactionLogRepository transactionLogRepository;

    @GetMapping("/home")
    public String home(@PathVariable("projectKey") String projectKey, Model model) {
        var issues = issueRepository.findAllBy(projectKey);
        model.addAttribute("issues", issues);

        model.addAttribute("project", projectRepository.findByProjectKey(projectKey));


        model.addAttribute("未対応数", issues.stream().filter(issue -> issue.getStatus() == Status.未対応).count());
        model.addAttribute("処理中数", issues.stream().filter(issue -> issue.getStatus() == Status.処理中).count());
        model.addAttribute("処理済み数", issues.stream().filter(issue -> issue.getStatus() == Status.処理済み).count());
        model.addAttribute("完了数", issues.stream().filter(issue -> issue.getStatus() == Status.完了).count());

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

        Map<String, Map<String, Integer>> ticketStatus = new LinkedHashMap<>();

        Map<String, Integer> aliceStatus = new HashMap<>();
        aliceStatus.put("未対応", 3);
        aliceStatus.put("処理中", 2);
        aliceStatus.put("処理済み", 5);
        aliceStatus.put("完了", 1);
        ticketStatus.put("Alice", aliceStatus);

        Map<String, Integer> bobStatus = new HashMap<>();
        bobStatus.put("未対応", 2);
        bobStatus.put("処理中", 4);
        bobStatus.put("処理済み", 1);
        bobStatus.put("完了", 0);
        ticketStatus.put("Bob", bobStatus);

        // JSON 文字列にして Thymeleaf に渡す
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(ticketStatus);
            model.addAttribute("ticketDataJson", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "layout/home";
    }
}