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
}