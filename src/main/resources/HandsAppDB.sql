drop table if exists likes;
drop table if exists post_tag;
drop table if exists tag;
drop table if exists comment;
drop table if exists post;
drop table if exists user_role;
drop table if exists role;
drop table if exists users;
drop table if exists subscriptions;
drop table if exists bugs;
drop table if exists dialogs;
drop table if exists messages;
drop table if exists user_dialog;

CREATE TABLE users (
    user_id bigserial PRIMARY KEY,
    username varchar(150) NOT NULL UNIQUE,
    password varchar(150) NOT NULL,
    surname varchar(150),
    firstname varchar(150),
    photo varchar(150),
    info varchar(255),
    www varchar(150),
    email varchar(50),
    phone varchar(20),
    sex varchar(7),
    created_at timestamp NOT NULL,
    is_active boolean,
    is_google boolean,
    is_hidden boolean,
    is_closed boolean,
    last_visit timestamp without time zone,
    previous_visit timestamp without time zone);

CREATE TABLE role (
    role_id int PRIMARY KEY,
    name varchar(50) NOT NULL);

CREATE TABLE user_role (
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    role_id int REFERENCES role(role_id),
    PRIMARY KEY (user_id, role_id));

CREATE TABLE subscriptions (
    sub_id bigserial PRIMARY KEY,
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    user_sub_id bigint REFERENCES users(user_id) ON DELETE CASCADE);

CREATE TABLE likes (
    like_id bigserial PRIMARY KEY,
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    post_id bigint REFERENCES post(post_id) ON DELETE CASCADE);

CREATE TABLE post (
    post_id bigserial PRIMARY KEY,
    photo varchar(250),
    extention varchar(5),
    storage_type varchar(5);
    content text NOT NULL,
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone);

CREATE TABLE comment (
    comment_id bigserial PRIMARY KEY,
    post_id bigint REFERENCES post(post_id) ON DELETE CASCADE,
    user_id bigint REFERENCES users(user_id),
    comment_text text NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone);

CREATE TABLE bugs (
    bug_id bigserial PRIMARY KEY,
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    bug_text text NOT NULL,
    username text,
    created_at timestamp without time zone);

CREATE TABLE dialogs (
    dialog_id bigserial PRIMARY KEY,
    name_dialog varchar(150),
    img_dialog  varchar(150),
    created_at timestamp without time zone,
    updated_at timestamp without time zone);

CREATE TABLE user_dialog (
    ud_id bigserial PRIMARY KEY,
    user_id bigint REFERENCES users(user_id),
    dialog_id bigint REFERENCES dialogs(dialog_id)
--    UNIQUE (user_id, dialog_id)
    );

CREATE TABLE messages (
    message_id bigserial PRIMARY KEY,
    dialog_id bigint REFERENCES dialogs(dialog_id),
    user_id bigint REFERENCES users(user_id),
    message_text text NOT NULL,
    message_file text,
    extention varchar(5),
    is_read boolean,
    created_at timestamp without time zone);

CREATE TABLE music (
    music_id bigserial PRIMARY KEY,
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    file_name varchar(250),
    project_name varchar(250),
    song_name varchar(250),
    song_year varchar(4));

CREATE TABLE todo (
    todo_id bigserial PRIMARY KEY,
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    todo_text varchar(250),
    is_complete boolean,
    deadline_at timestamp without time zone);

CREATE TABLE wishlist (
    wishlist_id bigserial PRIMARY KEY,
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    photo varchar(250),
    link varchar(500),
    name_wish varchar(350),
    description varchar(500),
    price varchar(15),
    is_secret boolean,
    is_done boolean,
    is_booking boolean,
    booking_user bigint,
    created_at timestamp without time zone);

CREATE TABLE wishlist_private (
    private_id bigserial PRIMARY KEY,
    wishlist_id bigint REFERENCES wishlist(wishlist_id) ON DELETE CASCADE,
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE);

CREATE TABLE playlist (
    playlist_id bigserial PRIMARY KEY,
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    playlist_name varchar(100));

