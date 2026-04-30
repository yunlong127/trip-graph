CREATE DATABASE IF NOT EXISTS travel_planner;

USE travel_planner;

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- 旅行计划表
CREATE TABLE IF NOT EXISTS trips (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 行程安排表
CREATE TABLE IF NOT EXISTS itineraries (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    activity VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    time VARCHAR(50) NOT NULL,
    location VARCHAR(255) NOT NULL,
    description TEXT,
    trip_id BIGINT,
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);

-- 预订表
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(50) NOT NULL,
    booking_number VARCHAR(255) NOT NULL,
    booking_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    trip_id BIGINT,
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);