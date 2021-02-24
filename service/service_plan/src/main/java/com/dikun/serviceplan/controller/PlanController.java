package com.dikun.serviceplan.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dikun.serviceplan.entity.Plan;
import com.dikun.serviceplan.entity.vo.PlanQuery;
import com.dikun.serviceplan.service.PlanService;
import commonutils.R;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dikun
 * @since 2021-02-19
 */
@RestController
@RequestMapping("/serviceplan/plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    @ApiOperation(value = "查询所有生产计划")
    @GetMapping
    public R list(){

         List<Plan> planList = planService.list(null);

        return R.ok().data("items",planList);
    }

    /**条件查询对象*/
    @ApiOperation(value = "条件生产计划列表")
    @PostMapping("planPageConditionList")
    public R planConditionList(@ApiParam(name = "planQuery",value = "查询对象",required = false)
                                   @RequestBody PlanQuery planQuery){
        Page<Plan> pagePlan = new Page<>();
        planService.planGoodsQuery(planQuery);
        List<Plan> planRecords = pagePlan.getRecords();

        return R.ok().data("rows",planRecords);
    }


}

