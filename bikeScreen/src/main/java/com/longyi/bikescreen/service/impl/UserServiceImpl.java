package com.longyi.bikescreen.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.longyi.bikescreen.entity.User;
import com.longyi.bikescreen.mapper.UserMapper;
import com.longyi.bikescreen.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private  UserMapper userMapper;
    @Override
    public IPage PageList(IPage<User> page, Wrapper wrapper) {
        return userMapper.PageList(page,wrapper);
    }

}
