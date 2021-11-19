alter table if exists users_entity
    add constraint users_entity_unique_uniques_email unique (email);
alter table if exists users_entity
    add constraint users_entity_unique_uniques_login unique (login);
alter table if exists answer
    add constraint answer_unique_uniques_foreign foreign key (question_id) references question;
alter table if exists lessons
    add constraint lessons_unique_uniques_foreign foreign key (theme_id) references theme;
alter table if exists question
    add constraint question_unique_uniques_foreign foreign key (test_id) references test;
alter table if exists test_users
    add constraint test_users_unique_uniques_foreign_test_id foreign key (test_id) references test;
alter table if exists test_users
    add constraint test_users_unique_uniques_foreign foreign key (user_user_id) references users_entity;
alter table if exists test_users
    add constraint answer_id_unique_uniques_foreign foreign key (answer_id) references answer;
alter table if exists todo
    add constraint todo_unique_uniques_foreign foreign key (user_id) references users_entity;
alter table if exists users_entity
    add constraint users_entity_unique_uniques_foreign_lesson_id foreign key (lesson_id) references lessons;
