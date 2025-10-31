package com.longyi.bikescreen.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.ChartPie;
import com.longyi.bikescreen.entity.Income;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 收益 Mapper 接口
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Mapper
public interface IncomeMapper extends BaseMapper<Income> {
    IPage PageList(IPage<Income> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    Integer moneyByUser(Long userId);
    List<ChartPie> allMoney(String bike);

    List<ChartPie> weekMoney(String bike);

    List<ChartPie> monthMoney(String bike);

    List<ChartPie> yearMoney(String bike);
    List<ChartPie> bikeIncome();

    List<ChartPie> bikeIncomeCount();


}
