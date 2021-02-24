package com.dikun.servicesale.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dikun.servicesale.entity.Sale;
import com.dikun.servicesale.entity.vo.SaleQuery;
import com.dikun.servicesale.service.SaleService;
import commonutils.R;
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
 * @since 2020-11-29
 */
@RestController
@RequestMapping("/servicesale/sale")
public class SaleController {

    /**
     * 其实在启动spring IoC时，容器自动装载了一个AutowiredAnnotationBeanPostProcessor后置处理器，
     * 当容器扫描到@Autowied、@Resource(是CommonAnnotationBeanPostProcessor后置处理器处理的)
     * 或@Inject时，就会在IoC容器自动查找需要的bean，并装配给该对象的属性*/

    @Autowired
    private SaleService saleService;

    //销售列表
    @ApiOperation("销售列表")
    public R list(){
        List<Sale> saleList = saleService.list(null);
        return R.ok().data("items",saleList);
    }

    //销售条件分页查询
    @ApiOperation("销售条件分页查询")
    @PostMapping("pageSaleConditionList/{current}/{limit}")
    public R pageSaleConditionList(@ApiParam(name = "current",value = "当前页",required = true) @PathVariable Long current,
                                   @ApiParam(name = "limit",value = "每页条目数",required = true) @PathVariable Long limit,
                                   @ApiParam(name = "pageSaleList",value = "查询对象",required = false) @RequestBody SaleQuery saleQuery){
        Page<Sale> pageSale = new Page<>();
        saleService.salePageCondition(pageSale,saleQuery);
        List<Sale> saleRecords = pageSale.getRecords();
        long saleTotal = pageSale.getTotal();

        return R.ok().data("total",saleTotal).data("rows",saleRecords);
    }

}

