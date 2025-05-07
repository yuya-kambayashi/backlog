package com.bluejeanssystems.backlog.controller;

import com.bluejeanssystems.backlog.repository.IssueRepository;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectKey}/gantt")
public class GanttController {

    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;


    @GetMapping
    public String view(@PathVariable("projectKey") String projectKey,
                       Model model) {

        var project = projectRepository.findByProjectKey(projectKey);

        model.addAttribute("projectKey", projectKey);
        model.addAttribute("project", projectRepository.findByProjectKey(projectKey));

        var issues = issueRepository.findAllBy(projectKey);
        model.addAttribute("issues", issues);

        Map<String, Map<String, Integer>> issueMapMap = new LinkedHashMap<>();

        List<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("x", new String[]{"2025-05-02", "2025-05-06"});
        map1.put("y", "Task 1");
        map1.put("name", "James");
        map1.put("status", 2);
        maps.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("x", new String[]{"2025-05-06", "2025-05-12"});
        map2.put("y", "Task 2");
        map2.put("name", "Luna");
        map2.put("status", 1);
        maps.add(map2);
        //        {x: ['2025-05-02', '2025-05-06'], y: 'Task 1', name: 'James', status: 2},
//        {x: ['2025-05-06', '2025-05-12'], y: 'Task 2', name: 'Luna', status: 2},
//        {x: ['2025-05-08', '2025-05-12'], y: 'Task 3', name: 'David', status: 2},
//        {x: ['2025-05-12', '2025-05-21'], y: 'Task 4', name: 'Lily', status: 1},
//        {x: ['2025-05-15', '2025-05-24'], y: 'Task 5', name: 'Santiago', status: 0},
//        {x: ['2025-05-18', '2025-05-30'], y: 'Task 6', name: 'James', status: 1},
//        {x: ['2025-06-12', '2025-06-21'], y: 'Task 7', name: 'Lily', status: 1},
//        {x: ['2025-06-15', '2025-06-24'], y: 'Task 8', name: 'Santiago', status: 0},
//        {x: ['2025-06-18', '2025-06-30'], y: 'Task 9', name: 'James', status: 1}
        try {
            model.addAttribute("maps", new ObjectMapper().writeValueAsString(maps));
        } catch (Exception e) {
        }

        return "layout/gantt";
    }
}
