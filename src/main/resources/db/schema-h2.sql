DROP TABLE IF EXISTS greeting;

CREATE TABLE greeting
(
    `id`         bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `message`    varchar(255) NOT NULL COMMENT '메시지',
    `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '생성 일자',
    PRIMARY KEY (`id`)
);