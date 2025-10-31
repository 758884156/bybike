package com.longyi.bikescreen.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.ChartPie;
import com.longyi.bikescreen.entity.Invert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 单车投放 Mapper 接口
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Mapper
public interface InvertMapper extends BaseMapper<Invert> {
    IPage PageList(IPage<Invert> page, @Param(Constants.WRAPPER) Wrapper wrapper);
    List<ChartPie> bikeInvert(String province);

    List<ChartPie> bikeCity();
    List<ChartPie> mapChart();
}
