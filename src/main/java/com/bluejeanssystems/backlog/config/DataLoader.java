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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        var m3 = new Milestone();
        m3.setName("Build2_20250501");
        milestoneRepository.save(m3);

        var category = new Category();
        category.setName("UI");
        categoryRepository.save(category);

        List<SiteUser> users = new ArrayList<>();

        var user1 = new SiteUser();
        user1.setUsername("Ryotaro");
        user1.setPassword(passwordEncoder.encode("bluejeanssystems"));
        user1.setEmail("ryotaro@bluejeanssystems.com");
//        user.setGender(0);
//        user.setAdmin(true);
        user1.setAuthority(Authority.ADMIN);
        users.add(user1);


        var user2 = new SiteUser();
        user2.setUsername("Daisuke");
        user2.setPassword(passwordEncoder.encode("bluejeanssystems"));
        user2.setEmail("daisuke@bluejeanssystems.com");
//        user.setGender(0);
//        user.setAdmin(true);
        user2.setAuthority(Authority.USER);
        users.add(user2);


        var user3 = new SiteUser();
        user3.setUsername("Backlog管理者");
        user3.setPassword(passwordEncoder.encode("bluejeanssystems"));
        user3.setEmail("admin@bluejeanssystems.com");
//        user.setGender(0);
//        user.setAdmin(true);
        user3.setAuthority(Authority.USER);
        users.add(user3);

        siteUserRepository.saveAll(users);

        var issue1 = new Issue();
        issue1.setTitle("画面レイアウトを調整する");
        issue1.setDescription("""
                                ssss
                dddd
                xxxxx
                """);
        issue1.setStatus(Status.未対応);
        issue1.setType(Type.タスク);
        issue1.setPriority(Priority.中);
        issue1.setMilestone(m2);
        issue1.setCategory(category);
        issue1.setAssigner(user1);
        issue1.setVoter(user3);
        issue1.setLimitDate(LocalDate.of(2025, 12, 31));
        issueRepository.save(issue1);

        var comment1 = new Comment();
        comment1.setIssue(issue1);
        comment1.setComment("Hello World");
        comment1.setCommenter(user1);
        commentRepository.save(comment1);

        var comment2 = new Comment();
        comment2.setIssue(issue1);
        comment2.setComment("booooooo");
        comment2.setCommenter(user1);
        commentRepository.save(comment2);

        var issue2 = new Issue();
        issue2.setTitle("ガントチャートを作成する");
        issue2.setDescription("""
                                Hello World
                こんにちは
                ニーハオ
                """);
        issue2.setStatus(Status.完了);
        issue2.setType(Type.バグ);
        issue2.setPriority(Priority.高);
        issue2.setVersions(m2);
        issue2.setMilestone(m3);
        issue2.setCategory(category);
        issue2.setAssigner(user2);
        issue2.setVoter(user3);
        issue2.setLimitDate(LocalDate.of(2025, 4, 1));
        issueRepository.save(issue2);

    }
}
