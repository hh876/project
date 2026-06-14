package com.yjj.campustradeplatform.mapper;

import com.yjj.campustradeplatform.entity.Review;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReviewMapper {

    @Insert("INSERT INTO review (order_id, buyer_id, seller_id, goods_id, rating, comment) VALUES (#{orderId}, #{buyerId}, #{sellerId}, #{goodsId}, #{rating}, #{comment})")
    int insert(Review review);

    @Select("SELECT r.*, u.nickname as buyerNickname, g.title as goodsTitle FROM review r LEFT JOIN user u ON r.buyer_id = u.id LEFT JOIN goods g ON r.goods_id = g.id WHERE r.seller_id=#{sellerId} ORDER BY r.create_time DESC")
    List<Review> findBySellerId(Long sellerId);

    @Select("SELECT r.*, u.nickname as buyerNickname, g.title as goodsTitle FROM review r LEFT JOIN user u ON r.buyer_id = u.id LEFT JOIN goods g ON r.goods_id = g.id WHERE r.goods_id=#{goodsId} ORDER BY r.create_time DESC")
    List<Review> findByGoodsId(Long goodsId);

    @Select("SELECT r.*, u.nickname as buyerNickname, g.title as goodsTitle FROM review r LEFT JOIN user u ON r.buyer_id = u.id LEFT JOIN goods g ON r.goods_id = g.id WHERE r.buyer_id=#{buyerId} ORDER BY r.create_time DESC")
    List<Review> findByBuyerId(Long buyerId);

    @Select("SELECT AVG(rating) FROM review WHERE seller_id=#{sellerId}")
    Double getSellerRating(Long sellerId);

    @Select("SELECT COUNT(*) FROM review WHERE seller_id=#{sellerId}")
    int getSellerReviewCount(Long sellerId);
}
