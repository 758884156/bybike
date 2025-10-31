package com.longyi.bikescreen.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 公告通知 Mapper 接口
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    IPage PageList(IPage<Notice> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
