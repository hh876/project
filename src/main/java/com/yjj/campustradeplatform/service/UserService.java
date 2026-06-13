package com.yjj.campustradeplatform.service;

import com.yjj.campustradeplatform.entity.User;

public interface UserService {
    boolean register(User user);
    User login(String username, String password);
}