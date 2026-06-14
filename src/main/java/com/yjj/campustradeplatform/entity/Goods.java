package com.yjj.campustradeplatform.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Goods {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer status;
    private Long categoryId;
    private String categoryName;
    private String imageUrl;
    private LocalDateTime createTime;
}