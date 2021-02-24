package com.dikun.servicestock.entity.vo;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.dikun.servicestock.entity.Goods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "Goods查询对象",description = "商品查询对象分装")
@Data

public class GoodsQuery  implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "库存数量")
    private Integer sumBegin;

    @ApiModelProperty(value = "库存数量")
    private Integer sumEnd;

    @ApiModelProperty(value = "查询开始时间",example = "2019-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间",example = "2019-01-01 10:10:10")
    private String end;


}
