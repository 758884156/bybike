package com.longyi.bikescreen.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 收益
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Income对象", description="收益")
public class Income implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收益id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户")
    private Integer user;

    @ApiModelProperty(value = "单车")
    private Integer bike;

    @ApiModelProperty(value = "订单")
    private Integer bikeorder;

    @ApiModelProperty(value = "金额")
    private BigDecimal money;

    @ApiModelProperty(value = "收益时间")
    private LocalDateTime createTime;


}
