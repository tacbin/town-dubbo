
-- ----------------------------
-- Table structure for wx_user
-- ----------------------------
DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user`  (
  `openid` varchar(50)  NOT NULL COMMENT '微信openid',
  `phone` char(11)  NULL DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(50)  NULL DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '性别(0-未知、1-男、2-女)',
  `city` varchar(20)  NULL DEFAULT NULL COMMENT '城市',
  `province` varchar(20)  NULL DEFAULT NULL COMMENT '省份',
  `headimgurl` varchar(255)  NULL DEFAULT NULL COMMENT '头像',
  `subscribe_time` datetime(0) NULL DEFAULT NULL COMMENT '订阅时间',
  `subscribe` tinyint(3) UNSIGNED NULL DEFAULT 1 COMMENT '是否关注',
  `unionid` varchar(50)  NULL DEFAULT NULL COMMENT 'unionid',
  `remark` varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  `tagid_list` json NULL COMMENT '标签ID列表',
  `subscribe_scene` varchar(50)  NULL DEFAULT NULL COMMENT '关注场景',
  `qr_scene_str` varchar(64)  NULL DEFAULT NULL COMMENT '扫码场景值',
  PRIMARY KEY (`openid`) USING BTREE,
  INDEX `idx_unionid`(`unionid`) USING BTREE COMMENT 'unionid'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = '用户表';