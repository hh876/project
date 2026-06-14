package com.yjj.campustradeplatform.service;

import com.yjj.campustradeplatform.entity.Goods;
import java.util.List;

public interface GoodsService {
    List<Goods> getOnSaleList();
    Goods getGoodsDetail(Long id);
    boolean publishGoods(Goods goods);
    List<Goods> searchGoods(String keyword);
    List<Goods> getGoodsByUserId(Long userId);
    List<Goods> getGoodsByCategory(Long categoryId);
    List<Goods> advancedSearch(String keyword, Long categoryId, Double minPrice, Double maxPrice, String sortBy, String sortOrder);
    boolean updateGoods(Goods goods);
    boolean deleteGoods(Long id);
}