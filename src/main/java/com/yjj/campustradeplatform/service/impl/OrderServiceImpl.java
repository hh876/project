package com.yjj.campustradeplatform.service.impl;

import com.yjj.campustradeplatform.entity.Goods;
import com.yjj.campustradeplatform.entity.TradeOrder;
import com.yjj.campustradeplatform.mapper.GoodsMapper;
import com.yjj.campustradeplatform.mapper.OrderMapper;
import com.yjj.campustradeplatform.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    @Transactional
    public String createOrder(Long buyerId, Long goodsId) {
        // 1. 查询商品，先判空，再操作
        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null) {
            return null;
        }

        // 2. 商品状态校验
        if (goods.getStatus() != 0) {
            return null;
        }

        // 3. 校验卖家ID是否存在，以及不能购买自己的商品
        if (goods.getUserId() == null) {
            return null;
        }
        if (goods.getUserId().equals(buyerId)) {
            return null;
        }

        // 4. 校验通过，创建订单
        TradeOrder order = new TradeOrder();
        order.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        order.setBuyerId(buyerId);
        order.setSellerId(goods.getUserId());
        order.setGoodsId(goodsId);
        order.setPrice(goods.getPrice());
        order.setStatus(0);

        return orderMapper.insert(order) > 0 ? order.getOrderNo() : null;
    }

    @Override
    public boolean payOrder(String orderNo) {
        TradeOrder order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || order.getStatus() != 0) {
            return false;
        }
        orderMapper.updateStatus(orderNo, 1);
        return true;
    }

    @Override
    @Transactional
    public boolean confirmOrder(String orderNo) {
        TradeOrder order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || order.getStatus() != 1) {
            return false;
        }
        orderMapper.updateStatus(orderNo, 3);
        goodsMapper.updateStatus(order.getGoodsId(), 1);
        return true;
    }

    @Override
    public List<TradeOrder> getOrderListByBuyerId(Long buyerId) {
        return orderMapper.selectByBuyerId(buyerId);
    }

    @Override
    public List<TradeOrder> getOrderListBySellerId(Long sellerId) {
        return orderMapper.selectBySellerId(sellerId);
    }

    @Override
    public TradeOrder getOrderByOrderNo(String orderNo) {
        return orderMapper.selectByOrderNo(orderNo);
    }

    @Override
    @Transactional
    public boolean cancelOrder(String orderNo) {
        TradeOrder order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || order.getStatus() != 0) {
            return false;
        }
        orderMapper.updateStatus(orderNo, 2);
        return true;
    }
}