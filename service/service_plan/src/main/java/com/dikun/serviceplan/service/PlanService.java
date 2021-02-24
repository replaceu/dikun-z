package com.dikun.serviceplan.service;

import com.dikun.serviceplan.entity.Plan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dikun.serviceplan.entity.vo.PlanQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dikun
 * @since 2021-02-19
 */
public interface PlanService extends IService<Plan> {


    void planGoodsQuery(PlanQuery planQuery);
}
