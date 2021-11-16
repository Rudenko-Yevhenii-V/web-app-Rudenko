alter table if exists users_entity
    add constraint users_entity_unique_uniques_email unique (email);
alter table if exists word_user
    add constraint word_user_uniques_word_id_foreign foreign key (word_id) references words;
alter table if exists word_user
    add constraint word_user_uniques_user_id_foreign foreign key (user_id) references users_entity;