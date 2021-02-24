package com.dikun.serviceplan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dikun.serviceplan.entity.Plan;
import com.dikun.serviceplan.entity.vo.PlanQuery;
import com.dikun.serviceplan.mapper.PlanMapper;
import com.dikun.serviceplan.service.PlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dikun
 * @since 2021-02-19
 */
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements PlanService {

    @Override
    public void planGoodsQuery(PlanQuery planQuery) {
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();

         Page<Plan> page = new Page<>();

        if (queryWrapper==null){
            baseMapper.selectPage(page,queryWrapper);
            return;
        }

        String name = planQuery.getName();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("goodsName", name);
        }

        baseMapper.selectPage(page,queryWrapper);

    }
}
