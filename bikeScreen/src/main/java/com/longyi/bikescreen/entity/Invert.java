package com.longyi.bikescreen.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 单车投放
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Invert对象", description="单车投放")
public class Invert implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "投放id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "投放单车")
    private Integer bike;

    @ApiModelProperty(value = "投放量")
    private Integer count;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "投放时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "投放地址")
    private String address;

    @ApiModelProperty(value = "投放省份")
    private String province;

    @ApiModelProperty(value = "投放城市")
    private String city;

    @ApiModelProperty(value = "经度")
    private BigDecimal lon;

    @ApiModelProperty(value = "纬度")
    private BigDecimal lat;


}
