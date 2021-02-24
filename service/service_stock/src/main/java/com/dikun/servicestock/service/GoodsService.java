package com.dikun.servicestock.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dikun.servicestock.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dikun.servicestock.entity.vo.GoodsQuery;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dikun
 * @since 2020-11-14
 */
public interface GoodsService extends IService<Goods> {

     void pageQuery(Page<Goods> goodsPage, GoodsQuery goodsQuery);

}
