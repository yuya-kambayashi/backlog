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

import java.text.SimpleDateFormat;
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

        List<Map<String, Object>> planMaps = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("x", new String[]{"2025-05-02", "2025-05-06"});
        map1.put("y", "BTS-1");
        map1.put("name", "画面レイアウトを調整する");
        map1.put("status", 2);
        planMaps.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("x", new String[]{"2025-05-06", "2025-05-12"});
        map2.put("y", "BTS-2");
        map2.put("name", "ガントチャートを作成する");
        map2.put("status", 1);
        planMaps.add(map2);

        List<Map<String, Object>> recordMaps = new ArrayList<>();
        Map<String, Object> map3 = new HashMap<>();
        map3.put("x", new String[]{"2025-05-12", "2025-05-16"});
        map3.put("y", "BTS-1");
        map3.put("name", "画面レイアウトを調整する");
        map3.put("status", 2);
        recordMaps.add(map3);
        Map<String, Object> map4 = new HashMap<>();
        map4.put("x", new String[]{"2025-05-16", "2025-06-12"});
        map4.put("x", new String[]{"2025-05-16", "2025-06-12"});
        map4.put("y", "BTS-2");
        map4.put("name", "ガントチャートを作成する");
        map4.put("status", 1);
        recordMaps.add(map4);

        var minDate = new Date(2025 - 1900, Calendar.MAY, 1);
        var maxDate = new Date(2025 - 1900, Calendar.JULY, 30);
        long days = (maxDate.getTime() - minDate.getTime()) / (1000 * 60 * 60 * 24);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            model.addAttribute("planData", new ObjectMapper().writeValueAsString(planMaps));
            model.addAttribute("recordData", new ObjectMapper().writeValueAsString(recordMaps));
            model.addAttribute("minDate", dateFormat.format(minDate));
            model.addAttribute("maxDate", dateFormat.format(maxDate));
            model.addAttribute("canvasWidth", days * 24);
            model.addAttribute("canvasHeight", days * 1.05);
        } catch (Exception e) {
        }

        return "layout/gantt";
    }
}
