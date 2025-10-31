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
 * 单车
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Bike对象", description="单车")
public class Bike implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单车id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "单车名称")
    private String name;

    @ApiModelProperty(value = "单车平台")
    private Integer platform;

    @ApiModelProperty(value = "起步价（元）")
    private BigDecimal firstPrice;

    @ApiModelProperty(value = "起步时长(分钟）")
    private Integer firstDistance;

    @ApiModelProperty(value = "计时价(元/分钟）")
    private BigDecimal price;

    @ApiModelProperty(value = "单车图片")
    private String image;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "介绍")
    private String remark;

    @ApiModelProperty(value = "单车投放总数")
    private Integer count;


}
