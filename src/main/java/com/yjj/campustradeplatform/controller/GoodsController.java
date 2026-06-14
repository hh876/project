package com.yjj.campustradeplatform.controller;

import com.yjj.campustradeplatform.entity.Category;
import com.yjj.campustradeplatform.entity.Goods;
import com.yjj.campustradeplatform.mapper.CategoryMapper;
import com.yjj.campustradeplatform.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    
    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping("/list")
    public List<Goods> getOnSaleGoods() {
        return goodsService.getOnSaleList();
    }

    @GetMapping("/search")
    public List<Goods> searchGoods(@RequestParam String keyword) {
        return goodsService.searchGoods(keyword);
    }

    @GetMapping("/advanced-search")
    public List<Goods> advancedSearch(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        return goodsService.advancedSearch(keyword, categoryId, minPrice, maxPrice, sortBy, sortOrder);
    }

    @GetMapping("/my")
    public List<Goods> getMyGoods(@RequestParam Long userId) {
        return goodsService.getGoodsByUserId(userId);
    }

    @GetMapping("/detail/{id}")
    public Goods getGoodsDetail(@PathVariable Long id) {
        return goodsService.getGoodsDetail(id);
    }

    @GetMapping("/category")
    public List<Goods> getGoodsByCategory(@RequestParam Long categoryId) {
        return goodsService.getGoodsByCategory(categoryId);
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryMapper.selectAll();
    }

    @PostMapping("/publish")
    public String publishGoods(@RequestBody Goods goods) {
        return goodsService.publishGoods(goods) ? "发布成功" : "发布失败";
    }

    @PutMapping("/update")
    public String updateGoods(@RequestBody Goods goods) {
        return goodsService.updateGoods(goods) ? "修改成功" : "修改失败";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGoods(@PathVariable Long id) {
        return goodsService.deleteGoods(id) ? "删除成功" : "删除失败";
    }
}