-- Insert location data
INSERT INTO location (name, city, country) VALUES
('Beijing', 'Beijing', 'China'),
('Shanghai', 'Shanghai', 'China'),
('Guangzhou', 'Guangzhou', 'China'),
('Shenzhen', 'Shenzhen', 'China'),
('Hangzhou', 'Hangzhou', 'China'),
('Chengdu', 'Chengdu', 'China'),
('Xian', 'Xian', 'China'),
('Tokyo', 'Tokyo', 'Japan'),
('Osaka', 'Osaka', 'Japan'),
('Seoul', 'Seoul', 'Korea'),
('New York', 'New York', 'USA'),
('Los Angeles', 'Los Angeles', 'USA');

-- Insert facility data
INSERT INTO facility (name, type, description, location_id) VALUES
('Forbidden City', 'attraction', 'Imperial palace of Ming and Qing dynasties', 1),
('Great Wall', 'attraction', 'Ancient Chinese military defense project', 1),
('Bund', 'attraction', 'Shanghai landmark buildings', 2),
('Oriental Pearl Tower', 'attraction', 'Shanghai landmark', 2),
('Canton Tower', 'attraction', 'Guangzhou Tower', 3),
('Window of the World', 'attraction', 'Shenzhen theme park', 4),
('West Lake', 'attraction', 'Famous scenic spot in Hangzhou', 5),
('Panda Base', 'attraction', 'Chengdu Research Base of Giant Panda Breeding', 6),
('Terracotta Warriors', 'attraction', 'Qin Shi Huang Mausoleum Terracotta Warriors', 7),
('Tokyo Tower', 'attraction', 'Tokyo landmark', 8),
('Osaka Castle', 'attraction', 'Osaka landmark', 9),
('Myeong-dong', 'shopping', 'Famous shopping district in Seoul', 10),
('Statue of Liberty', 'attraction', 'New York landmark', 11),
('Hollywood', 'attraction', 'Los Angeles film industry center', 12);

-- Insert route data
INSERT INTO route (distance, duration, price, transportation, visa_required, start_location_id, end_location_id) VALUES
-- Domestic routes
(1318, 2.5, 553, 'plane', false, 1, 2),
(1215, 5, 309, 'train', false, 1, 2),
(2100, 3, 860, 'plane', false, 1, 3),
(2372, 10, 450, 'train', false, 1, 3),
(2635, 3.5, 980, 'plane', false, 1, 4),
(2400, 12, 520, 'train', false, 1, 4),
(1150, 2, 480, 'plane', false, 2, 5),
(1500, 4, 280, 'train', false, 2, 5),
(1600, 2.5, 620, 'plane', false, 3, 4),
(1200, 3, 200, 'train', false, 3, 4),
(1500, 2.5, 650, 'plane', false, 1, 6),
(2000, 8, 380, 'train', false, 1, 6),
(1200, 2, 520, 'plane', false, 1, 7),
(1800, 6, 320, 'train', false, 1, 7),

-- International routes
(2500, 4, 1500, 'plane', true, 1, 8),
(3000, 5, 1800, 'plane', true, 1, 9),
(950, 2, 800, 'plane', true, 1, 10),
(11000, 13, 6000, 'plane', true, 1, 11),
(12000, 14, 6500, 'plane', true, 1, 12),
(1800, 3, 1200, 'plane', true, 8, 9),
(850, 2, 700, 'plane', true, 9, 10),
(10000, 12, 5500, 'plane', true, 8, 11),
(9000, 11, 5000, 'plane', true, 10, 11),
(4000, 5, 2500, 'plane', true, 11, 12);
