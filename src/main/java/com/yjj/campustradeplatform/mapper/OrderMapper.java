package com.yjj.campustradeplatform.mapper;

import com.yjj.campustradeplatform.entity.TradeOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO trade_order(order_no, buyer_id, seller_id, goods_id, price, status) " +
            "VALUES(#{orderNo}, #{buyerId}, #{sellerId}, #{goodsId}, #{price}, #{status})")
    int insert(TradeOrder order);

    @Select("SELECT * FROM trade_order WHERE order_no = #{orderNo}")
    TradeOrder selectByOrderNo(String orderNo);

    @Select("SELECT * FROM trade_order WHERE id = #{id}")
    TradeOrder selectById(Long id);

    @Select("SELECT o.*, g.title as goods_title FROM trade_order o LEFT JOIN goods g ON o.goods_id = g.id WHERE o.buyer_id = #{buyerId} ORDER BY o.create_time DESC")
    java.util.List<TradeOrder> selectByBuyerId(Long buyerId);

    @Select("SELECT o.*, g.title as goods_title FROM trade_order o LEFT JOIN goods g ON o.goods_id = g.id WHERE o.seller_id = #{sellerId} ORDER BY o.create_time DESC")
    java.util.List<TradeOrder> selectBySellerId(Long sellerId);

    @Update("UPDATE trade_order SET status=#{status} WHERE order_no=#{orderNo}")
    int updateStatus(@Param("orderNo") String orderNo, @Param("status") int status);

    @Update("UPDATE trade_order SET status=#{status} WHERE id=#{id}")
    int updateStatusById(@Param("id") Long id, @Param("status") int status);
}