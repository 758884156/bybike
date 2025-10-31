package com.longyi.bikescreen.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.Bikeorder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.longyi.bikescreen.entity.ChartPie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 单车订单 Mapper 接口
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Mapper
public interface BikeorderMapper extends BaseMapper<Bikeorder> {
    IPage PageList(IPage<Bikeorder> page, @Param(Constants.WRAPPER) Wrapper wrapper);

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
