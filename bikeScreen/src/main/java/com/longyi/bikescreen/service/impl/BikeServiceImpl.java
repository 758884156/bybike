package com.longyi.bikescreen.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.ChartPie;
import com.longyi.bikescreen.mapper.BikeMapper;
import com.longyi.bikescreen.service.BikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 单车 服务实现类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Service
public class BikeServiceImpl extends ServiceImpl<BikeMapper, Bike> implements BikeService {
    @Resource
    private BikeMapper bikeMapper;
    @Override
    public IPage PageList(IPage<Bike> page, Wrapper wrapper) {
        return bikeMapper.PageList(page,wrapper);
    }

    @Override
    public List<ChartPie> biketype() {
        return bikeMapper.biketype();
    }

}
