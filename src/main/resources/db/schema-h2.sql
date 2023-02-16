DROP TABLE IF EXISTS users;

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
