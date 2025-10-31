package com.longyi.bikescreen.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.Platform;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 平台 服务类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
public interface PlatformService extends IService<Platform> {
    IPage PageList(IPage<Platform> page, Wrapper wrapper);
}
