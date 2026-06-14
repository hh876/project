package com.yjj.campustradeplatform.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {
    private Long id;
    private Long orderId;
    private Long buyerId;
    private Long sellerId;
    private Long goodsId;
    private Integer rating;
    private String comment;
    private LocalDateTime createTime;
    private String buyerNickname;
    private String goodsTitle;
}
