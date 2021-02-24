package com.dikun.servicestock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dikun.servicestock.entity.Goods;
import com.dikun.servicestock.entity.vo.GoodsQuery;
import com.dikun.servicestock.mapper.GoodsMapper;
import com.dikun.servicestock.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dikun
 * @since 2020-11-14
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {


    @Override
    public void pageQuery(Page<Goods> goodsPage, GoodsQuery goodsQuery) {

        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("sort");

        if (goodsQuery==null){
            baseMapper.selectPage(goodsPage,queryWrapper);
            return;

        }

        String name = goodsQuery.getName();
        Integer sumBegin = goodsQuery.getSumBegin();
        Integer sumEnd = goodsQuery.getSumEnd();
        String begin = goodsQuery.getBegin();
        String end = goodsQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        if (sumEnd!=null){
            queryWrapper.le("sum",sumEnd);
        }
        if (sumBegin!=null){
            queryWrapper.ge("sum",sumBegin);
        }

        baseMapper.selectPage(goodsPage,queryWrapper);


    }
}


