package com.xiaotu.advertiser.dictionary.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.dictionary.service.GoodsService;

@RestController
@RequestMapping("goods")
public class GoodsController
{
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/saveGoodsAndWord")
    public Object saveGoodsAndWord()
    {
        goodsService.saveGoodsAndWord(new File("D://品类.csv"), new File("D://权重.csv"));
        return null;
    }

    /**
     * 获取品类列表
     *
     * @return
     * @author xuchangjian 2017年7月6日下午2:59:43
     */
    @RequestMapping("/queryGoodsList")
    public Object queryGoodsList()
    {
        return this.goodsService.queryGoodsList();
    }
}