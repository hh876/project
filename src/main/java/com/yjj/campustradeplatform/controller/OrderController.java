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
            return "商品信息异常（卖家ID为空）";
        }
        if (goods.getUserId().equals(buyerId)) {
            return "不能购买自己发布的商品";
        }

        // 4. 创建订单（业务层加事务）
        String orderNo = orderService.createOrder(buyerId, goodsId);
        if (orderNo == null) {
            return "订单创建失败";
        }
        return "success";
    }

    @GetMapping("/list")
    public java.util.List<TradeOrder> getOrderList(@RequestParam Long userId) {
        return orderService.getOrderListByBuyerId(userId);
    }

    @GetMapping("/seller")
    public java.util.List<TradeOrder> getSellerOrders(@RequestParam Long userId) {
        return orderService.getOrderListBySellerId(userId);
    }

    @GetMapping("/detail")
    public TradeOrder getOrderDetail(@RequestParam String orderNo) {
        return orderService.getOrderByOrderNo(orderNo);
    }

    @PostMapping("/confirm")
    public String confirmOrder(@RequestParam String orderNo) {
        return orderService.confirmOrder(orderNo) ? "订单已完成" : "确认失败";
    }

    @PostMapping("/pay")
    public String payOrder(@RequestParam String orderNo) {
        return orderService.payOrder(orderNo) ? "支付成功" : "支付失败";
    }

    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam String orderNo) {
        return orderService.cancelOrder(orderNo) ? "订单已取消" : "取消失败";
    }
}