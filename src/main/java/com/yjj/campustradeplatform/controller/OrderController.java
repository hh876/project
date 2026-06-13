package com.yjj.campustradeplatform.controller;

import com.yjj.campustradeplatform.entity.Goods;
import com.yjj.campustradeplatform.entity.User;
import com.yjj.campustradeplatform.mapper.GoodsMapper;
import com.yjj.campustradeplatform.mapper.UserMapper;
import com.yjj.campustradeplatform.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import com.yjj.campustradeplatform.entity.TradeOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @PostMapping("/create")
    public String createOrder(
            @RequestParam Long goodsId,
            @RequestParam Long buyerId) {

        // 1. 校验用户是否存在
        User buyer = userMapper.selectById(buyerId);
        if (buyer == null) {
            return "用户不存在，请重新登录";
        }

        // 2. 校验商品是否存在、是否在售
        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null) {
            return "商品不存在";
        }
        if (goods.getStatus() != 0) {
            return "商品已售出，无法购买";
        }

        // 3. 校验不能购买自己的商品
        if (goods.getUserId() == null) {
            return "商品信息异常";
        }
        if (goods.getUserId().equals(buyerId)) {
            return "不能购买自己发布的商品";
        }

        // 4. 创建订单（业务层加事务）
        orderService.createOrder(buyerId, goodsId);
        return "success";
    }

    @GetMapping("/list")
    public java.util.List<TradeOrder> getOrderList(@RequestParam Long userId) {
        return orderService.getOrderListByBuyerId(userId);
    }

    @GetMapping("/sales")
    public java.util.List<TradeOrder> getSalesList(@RequestParam Long userId) {
        return orderService.getOrderListBySellerId(userId);
    }

    @PostMapping("/confirm")
    public String confirmOrder(@RequestParam Long orderId) {
        return orderService.confirmOrder(orderId) ? "success" : "确认失败";
    }
}