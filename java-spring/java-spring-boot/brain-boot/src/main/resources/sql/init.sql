CREATE TABLE `category`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`    varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
    `user_id` int(11) NOT NULL COMMENT '添加用户id',
    `ctime`   bigint(13) DEFAULT NULL COMMENT '添加时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `brain_question`
(
    `id`          int(11) NOT NULL  AUTO_INCREMENT COMMENT '主键',
    `category_id` int(11) DEFAULT NULL COMMENT '分类id',
    `content`     varchar(1024) COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
    `answer`      varchar(512) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '答案',
    `user_id`     int(11) DEFAULT NULL COMMENT '添加人',
    `ctime`       bigint(13) DEFAULT NULL COMMENT '添加时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;