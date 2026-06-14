package com.yjj.campustradeplatform.mapper;

import com.yjj.campustradeplatform.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("SELECT * FROM category ORDER BY id")
    List<Category> selectAll();

    @Select("SELECT * FROM category WHERE id=#{id}")
    Category selectById(Long id);

    @Select("SELECT name FROM category WHERE id=#{id}")
    String selectNameById(Long id);
}