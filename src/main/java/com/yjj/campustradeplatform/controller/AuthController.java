package com.yjj.campustradeplatform.controller;

import com.yjj.campustradeplatform.entity.User;
import com.yjj.campustradeplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        // 如果没有选择角色，默认设置为both
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("both");
        }
        return userService.register(user) ? "注册成功" : "用户名已存在";
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody User user) {
        User loginUser = userService.login(user.getUsername(), user.getPassword());
        Map<String, Object> result = new HashMap<>();
        
        if (loginUser != null) {
            result.put("success", true);
            result.put("userId", loginUser.getId());
            result.put("username", loginUser.getUsername());
            result.put("role", loginUser.getRole());
        } else {
            result.put("success", false);
            result.put("message", "账号或密码错误");
        }
        return result;
    }
}