CREATE TABLE playlist_music (
    playlist_music_id bigserial PRIMARY KEY,
    music_id bigint REFERENCES music(music_id) ON DELETE CASCADE,
    playlist_id bigint REFERENCES playlist(playlist_id) ON DELETE CASCADE);

insert into dialogs (created_at) values ('2020-12-12 16:10:23'::timestamp);
insert into dialogs (created_at) values ('2021-12-12 16:10:23'::timestamp);
insert into dialogs (created_at) values ('2019-12-12 16:10:23'::timestamp);
insert into dialogs (created_at) values ('2020-12-12 16:10:23'::timestamp);

insert into messages (dialog_id, user_id, message_text, is_read, created_at) values (1, 1, 'Тестовое сообщение', false, '2022-12-12 16:10:23'::timestamp);
insert into messages (dialog_id, user_id, message_text, is_read, created_at) values (1, 2, 'Тестовое сообщение', false, '2022-12-12 16:10:23'::timestamp);
insert into messages (dialog_id, user_id, message_text, is_read, created_at) values (2, 1, 'Тестовое сообщение', false, '2022-12-12 16:10:23'::timestamp);
insert into messages (dialog_id, user_id, message_text, is_read, created_at) values (2, 3, 'Тестовое сообщение', false, '2022-12-12 16:10:23'::timestamp);
insert into messages (dialog_id, user_id, message_text, is_read, created_at) values (2, 1, 'Тестовое сообщение', false, '2022-12-12 16:10:23'::timestamp);
insert into messages (dialog_id, user_id, message_text, is_read, created_at) values (2, 3, 'Тестовое сообщение', false, '2022-12-12 16:10:23'::timestamp);
insert into messages (dialog_id, user_id, message_text, is_read, created_at) values (3, 2, 'Тестовое сообщение', false, '2022-12-12 16:10:23'::timestamp);
insert into messages (dialog_id, user_id, message_text, is_read, created_at) values (3, 3, 'Тестовое сообщение', false, '2022-12-12 16:10:23'::timestamp);
insert into messages (dialog_id, user_id, message_text, is_read, created_at) values (4, 2, 'Тестовое сообщение', false, '2022-12-12 16:10:23'::timestamp);
insert into messages (dialog_id, user_id, message_text, is_read, created_at) values (4, 4, 'Тестовое сообщение', false, '2022-12-12 16:10:23'::timestamp);
insert into messages (dialog_id, user_id, message_text, is_read, created_at) values (4, 2, 'Тестовое сообщение', false, '2022-12-12 16:10:23'::timestamp);

insert into role values (1, 'ADMIN');
insert into role values (2, 'USER');

insert into users (username, password, created_at, is_active) values ('admin', '$2a$10$nzYRhy8lWbVTxvr7xnZFqu8BLBP0pNQaTU1hslTl0xoR6yA2CgsbC', now()::timestamp, true);
insert into users (username, password, created_at, is_active) values ('pavlov89312', '$2a$10$nzYRhy8lWbVTxvr7xnZFqu8BLBP0pNQaTU1hslTl0xoR6yA2CgsbC', now()::timestamp, true);
insert into users (username, password, created_at, is_active) values ('user1', '$2a$10$nzYRhy8lWbVTxvr7xnZFqu8BLBP0pNQaTU1hslTl0xoR6yA2CgsbC', now()::timestamp, true);

insert into user_role values (1, 1);
insert into user_role values (2, 2);

insert into subscriptions (user_id, user_sub_id) values (4, 11);
insert into subscriptions (user_id, user_sub_id) values (4, 2);

insert into post (photo, content, user_id, created_at, updated_at) values ('test.jpg', 'It''s all good!', 2, '2020-12-12 16:10:23'::timestamp, null);
insert into post (photo, content, user_id, created_at, updated_at) values ('test.jpg', 'It''s all ok!', 3, '2022-12-12 16:10:23'::timestamp, null);
insert into post (photo, content, user_id, created_at, updated_at) values ('test2.jpg', 'It''s all bad!', 2, '2020-12-12 16:10:23'::timestamp, null);

