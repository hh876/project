package com.yjj.campustradeplatform.controller;

import com.yjj.campustradeplatform.entity.Favorite;
import com.yjj.campustradeplatform.entity.Goods;
import com.yjj.campustradeplatform.mapper.FavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @PostMapping("/add")
    public String add(@RequestParam Long userId, @RequestParam Long goodsId) {
        Favorite existing = favoriteMapper.findByUserIdAndGoodsId(userId, goodsId);
        if (existing != null) {
            return "已收藏";
        }
        
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setGoodsId(goodsId);
        favoriteMapper.insert(favorite);
        return "收藏成功";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long userId, @RequestParam Long goodsId) {
        int result = favoriteMapper.delete(userId, goodsId);
        return result > 0 ? "取消收藏成功" : "未找到收藏记录";
    }

    @GetMapping("/check")
    public Map<String, Object> check(@RequestParam Long userId, @RequestParam Long goodsId) {
        Map<String, Object> result = new HashMap<>();
        Favorite favorite = favoriteMapper.findByUserIdAndGoodsId(userId, goodsId);
        result.put("isFavorite", favorite != null);
        return result;
    }

    @GetMapping("/list")
    public List<Goods> list(@RequestParam Long userId) {
        return favoriteMapper.findGoodsByUserId(userId);
    }

    @GetMapping("/count")
    public Map<String, Object> count(@RequestParam Long goodsId) {
        Map<String, Object> result = new HashMap<>();
        result.put("count", favoriteMapper.countByGoodsId(goodsId));
        return result;
    }
}
