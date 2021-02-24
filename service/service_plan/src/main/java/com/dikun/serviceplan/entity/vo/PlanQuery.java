package com.dikun.serviceplan.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "Goods查询对象",description = "商品查询对象分装")
@Data

public class PlanQuery implements Serializable{
    private  static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品名称，模糊查询")
    private String name;
}
