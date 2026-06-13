package com.yjj.campustradeplatform.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TradeOrder {
    private Long id;
    private String orderNo;
    private Long buyerId;
    private Long sellerId;
    private Long goodsId;
    private BigDecimal price;
    private Integer status;
    private LocalDateTime payTime;
    private LocalDateTime createTime;
    private String goodsTitle;
}