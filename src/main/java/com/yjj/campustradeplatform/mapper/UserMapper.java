package com.yjj.campustradeplatform.mapper;

import com.yjj.campustradeplatform.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(Long id);

    @Insert("INSERT INTO user(username, password, nickname, phone, avatar, status, role) " +
            "VALUES(#{username}, #{password}, #{nickname}, #{phone}, #{avatar}, #{status}, #{role})")
    int insert(User user);

    @Update("UPDATE user SET nickname=#{nickname}, phone=#{phone}, avatar=#{avatar} WHERE id=#{id}")
    int update(User user);

    @Select("SELECT COUNT(*) FROM goods WHERE user_id=#{userId} AND status=0")
    int countGoodsByUserId(Long userId);

    @Select("SELECT COUNT(*) FROM trade_order WHERE buyer_id=#{userId} OR seller_id=#{userId}")
    int countOrdersByUserId(Long userId);
}