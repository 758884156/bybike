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
 * 单车订单
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Bikeorder对象", description="单车订单")
public class Bikeorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单编号")
    private String orderCode;

    @ApiModelProperty(value = "下单用户")
    private Integer user;

    @ApiModelProperty(value = "使用单车")
    private Integer bike;

    @ApiModelProperty(value = "开始使用时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal money;

    @ApiModelProperty(value = "订单状态，0已下单，未确认完成，1，已完成确认，未支付，2已支付")
    private Integer status;

    @ApiModelProperty(value = "结束单车使用时间")
    private LocalDateTime finishTime;

    @ApiModelProperty(value = "使用时长")
    private Integer useTime;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;


}
