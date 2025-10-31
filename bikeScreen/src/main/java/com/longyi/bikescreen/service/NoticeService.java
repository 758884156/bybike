package com.longyi.bikescreen.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 公告通知 服务类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
public interface NoticeService extends IService<Notice> {
    IPage PageList(IPage<Notice> page, Wrapper wrapper);
}
