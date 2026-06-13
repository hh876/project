package com.yjj.campustradeplatform.mapper;

import com.yjj.campustradeplatform.entity.Goods;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface GoodsMapper {
    @Select("SELECT * FROM goods WHERE status=0 AND user_id IS NOT NULL ORDER BY create_time DESC")
    List<Goods> selectOnSaleList();

    @Select("SELECT * FROM goods WHERE status=0 AND user_id IS NOT NULL AND (title LIKE CONCAT('%',#{keyword},'%') OR description LIKE CONCAT('%',#{keyword},'%')) ORDER BY create_time DESC")
    List<Goods> searchGoods(String keyword);

    @Select("SELECT * FROM goods WHERE id=#{id}")
    Goods selectById(Long id);

    @Select("SELECT * FROM goods WHERE user_id=#{userId} ORDER BY create_time DESC")
    List<Goods> selectByUserId(Long userId);

    @Insert("INSERT INTO goods(user_id,title,description,price,status) VALUES(#{userId},#{title},#{description},#{price},#{status})")
    int insert(Goods goods);

    @Update("UPDATE goods SET status=#{status} WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") int status);
}