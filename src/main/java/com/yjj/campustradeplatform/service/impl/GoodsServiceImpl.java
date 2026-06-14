package com.yjj.campustradeplatform.service.impl;

import com.yjj.campustradeplatform.entity.Goods;
import com.yjj.campustradeplatform.mapper.GoodsMapper;
import com.yjj.campustradeplatform.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> getOnSaleList() {
        return goodsMapper.selectOnSaleList();
    }

    @Override
    public Goods getGoodsDetail(Long id) {
        return goodsMapper.selectById(id);
    }

    @Override
    public boolean publishGoods(Goods goods) {
        goods.setStatus(0);
        return goodsMapper.insert(goods) > 0;
    }

    @Override
    public List<Goods> searchGoods(String keyword) {
        return goodsMapper.searchGoods(keyword);
    }

    @Override
    public List<Goods> getGoodsByUserId(Long userId) {
        return goodsMapper.selectByUserId(userId);
    }

    @Override
    public List<Goods> getGoodsByCategory(Long categoryId) {
        return goodsMapper.selectByCategoryId(categoryId);
    }

    @Override
    public List<Goods> advancedSearch(String keyword, Long categoryId, Double minPrice, Double maxPrice, String sortBy, String sortOrder) {
        return goodsMapper.advancedSearch(keyword, categoryId, minPrice, maxPrice, sortBy, sortOrder);
    }

    @Override
    public boolean updateGoods(Goods goods) {
        return goodsMapper.update(goods) > 0;
    }

    @Override
    public boolean deleteGoods(Long id) {
        return goodsMapper.deleteById(id) > 0;
    }
}