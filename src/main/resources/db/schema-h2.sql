DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS sessions;

create table users
(
    `id`                bigint auto_increment comment '사용자 ID',
    `email`             varchar(128)                       not null comment '사용자 이메일',
    `nickname`          varchar(64)                        not null comment '닉네임',
    `profile_image_url` varchar(256)                       null comment '프로필 사진 url',
    `oauth_platform`    enum ('KAKAO', 'APPLE')            not null comment '사용자 로그인한 sns 플랫폼',
    `role`              enum('USER', 'ADIMIN')             not null default 'USER' comment '사용자 권한',
    `created_at`        datetime                           not null default CURRENT_TIMESTAMP comment '생성한 시간',
    `updated_at`        datetime                           not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '수정한 시간',
    `deleted_at`        datetime                           null comment '삭제한 시간',
    primary key (`id`),
    constraint users_pk unique (`email`),
    index users_email_index(`email`)
)
comment '사용자 정보 관리 테이블';

create table sessions
(
    `id`            bigint auto_increment comment 'id',
    `user_id`       bigint                             not null comment '사용자 ID',
    `refresh_token` varchar(512)                       not null comment '사용자 refresh_token',
    `issues_at`     datetime                           not null comment 'refresh_token 발행한 시간',
    `expires_at`    datetime                           not null comment 'refresh_token 만료 시간',
    `created_at`    datetime                           not null default CURRENT_TIMESTAMP comment '생성한 시간',
    `updated_at`    datetime                           not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '수정한 시간',
    `deleted_at`    datetime                           null comment '삭제한 시간',
    primary key (`id`),
    constraint sessions_users_null_fk foreign key (`user_id`) references users (`id`),
    index sessions_refresh_token_index(`refresh_token`),
    index sessions_user_id_index(`user_id`)
)
    comment '사용자 refresh_token 관리 테이블';

