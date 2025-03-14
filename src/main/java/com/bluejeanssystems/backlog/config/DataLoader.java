package com.bluejeanssystems.backlog.config;

import com.bluejeanssystems.backlog.model.Project;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final ProjectRepository projectRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        var project = new Project();
        project.setName("Hoge");
        projectRepository.save(project);
    }
}
