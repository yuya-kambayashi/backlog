package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.model.TransactionLog;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import com.bluejeanssystems.backlog.repository.TransactionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class DashboardController {
    private final ProjectRepository projectRepository;
    private final TransactionLogRepository transactionLogRepository;

    @GetMapping("/dashboard")
    public String showList(Model model) {
        model.addAttribute("projects", projectRepository.findAll());

        var logs = transactionLogRepository.findAll();
        var map = new LinkedHashMap<String, List<TransactionLog>>();
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
        return "layout/dashboard";
    }
}
