package com.longyi.bikescreen.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.Notice;
import com.longyi.bikescreen.mapper.NoticeMapper;
import com.longyi.bikescreen.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 公告通知 服务实现类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;
    @Override
    public IPage PageList(IPage<Notice> page, Wrapper wrapper) {
        return noticeMapper.PageList(page,wrapper);
    }

}
