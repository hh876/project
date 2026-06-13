package com.yjj.campustradeplatform.service;

import com.yjj.campustradeplatform.entity.TradeOrder;
import java.util.List;

public interface OrderService {
    String createOrder(Long buyerId, Long goodsId);
    boolean payOrder(String orderNo);
    boolean confirmOrder(String orderNo);
    List<TradeOrder> getOrderListByBuyerId(Long buyerId);
    List<TradeOrder> getOrderListBySellerId(Long sellerId);
    boolean confirmOrder(Long orderId);
}