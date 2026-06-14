package com.yjj.campustradeplatform.mapper;

import com.yjj.campustradeplatform.entity.Goods;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface GoodsMapper {
    @Select("SELECT g.*, c.name as categoryName FROM goods g LEFT JOIN category c ON g.category_id = c.id WHERE g.status=0 AND g.user_id IS NOT NULL ORDER BY g.create_time DESC")
    List<Goods> selectOnSaleList();

    @Select("SELECT g.*, c.name as categoryName FROM goods g LEFT JOIN category c ON g.category_id = c.id WHERE g.status=0 AND g.user_id IS NOT NULL AND (g.title LIKE CONCAT('%',#{keyword},'%') OR g.description LIKE CONCAT('%',#{keyword},'%')) ORDER BY g.create_time DESC")
    List<Goods> searchGoods(String keyword);

    @Select("SELECT g.*, c.name as categoryName FROM goods g LEFT JOIN category c ON g.category_id = c.id WHERE g.id=#{id} AND g.user_id IS NOT NULL")
    Goods selectById(Long id);

    @Select("SELECT g.*, c.name as categoryName FROM goods g LEFT JOIN category c ON g.category_id = c.id WHERE g.user_id=#{userId} ORDER BY g.create_time DESC")
    List<Goods> selectByUserId(Long userId);

    @Select("SELECT g.*, c.name as categoryName FROM goods g LEFT JOIN category c ON g.category_id = c.id WHERE g.status=0 AND g.user_id IS NOT NULL AND g.category_id=#{categoryId} ORDER BY g.create_time DESC")
    List<Goods> selectByCategoryId(Long categoryId);

    @Insert("INSERT INTO goods(user_id,title,description,price,status,category_id,image_url) VALUES(#{userId},#{title},#{description},#{price},#{status},#{categoryId},#{imageUrl})")
    int insert(Goods goods);

    @Update("UPDATE goods SET status=#{status} WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") int status);

    @Update("UPDATE goods SET title=#{title}, description=#{description}, price=#{price}, category_id=#{categoryId}, image_url=#{imageUrl} WHERE id=#{id}")
    int update(Goods goods);

    @Delete("DELETE FROM goods WHERE id=#{id}")
    int deleteById(Long id);

    @SelectProvider(type = GoodsMapperProvider.class, method = "advancedSearch")
    List<Goods> advancedSearch(
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder);
}