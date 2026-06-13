package com.yjj.campustradeplatform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String avatar;
    private LocalDateTime createTime;
    private Integer status;
    // 用户角色：buyer(买家)、seller(卖家)、both(两者都是)
    private String role;
}