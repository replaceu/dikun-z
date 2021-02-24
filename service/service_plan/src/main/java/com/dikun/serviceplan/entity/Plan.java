package com.dikun.serviceplan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Plan对象", description="")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("goodsName")
    private String goodsName;

    @TableField("S")
    private Long s;

    @TableField("M")
    private Long m;

    @TableField("L")
    private Long l;

    @TableField("XL")
    private Long xl;

    @TableField("XXL")
    private Long xxl;

    private Long number;


}
