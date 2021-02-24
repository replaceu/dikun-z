package com.dikun.servicestock.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dikun.servicestock.entity.Goods;
import com.dikun.servicestock.entity.vo.GoodsQuery;
import com.dikun.servicestock.service.GoodsService;
import commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dikun
 * @since 2020-11-14
 */
@Api(description = "商品信息")
@RestController
@RequestMapping("/servicestock/goods")
@CrossOrigin

public class GoodsController {

    @Autowired
    private GoodsService goodsService;

//    //1.查询商品库存信息
//
//    @GetMapping("findAll")
//    public List<Goods> findAllGoods(){
//        return goodsService.list(null);
//    }
//
//    //2.库存信息逻辑删除
//    @ApiOperation(value = "依据ID删除商品库存信息")
//    @DeleteMapping("{id}")
//    public boolean removeById(@ApiParam(name = "id",value = "商品ID",required = true) @PathVariable Long id){
//        return goodsService.removeById(id);
//    }

    //1.1查询所有商品库存列表
    @ApiOperation(value = "查询所有商品库存列表")
    @GetMapping
    public R list(){
        List<Goods> goodsList = goodsService.list(null);
        return R.ok().data("items",goodsList);
    }

    //1.2依据ID逻辑删除商品库存信息
    @ApiOperation(value = "依据ID删除商品库存信息")
    @DeleteMapping("{id}")
    public R removeById(@ApiParam(name = "id",value = "商品ID",required = true) @PathVariable Long id ){
        boolean removeById = goodsService.removeById(id);
        return R.ok();
    }

    //3.分页查询方法

//    @ApiOperation(value = "分页商品信库存信息列表")
//    @GetMapping("pageGoods/{current}/{limit}")
//
//    public R goodsPageList(@ApiParam(name = "current", value = "当前页码", required = true)
//                               @PathVariable Long current,
//                           @ApiParam(name = "limit", value = "每页记录数", required = true)
//                               @PathVariable Long limit){
//        Page<Goods> pageGoods = new Page<>(current,limit);
//
//        goodsService.page(pageGoods,null);
//
//        long pageGoodsTotal = pageGoods.getTotal();//总记录数
//        List<Goods> pageGoodsRecords = pageGoods.getRecords();//数据list集合
//
//        return R.ok().data("total",pageGoodsTotal).data("rows",pageGoodsRecords);
//    }

    //4.分页条件查询

    @ApiOperation(value = "条件分页商品信库存信息列表")
    @PostMapping ("goodsPageConditionList/{current}/{limit}")

    public R goodsPageConditionList(@ApiParam(name = "current", value = "当前页码", required = true)
                                    @PathVariable Long current,
                                    @ApiParam(name = "limit", value = "每页记录数", required = true)
                                    @PathVariable Long limit,
                                    @ApiParam(name = "goodsQuery",value = "查询对象",required = false)
                                               @RequestBody GoodsQuery goodsQuery)
    {
        Page<Goods> pageGoods = new Page<>(current,limit);

        goodsService.pageQuery(pageGoods,goodsQuery);
        List<Goods> records = pageGoods.getRecords();
        long total = pageGoods.getTotal();

        return R.ok().data("total",total).data("rows",records);
    }

    }






