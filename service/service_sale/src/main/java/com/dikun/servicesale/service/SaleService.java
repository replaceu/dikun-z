package com.dikun.servicesale.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dikun.servicesale.entity.Sale;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dikun.servicesale.entity.vo.SaleQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dikun
 * @since 2020-11-29
 */


public interface SaleService extends IService<Sale> {


    void salePageCondition(Page<Sale> pageSale, SaleQuery saleQuery);
}
