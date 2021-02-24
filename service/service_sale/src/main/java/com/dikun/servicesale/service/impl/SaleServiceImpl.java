package com.dikun.servicesale.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dikun.servicesale.entity.Sale;
import com.dikun.servicesale.entity.vo.SaleQuery;
import com.dikun.servicesale.mapper.SaleMapper;
import com.dikun.servicesale.service.SaleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.http.client.utils.DateUtils;
import org.reflections.util.Utils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dikun
 * @since 2020-11-29
 */
@Service
public class SaleServiceImpl extends ServiceImpl<SaleMapper, Sale> implements SaleService {


    @Override
    public void salePageCondition(Page<Sale> pageSale, SaleQuery saleQuery) {
        QueryWrapper<Sale> saleQueryWrapper = new QueryWrapper<>();

         /**如果为空*/
         if (saleQuery==null){
             baseMapper.selectPage(pageSale,saleQueryWrapper);
         }

        String name = saleQuery.getName();
        Date begin = saleQuery.getBegin();
        Date end = saleQuery.getEnd();
        Integer saleNumber = saleQuery.getSaleNumber();

        if(!StringUtils.isEmpty(name)){
            saleQueryWrapper.like("name",name);
        }
        if (!DateUtils.formatDate(begin).isEmpty()){
            saleQueryWrapper.ge("date",begin);
        }
        if (!DateUtils.formatDate(end).isEmpty()){
            saleQueryWrapper.le("date",end);
        }

        baseMapper.selectPage(pageSale,saleQueryWrapper);




    }
}
