package com.yjj.campustradeplatform.mapper;

import org.apache.ibatis.jdbc.SQL;

public class GoodsMapperProvider {

    public String advancedSearch(String keyword, Long categoryId, Double minPrice, Double maxPrice, String sortBy, String sortOrder) {
        SQL sql = new SQL()
            .SELECT("g.*, c.name as categoryName")
            .FROM("goods g")
            .LEFT_OUTER_JOIN("category c ON g.category_id = c.id")
            .WHERE("g.status=0 AND g.user_id IS NOT NULL");

        if (keyword != null && !keyword.isEmpty()) {
            sql.AND().WHERE("(g.title LIKE CONCAT('%',#{keyword},'%') OR g.description LIKE CONCAT('%',#{keyword},'%'))");
        }

        if (categoryId != null && categoryId > 0) {
            sql.AND().WHERE("g.category_id=#{categoryId}");
        }

        if (minPrice != null) {
            sql.AND().WHERE("g.price >= #{minPrice}");
        }

        if (maxPrice != null) {
            sql.AND().WHERE("g.price <= #{maxPrice}");
        }

        String orderColumn = "g.create_time";
        if ("price".equals(sortBy)) {
            orderColumn = "g.price";
        } else if ("title".equals(sortBy)) {
            orderColumn = "g.title";
        }

        String orderDirection = "DESC";
        if ("asc".equalsIgnoreCase(sortOrder)) {
            orderDirection = "ASC";
        }

        sql.ORDER_BY(orderColumn + " " + orderDirection);

        return sql.toString();
    }
}
