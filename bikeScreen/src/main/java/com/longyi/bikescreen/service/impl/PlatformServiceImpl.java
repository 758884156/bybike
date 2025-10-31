package com.longyi.bikescreen.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.Platform;
import com.longyi.bikescreen.mapper.PlatformMapper;
import com.longyi.bikescreen.service.PlatformService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 平台 服务实现类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Service
public class PlatformServiceImpl extends ServiceImpl<PlatformMapper, Platform> implements PlatformService {
    @Resource
    private PlatformMapper platformMapper;
    @Override
    public IPage PageList(IPage<Platform> page, Wrapper wrapper) {
        return platformMapper.PageList(page,wrapper);
    }

}
