package com.longyi.bikescreen.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.ChartPie;
import com.longyi.bikescreen.entity.Invert;
import com.longyi.bikescreen.mapper.InvertMapper;
import com.longyi.bikescreen.service.InvertService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 单车投放 服务实现类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Service
public class InvertServiceImpl extends ServiceImpl<InvertMapper, Invert> implements InvertService {
    @Resource
    private InvertMapper invertMapper;
    @Override
    public IPage PageList(IPage<Invert> page, Wrapper wrapper) {
        return invertMapper.PageList(page,wrapper);
    }

    @Override
    public List<ChartPie> bikeInvert(String province) {
        return invertMapper.bikeInvert(province);
    }

    @Override
    public List<ChartPie> bikeCity() {
        return invertMapper.bikeCity();
    }

    @Override
    public List<ChartPie> mapChart() {
        return invertMapper.mapChart();
    }

}
