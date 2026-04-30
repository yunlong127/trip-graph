package com.travel.service;

import com.travel.entity.User;

public interface UserService {
    User register(User user);
    User login(String email, String password);
    User getUserById(Long id);
}