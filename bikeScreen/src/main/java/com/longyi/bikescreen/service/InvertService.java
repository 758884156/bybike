package com.longyi.bikescreen.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.ChartPie;
import com.longyi.bikescreen.entity.Invert;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 单车投放 服务类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
public interface InvertService extends IService<Invert> {
    IPage PageList(IPage<Invert> page, Wrapper wrapper);

    List<ChartPie> bikeInvert(String province);

    List<ChartPie> bikeCity();

    List<ChartPie> mapChart();
}
