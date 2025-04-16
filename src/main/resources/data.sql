-- Project
INSERT INTO project (name, project_key)
VALUES ('課題管理システム構築Prj', 'BTS');
INSERT INTO project (name, project_key)
VALUES ('競技プログラミングPrj', 'KYOPRO');

-- Milestone
INSERT INTO milestone (project_id, name, description)
 VALUES (1, '未設定', 'デフォルト'),
         (1, 'Build1_20250401', '初回'),
         (1, 'Build2_20250501', '最終'),
         (2, '未設定', 'デフォルト');

-- Category
INSERT INTO category (project_id, name, description)
VALUES (1, '未設定','デフォルト' ),
        (1, 'UI','デフォルト' ),
        (2, '未設定','デフォルト' ),
        (2, 'Up Solve','' ),
        (2, '鹿本','' );　


-- SiteUser (passwordはエンコード済みの値にする必要があります。以下は仮の例)
INSERT INTO site_user (username, password, email, authority)
VALUES ('Ryotaro', '$2a$10$Bf44kjpHyFzMEsiftvgNgOaKojMEyvbqLJ/Jy.bg7Zk4ZnDJ3mqLa', 'ryotaro@bluejeanssystems.com',
        'ADMIN'),
       ('Daisuke', '$2a$10$Bf44kjpHyFzMEsiftvgNgOaKojMEyvbqLJ/Jy.bg7Zk4ZnDJ3mqLa', 'daisuke@bluejeanssystems.com',
        'USER'),
       ('Backlog管理者', '$2a$10$Bf44kjpHyFzMEsiftvgNgOaKojMEyvbqLJ/Jy.bg7Zk4ZnDJ3mqLa',
        'admin@bluejeanssystems.com', 'USER');

-- Issue
INSERT INTO issue (project_id, issue_number, title, description, status, type, priority, milestone_id,
                   versions_id, category_id,
                   assigner_id, voter_id, limit_date, created_at, updated_at)
VALUES (1, 1, '画面レイアウトを調整する', 'ssss\n\n<img src="/images/screenshot.png">\ndddd\nxxxxx', '未対応', 'タスク',
        '中', 2, NULL, 1, 1, 3, '2025-12-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (1, 2, 'ガントチャートを作成する', 'Hello World\nこんにちは\nニーハオ', '完了', 'バグ', '高', 3, 2, 1, 2, 3,
        '2025-04-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 1, '青色になる', '目指せ青色', '完了', 'バグ', '高', 4, 2, 1, 2, 3, '2025-04-01', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

-- Comment
INSERT INTO comment (project_id, issue_number, comment_number, comment, commenter_id, created_at, updated_at)
VALUES (1, 1, 1, 'Hello World', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (1, 1, 2, 'booooooo', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 1, 1, 'booooooo', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 1, 2, 'booooooo', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);