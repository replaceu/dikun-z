package com.dikun.servicesale.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class SaleQuery implements Serializable{

    private  static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "查询开始时间",example = "2019-01-01 10:10:10")
    private Date begin;

    @ApiModelProperty(value = "查询结束时间",example = "2019-01-01 10:10:10")
    private Date end;

    @ApiModelProperty(value = "销售数量")
    private Integer saleNumber;

}
