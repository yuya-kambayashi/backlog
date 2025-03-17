package com.bluejeanssystems.backlog.config;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.model.Project;
import com.bluejeanssystems.backlog.repository.IssueRepository;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import com.bluejeanssystems.backlog.util.Priority;
import com.bluejeanssystems.backlog.util.Status;
import com.bluejeanssystems.backlog.util.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        var project = new Project();
        project.setName("Hoge");
        projectRepository.save(project);

        var issue1 = new Issue();
        issue1.setTitle("Hoge");
        issue1.setDescription("HogeHoge");
        issue1.setStatus(Status.未対応);
        issue1.setType(Type.タスク);
        issue1.setPriority(Priority.中);
        issueRepository.save(issue1);
    }
}
