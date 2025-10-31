package com.longyi.bikescreen.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.ChartPie;
import com.longyi.bikescreen.entity.Recovery;
import com.longyi.bikescreen.mapper.RecoveryMapper;
import com.longyi.bikescreen.service.RecoveryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 回收 服务实现类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Service
public class RecoveryServiceImpl extends ServiceImpl<RecoveryMapper, Recovery> implements RecoveryService {
    @Resource
    private RecoveryMapper recoveryMapper;
    @Override
    public IPage PageList(IPage<Recovery> page, Wrapper wrapper) {
        return recoveryMapper.PageList(page,wrapper);
    }

    @Override
    public List<ChartPie> bikeRecovery(String province) {
        return recoveryMapper.bikeRecovery(province);
    }

}
