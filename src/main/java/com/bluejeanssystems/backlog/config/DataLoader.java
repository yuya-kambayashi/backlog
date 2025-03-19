package com.bluejeanssystems.backlog.config;

import com.bluejeanssystems.backlog.model.Comment;
import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.model.Project;
import com.bluejeanssystems.backlog.repository.IssueRepository;
import com.bluejeanssystems.backlog.repository.ProjectRepository;
import com.bluejeanssystems.backlog.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    @Override
    public void run(ApplicationArguments args) {

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

        var comment1 = new Comment();
        comment1.setIssue(issue1);
        comment1.setComment("Hello World");
        commentRepository.save(comment1);

        var comment2 = new Comment();
        comment2.setIssue(issue1);
        comment2.setComment("booooooo");
        commentRepository.save(comment2);

        var issue2 = new Issue();
        issue2.setTitle("Foo");
        issue2.setDescription("FooFoo");
        issue2.setStatus(Status.完了);
        issue2.setType(Type.バグ);
        issue2.setPriority(Priority.高);
        issueRepository.save(issue2);

    }
}
