package com.yjj.campustradeplatform.controller;

import com.yjj.campustradeplatform.entity.Goods;
import com.yjj.campustradeplatform.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/list")
    public List<Goods> getOnSaleGoods() {
        return goodsService.getOnSaleList();
    }

    @GetMapping("/search")
    public List<Goods> searchGoods(@RequestParam String keyword) {
        return goodsService.searchGoods(keyword);
    }

    @GetMapping("/my")
    public List<Goods> getMyGoods(@RequestParam Long userId) {
        return goodsService.getGoodsByUserId(userId);
    }

    @GetMapping("/detail/{id}")
    public Goods getGoodsDetail(@PathVariable Long id) {
        return goodsService.getGoodsDetail(id);
    }

    @PostMapping("/publish")
    public String publishGoods(@RequestBody Goods goods) {
        return goodsService.publishGoods(goods) ? "发布成功" : "发布失败";
    }
}