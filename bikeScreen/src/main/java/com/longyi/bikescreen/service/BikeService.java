package com.longyi.bikescreen.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.longyi.bikescreen.entity.Bike;
import com.baomidou.mybatisplus.extension.service.IService;
import com.longyi.bikescreen.entity.ChartPie;

import java.util.List;

/**
 * <p>
 * 单车 服务类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
public interface BikeService extends IService<Bike> {
    IPage PageList(IPage<Bike> page, Wrapper wrapper);

    List<ChartPie> biketype();
}
