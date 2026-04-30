USE travel_planner;

INSERT INTO user (username, email, password, created_at, updated_at) VALUES
('张三', 'zhangsan@example.com', 'password123', NOW(), NOW()),
('李四', 'lisi@example.com', 'password456', NOW(), NOW()),
('王五', 'wangwu@example.com', 'password789', NOW(), NOW());

INSERT INTO trip (user_id, title, destination, start_date, end_date, status, created_at, updated_at) VALUES
(1, '北京之旅', '北京', '2024-05-01', '2024-05-05', 'planning', NOW(), NOW()),
(1, '上海出差', '上海', '2024-06-10', '2024-06-12', 'planning', NOW(), NOW()),
(2, '三亚度假', '三亚', '2024-07-15', '2024-07-20', 'planning', NOW(), NOW());

INSERT INTO itinerary (trip_id, day_number, date, title, description, location, created_at, updated_at) VALUES
(1, 1, '2024-05-01', '抵达北京', '乘坐高铁抵达北京，入住酒店', '北京西站', NOW(), NOW()),
(1, 2, '2024-05-02', '故宫游览', '参观故宫博物院，感受皇家文化', '故宫', NOW(), NOW()),
(1, 3, '2024-05-03', '长城之旅', '游览八达岭长城', '八达岭长城', NOW(), NOW()),
(1, 4, '2024-05-04', '颐和园', '游览颐和园，欣赏皇家园林', '颐和园', NOW(), NOW()),
(1, 5, '2024-05-05', '返程', '乘坐高铁返回', '北京西站', NOW(), NOW()),
(2, 1, '2024-06-10', '商务会议', '参加上午的商务会议', '上海国际会议中心', NOW(), NOW()),
(2, 2, '2024-06-11', '客户拜访', '拜访重要客户', '浦东新区', NOW(), NOW()),
(3, 1, '2024-07-15', '抵达三亚', '乘坐飞机抵达三亚，入住酒店', '三亚凤凰国际机场', NOW(), NOW()),
(3, 2, '2024-07-16', '海滩度假', '在亚龙湾海滩享受阳光沙滩', '亚龙湾', NOW(), NOW()),
(3, 3, '2024-07-17', '潜水体验', '体验潜水活动', '蜈支洲岛', NOW(), NOW()),
(3, 4, '2024-07-18', '南山寺', '参观南山寺，欣赏海上观音', '南山寺', NOW(), NOW()),
(3, 5, '2024-07-19', '免税购物', '在免税店购物', '海棠湾免税店', NOW(), NOW()),
(3, 6, '2024-07-20', '返程', '乘坐飞机返回', '三亚凤凰国际机场', NOW(), NOW());

INSERT INTO booking (trip_id, type,provider, booking_reference, status, price, booking_date, created_at, updated_at) VALUES
(1, 'hotel', '北京王府井希尔顿酒店', 'HTL001', 'confirmed', 1200.00, '2024-05-01', NOW(), NOW()),
(1, 'transport', '中国铁路', 'G123456', 'confirmed', 553.00, '2024-05-01', NOW(), NOW()),
(2, 'hotel', '上海外滩茂悦大酒店', 'HTL002', 'confirmed', 800.00, '2024-06-10', NOW(), NOW()),
(2, 'transport', '中国东方航空', 'MU1234', 'confirmed', 1200.00, '2024-06-10', NOW(), NOW()),
(3, 'hotel', '三亚亚特兰蒂斯酒店', 'HTL003', 'confirmed', 2500.00, '2024-07-15', NOW(), NOW()),
(3, 'transport', '中国南方航空', 'CZ5678', 'confirmed', 1500.00, '2024-07-15', NOW(), NOW()),
(3, 'activity', '蜈支洲岛潜水中心', 'ACT001', 'confirmed', 680.00, '2024-07-17', NOW(), NOW());
