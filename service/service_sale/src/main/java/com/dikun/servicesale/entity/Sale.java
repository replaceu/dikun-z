package com.dikun.servicesale.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author dikun
 * @since 2020-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Sale对象", description="")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

    @ApiModelProperty(value = "商品全名")
    private String name;

    @ApiModelProperty(value = "日期")
    private Date date;

    @ApiModelProperty(value = "销售数量")
    private Integer saleNumber;

    @ApiModelProperty(value = "客户数量")
    private Integer customerNumber;

    @ApiModelProperty(value = "下单数量")
    private Integer planNumber;


}
