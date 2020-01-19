CREATE TABLE `t_app_info` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `app_id` varchar(50) NOT NULL COMMENT '应用id',
  `app_name` varchar(255) NOT NULL COMMENT '应用名称',
  `log_path` varchar(255) DEFAULT NULL COMMENT '日志默认路径',
  `status` int(1) DEFAULT '1' COMMENT '状态（1正常，0删除）',
  `create_user` varchar(100) DEFAULT 'sulan' COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(100) DEFAULT 'sulan' COMMENT '更新人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_app_instance` (
  `id` varchar(100) NOT NULL COMMENT 'id',
  `app_id` varchar(255) DEFAULT NULL COMMENT '应用id',
  `app_instance_name` varchar(255) DEFAULT NULL COMMENT '应用实例名',
  `shell_host` varchar(255) DEFAULT NULL COMMENT '应用主机',
  `shell_port` int(11) DEFAULT NULL COMMENT 'shell端口',
  `shell_user` varchar(255) DEFAULT NULL COMMENT 'shell用户',
  `shell_pass` varchar(255) DEFAULT NULL COMMENT 'shell密码',
  `status` int(1) DEFAULT '1' COMMENT '1正常，0失效',
  `create_user` varchar(100) DEFAULT 'sulan',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_user` varchar(100) DEFAULT 'sulan',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;