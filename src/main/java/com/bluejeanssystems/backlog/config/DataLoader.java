package com.bluejeanssystems.backlog.config;

import com.bluejeanssystems.backlog.model.*;
import com.bluejeanssystems.backlog.repository.*;
import com.bluejeanssystems.backlog.util.Authority;
import com.bluejeanssystems.backlog.util.Priority;
import com.bluejeanssystems.backlog.util.Status;
import com.bluejeanssystems.backlog.util.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final MilestoneRepository milestoneRepository;
    private final CategoryRepository categoryRepository;
    private final SiteUserRepository siteUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        var project = new Project();
        project.setName("Hoge");
        projectRepository.save(project);

        var m1 = new Milestone();
        m1.setName("未設定");
        milestoneRepository.save(m1);

        var m2 = new Milestone();
        m2.setName("Build1_20250401");
        milestoneRepository.save(m2);

        var category = new Category();
        category.setName("UI");
        categoryRepository.save(category);

        var user = new SiteUser();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("password"));
//        user.setEmail("admin@example.com");
//        user.setGender(0);
//        user.setAdmin(true);
        user.setAuthority(Authority.ADMIN);

        // ユーザが存在しない場合、登録します
        if (siteUserRepository.findByUsername(user.getUsername()).isEmpty()) {
            siteUserRepository.save(user);
        }

        var issue1 = new Issue();
        issue1.setTitle("Hoge");
        issue1.setDescription("HogeHoge");
        issue1.setStatus(Status.未対応);
        issue1.setType(Type.タスク);
        issue1.setPriority(Priority.中);
        issue1.setMilestone(m1);
        issue1.setCategory(category);
        issue1.setAssigner(user);
        issue1.setVoter(user);
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
        issue2.setMilestone(m2);
        issue2.setCategory(category);
        issue2.setAssigner(user);
        issue2.setVoter(user);
        issueRepository.save(issue2);

    }
}
