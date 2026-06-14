-- 初始化测试数据
USE campus_trade;

-- 初始化用户（密码都是 bcrypt 加密后的 "123456"）
INSERT INTO `user` (username, password, nickname, role, status) VALUES 
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '管理员', 'both', 1),
('seller1', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '卖家小王', 'seller', 1),
('buyer1', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '买家小李', 'buyer', 1),
('user1', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '张三', 'both', 1),
('user2', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '李四', 'both', 1);

-- 初始化商品分类
INSERT INTO `category` (name, description) VALUES 
('数码产品', '手机、电脑、平板等数码设备'),
('学习用品', '书籍、文具、教材等学习相关物品'),
('生活用品', '衣物、鞋子、化妆品等日常用品'),
('体育器材', '运动装备、健身器材等'),
('其他', '其他闲置物品');

-- 初始化商品
INSERT INTO `goods` (user_id, title, description, price, status, category_id) VALUES 
(2, 'iPhone 13 Pro', '95新，无划痕，电池健康89%，全网通', 4500.00, 0, 1),
(2, 'MacBook Pro 14寸', '2021款，M1 Pro芯片，16G+512G', 8800.00, 0, 1),
(4, '高等数学教材', '同济大学第七版，上下册，有少量笔记', 35.00, 0, 2),
(4, '机械键盘', 'RGB背光，青轴，使用半年', 180.00, 0, 1),
(5, '羽毛球拍', '尤尼克斯NR-D11，9成新', 260.00, 0, 4),
(5, '牛仔裤', '李维斯501，32码，穿过几次', 150.00, 0, 3),
(2, 'iPad Air 5', '256G，Wi-Fi版，几乎全新', 3200.00, 0, 1),
(4, '台灯', 'LED护眼台灯，可调节亮度', 85.00, 0, 3);