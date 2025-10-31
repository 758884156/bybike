package com.longyi.bikescreen.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.ChartPie;
import com.longyi.bikescreen.entity.Recovery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 回收 服务类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
public interface RecoveryService extends IService<Recovery> {
    IPage PageList(IPage<Recovery> page, Wrapper wrapper);

    List<ChartPie> bikeRecovery(String province);
}
