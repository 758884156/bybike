package com.longyi.bikescreen.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.ChartPie;
import com.longyi.bikescreen.entity.Income;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 收益 服务类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
public interface IncomeService extends IService<Income> {
    IPage PageList(IPage<Income> page, Wrapper wrapper);

    Integer moneyByUser(Long userId);
    List<ChartPie> allMoney(String bike);

    List<ChartPie> weekMoney(String bike);

    List<ChartPie> monthMoney(String bike);

    List<ChartPie> yearMoney(String bike);

    List<ChartPie> bikeIncome();

    List<ChartPie> bikeIncomeCount();
}
