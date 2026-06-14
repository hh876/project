package com.yjj.campustradeplatform.mapper;

import com.yjj.campustradeplatform.entity.Favorite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    @Insert("INSERT INTO favorite (user_id, goods_id) VALUES (#{userId}, #{goodsId})")
    int insert(Favorite favorite);

    @Delete("DELETE FROM favorite WHERE user_id=#{userId} AND goods_id=#{goodsId}")
    int delete(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    @Select("SELECT * FROM favorite WHERE user_id=#{userId} AND goods_id=#{goodsId}")
    Favorite findByUserIdAndGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    @Select("SELECT g.*, c.name as categoryName FROM goods g LEFT JOIN category c ON g.category_id = c.id WHERE g.id IN (SELECT goods_id FROM favorite WHERE user_id=#{userId}) AND g.user_id IS NOT NULL ORDER BY g.create_time DESC")
    List<com.yjj.campustradeplatform.entity.Goods> findGoodsByUserId(Long userId);

    @Select("SELECT COUNT(*) FROM favorite WHERE goods_id=#{goodsId}")
    int countByGoodsId(Long goodsId);
}
