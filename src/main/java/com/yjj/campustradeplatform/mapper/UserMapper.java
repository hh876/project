package com.yjj.campustradeplatform.mapper;

import com.yjj.campustradeplatform.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(Long id);

    @Insert("INSERT INTO user(username, password, nickname, phone, avatar, status, role) " +
            "VALUES(#{username}, #{password}, #{nickname}, #{phone}, #{avatar}, #{status}, #{role})")
    int insert(User user);
}