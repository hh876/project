-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS campus_trade DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE campus_trade;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码（BCrypt加密）',
    `nickname` VARCHAR(50) COMMENT '昵称',
    `phone` VARCHAR(20) COMMENT '手机号',
    `avatar` VARCHAR(255) COMMENT '头像URL',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `status` INT DEFAULT 1 COMMENT '状态：0禁用，1启用',
    `role` VARCHAR(20) DEFAULT 'both' COMMENT '角色：buyer(买家)、seller(卖家)、both(两者都是)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 商品表
CREATE TABLE IF NOT EXISTS `goods` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '商品ID',
    `user_id` BIGINT NOT NULL COMMENT '卖家ID',
    `title` VARCHAR(100) NOT NULL COMMENT '商品标题',
    `description` TEXT COMMENT '商品描述',
    `price` DECIMAL(10,2) NOT NULL COMMENT '商品价格',
    `status` INT DEFAULT 0 COMMENT '状态：0在售，1已售出',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 订单表
CREATE TABLE IF NOT EXISTS `trade_order` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID',
    `order_no` VARCHAR(64) NOT NULL UNIQUE COMMENT '订单编号',
    `buyer_id` BIGINT NOT NULL COMMENT '买家ID',
    `seller_id` BIGINT NOT NULL COMMENT '卖家ID',
    `goods_id` BIGINT NOT NULL COMMENT '商品ID',
    `price` DECIMAL(10,2) NOT NULL COMMENT '订单金额',
    `status` INT DEFAULT 0 COMMENT '状态：0待支付，1已支付，2已取消，3已完成',
    `pay_time` DATETIME COMMENT '支付时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (`buyer_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`seller_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`goods_id`) REFERENCES `goods`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';