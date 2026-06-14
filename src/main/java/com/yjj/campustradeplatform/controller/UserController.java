package com.yjj.campustradeplatform.controller;

import com.yjj.campustradeplatform.entity.User;
import com.yjj.campustradeplatform.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/get")
    public User getUser(@RequestParam Long id) {
        return userMapper.selectById(id);
    }

    @PostMapping("/update")
    public String updateUser(
            @RequestParam Long id,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String avatar) {
        
        User user = userMapper.selectById(id);
        if (user == null) {
            return "用户不存在";
        }
        
        if (nickname != null && !nickname.isEmpty()) {
            user.setNickname(nickname);
        }
        if (phone != null) {
            user.setPhone(phone);
        }
        if (avatar != null && !avatar.isEmpty()) {
            user.setAvatar(avatar);
        }
        
        userMapper.update(user);
        return "success";
    }

    @PostMapping("/changePassword")
    public String changePassword(
            @RequestParam Long id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        
        User user = userMapper.selectById(id);
        if (user == null) {
            return "用户不存在";
        }
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "原密码错误";
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.update(user);
        return "success";
    }

    @GetMapping("/stats")
    public Map<String, Object> getUserStats(@RequestParam Long userId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("goodsCount", userMapper.countGoodsByUserId(userId));
        stats.put("orderCount", userMapper.countOrdersByUserId(userId));
        return stats;
    }
}
