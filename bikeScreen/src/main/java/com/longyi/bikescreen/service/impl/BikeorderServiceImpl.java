package com.longyi.bikescreen.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.Bikeorder;
import com.longyi.bikescreen.entity.ChartPie;
import com.longyi.bikescreen.mapper.BikeorderMapper;
import com.longyi.bikescreen.service.BikeorderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 单车订单 服务实现类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Service
public class BikeorderServiceImpl extends ServiceImpl<BikeorderMapper, Bikeorder> implements BikeorderService {
    @Resource
    private BikeorderMapper bikeorderMapper;

    @Override
    public IPage PageList(IPage<Bikeorder> page, Wrapper wrapper) {
        return bikeorderMapper.PageList(page,wrapper);
    }

    @Override
    public Integer moneyByUser(Long userId) {
        return bikeorderMapper.moneyByUser(userId);
    }
    @Override
    public List<ChartPie> allMoney(String bike) {
        return bikeorderMapper.allMoney(bike);
    }

    @Override
    public List<ChartPie> weekMoney(String bike) {
        return bikeorderMapper.weekMoney(bike);
    }

    @Override
    public List<ChartPie> monthMoney(String bike) {
        return bikeorderMapper.monthMoney(bike);
    }

    @Override
    public List<ChartPie> yearMoney(String bike) {
        return bikeorderMapper.yearMoney(bike);
    }

    @Override
    public List<ChartPie> bikeUser() {
        return bikeorderMapper.bikeUser();
    }

    @Override
    public List<ChartPie> bikeOrder() {
        return bikeorderMapper.bikeOrder();
    }

    @Override
    public List<ChartPie> allCount(String bike) {
        return bikeorderMapper.allCount(bike);
    }

    @Override
    public List<ChartPie> yearCount(String bike) {
        return bikeorderMapper.yearCount(bike);
    }

    @Override
    public List<ChartPie> monthCount(String bike) {
        return bikeorderMapper.monthCount(bike);
    }

    @Override
    public List<ChartPie> weekCount(String bike) {
        return bikeorderMapper.weekCount(bike);
    }

    @Override
    public List<ChartPie> bikeOrderCount() {
        return bikeorderMapper.bikeOrderCount();
    }


}
