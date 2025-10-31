package com.longyi.bikescreen.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.ChartPie;
import com.longyi.bikescreen.entity.Income;
import com.longyi.bikescreen.mapper.IncomeMapper;
import com.longyi.bikescreen.service.IncomeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 收益 服务实现类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Service
public class IncomeServiceImpl extends ServiceImpl<IncomeMapper, Income> implements IncomeService {
    @Resource
    private IncomeMapper incomeMapper;

    @Override
    public IPage PageList(IPage<Income> page, Wrapper wrapper) {
        return incomeMapper.PageList(page,wrapper);
    }

    @Override
    public Integer moneyByUser(Long userId) {
        return incomeMapper.moneyByUser(userId);
    }
    @Override
    public List<ChartPie> allMoney(String bike) {
        return incomeMapper.allMoney(bike);
    }

    @Override
    public List<ChartPie> weekMoney(String bike) {
        return incomeMapper.weekMoney(bike);
    }

    @Override
    public List<ChartPie> monthMoney(String bike) {
        return incomeMapper.monthMoney(bike);
    }

    @Override
    public List<ChartPie> yearMoney(String bike) {
        return incomeMapper.yearMoney(bike);
    }

    @Override
    public List<ChartPie> bikeIncome() {
        return incomeMapper.bikeIncome();
    }

    @Override
    public List<ChartPie> bikeIncomeCount() {
        return incomeMapper.bikeIncomeCount();
    }


}
