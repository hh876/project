package com.yjj.campustradeplatform.controller;

import com.yjj.campustradeplatform.entity.Review;
import com.yjj.campustradeplatform.entity.TradeOrder;
import com.yjj.campustradeplatform.mapper.ReviewMapper;
import com.yjj.campustradeplatform.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping("/add")
    public String add(@RequestParam Long orderId, @RequestParam Integer rating, @RequestParam(required = false) String comment) {
        TradeOrder order = orderMapper.selectByOrderNo(orderId.toString());
        if (order == null) {
            return "订单不存在";
        }
        if (order.getStatus() != 3) {
            return "只有已完成的订单才能评价";
        }

        Review review = new Review();
        review.setOrderId(orderId);
        review.setBuyerId(order.getBuyerId());
        review.setSellerId(order.getSellerId());
        review.setGoodsId(order.getGoodsId());
        review.setRating(rating);
        review.setComment(comment);
        reviewMapper.insert(review);
        return "评价成功";
    }

    @GetMapping("/seller/{sellerId}")
    public List<Review> getBySeller(@PathVariable Long sellerId) {
        return reviewMapper.findBySellerId(sellerId);
    }

    @GetMapping("/goods/{goodsId}")
    public List<Review> getByGoods(@PathVariable Long goodsId) {
        return reviewMapper.findByGoodsId(goodsId);
    }

    @GetMapping("/buyer/{buyerId}")
    public List<Review> getByBuyer(@PathVariable Long buyerId) {
        return reviewMapper.findByBuyerId(buyerId);
    }

    @GetMapping("/seller/rating/{sellerId}")
    public Map<String, Object> getSellerRating(@PathVariable Long sellerId) {
        Map<String, Object> result = new HashMap<>();
        result.put("rating", reviewMapper.getSellerRating(sellerId));
        result.put("count", reviewMapper.getSellerReviewCount(sellerId));
        return result;
    }
}
