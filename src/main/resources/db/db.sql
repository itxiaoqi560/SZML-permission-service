CREATE DATABASE IF NOT EXISTS permission;

USE permission;

-- 角色表
CREATE TABLE IF NOT EXISTS tb_role
(
    id        BIGINT UNSIGNED PRIMARY KEY COMMENT '角色id', -- 1:超管 2:普通用户 3:管理员
    role_code VARCHAR(20) NOT NULL UNIQUE COMMENT '角色标识'-- super_admin/user/admin
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
    COMMENT ='角色表';

INSERT INTO tb_role(role_code)
VALUES ('super_admin'),
       ('user'),
       ('admin');

-- 用户-角色关系表
CREATE TABLE IF NOT EXISTS tb_user_role
(
    id      BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '用户角色id',
    user_id BIGINT UNSIGNED NOT NULL UNIQUE COMMENT '用户id',
    role_id BIGINT UNSIGNED NOT NULL COMMENT '角色id'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
    COMMENT ='用户角色表';

INSERT INTO tb_user_role(user_id, role_id)
VALUES (0, 2),
       (1, 3),
       (2, 1);

CREATE TABLE IF NOT EXISTS `undo_log`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20)   NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int(11)      NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
    COMMENT = '回滚日志';