insert into comment (post_id, comment_text, created_at) values (2, 'Nice!', current_timestamp);
insert into comment (post_id, comment_text, created_at) values (1, 'Awesome!', current_timestamp);
insert into comment (post_id, comment_text, created_at) values (1, 'Excellent!', current_timestamp);
insert into comment (post_id, comment_text, created_at) values (1, 'Wonderful!', current_timestamp);
insert into comment (post_id, comment_text, created_at) values (3, 'Disgusting!', current_timestamp);
insert into comment (post_id, comment_text, created_at) values (3, 'Atrocious!', current_timestamp);

-- Создание представления (View) для рекомендаций
create view recommendations AS
select u.user_id, u.username, u.password, u.surname, u.firstname, u.photo, u.info, u.www, u.email, u.phone, u.sex, u.created_at, u.is_active, u.is_google, u.is_hidden, u.is_closed, u.last_visit, u.previous_visit
from users u
         join user_role ur on ur.user_id = u.user_id
         join role r on r.role_id = ur.role_id
where r.name = 'USER' AND u.is_closed = 'false'
order by u.created_at desc
    LIMIT 5;


-- Если в БД не была указано каскадное удаление (добавление каскадного удаления для удаления профиля пользователя)

--ALTER TABLE user_role DROP CONSTRAINT user_role_user_id_fkey;
--ALTER TABLE user_role
--ADD CONSTRAINT user_role_user_id_fkey FOREIGN KEY (user_id)
--REFERENCES users(user_id) ON DELETE CASCADE;

--ALTER TABLE subscriptions DROP CONSTRAINT subscriptions_user_sub_id_fkey;
--ALTER TABLE subscriptions
--ADD CONSTRAINT subscriptions_user_sub_id_fkey FOREIGN KEY (user_sub_id)
--REFERENCES users(user_id) ON DELETE CASCADE;

--ALTER TABLE subscriptions DROP CONSTRAINT subscriptions_user_id_fkey;
--ALTER TABLE subscriptions
--ADD CONSTRAINT subscriptions_user_id_fkey FOREIGN KEY (user_id)
--REFERENCES users(user_id) ON DELETE CASCADE;

--ALTER TABLE likes DROP CONSTRAINT likes_user_id_fkey;
--ALTER TABLE likes
--ADD CONSTRAINT likes_user_id_fkey FOREIGN KEY (user_id)
--REFERENCES users(user_id) ON DELETE CASCADE;

--ALTER TABLE likes DROP CONSTRAINT likes_post_id_fkey;
--ALTER TABLE likes
--ADD CONSTRAINT likes_post_id_fkey FOREIGN KEY (post_id)
--REFERENCES post(post_id) ON DELETE CASCADE;

--ALTER TABLE post DROP CONSTRAINT post_user_id_fkey;
--ALTER TABLE post
--ADD CONSTRAINT post_user_id_fkey FOREIGN KEY (user_id)
--REFERENCES users(user_id) ON DELETE CASCADE;

--ALTER TABLE comment DROP CONSTRAINT comment_post_id_fkey;
--ALTER TABLE comment
--ADD CONSTRAINT comment_post_id_fkey FOREIGN KEY (post_id)
--REFERENCES post(post_id) ON DELETE CASCADE;

--ALTER TABLE comment DROP CONSTRAINT comment_user_id_fkey;
--ALTER TABLE comment
--ADD CONSTRAINT comment_user_id_fkey FOREIGN KEY (user_id)
--REFERENCES users(user_id) ON DELETE CASCADE;

--ALTER TABLE bugs DROP CONSTRAINT bugs_user_id_fkey;
--ALTER TABLE bugs
--ADD CONSTRAINT bugs_user_id_fkey FOREIGN KEY (user_id)
--REFERENCES users(user_id) ON DELETE CASCADE;