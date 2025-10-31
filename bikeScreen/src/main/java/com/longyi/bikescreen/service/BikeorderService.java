package com.longyi.bikescreen.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.Bikeorder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.longyi.bikescreen.entity.ChartPie;

import java.util.List;

/**
 * <p>
 * 单车订单 服务类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
public interface BikeorderService extends IService<Bikeorder> {
    IPage PageList(IPage<Bikeorder> page, Wrapper wrapper);

    Integer moneyByUser(Long userId);
    List<ChartPie> allMoney(String bike);

    List<ChartPie> weekMoney(String bike);

    List<ChartPie> monthMoney(String bike);

    List<ChartPie> yearMoney(String bike);

    List<ChartPie> bikeUser();

    List<ChartPie> bikeOrder();

    List<ChartPie> allCount(String bike);

    List<ChartPie> yearCount(String bike);

    List<ChartPie> monthCount(String bike);

    List<ChartPie> weekCount(String bike);

    List<ChartPie> bikeOrderCount();
}
