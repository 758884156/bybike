package com.longyi.bikescreen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.longyi.bikescreen.common.QueryPageParam;
import com.longyi.bikescreen.common.Result;
import com.longyi.bikescreen.entity.Platform;
import com.longyi.bikescreen.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 平台 前端控制器
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@RestController
@RequestMapping("/platform")
public class PlatformController {
    @Autowired
    PlatformService platformService;

    @GetMapping("/count")
    public Integer count() {

        return platformService.count();
    }

    @GetMapping("/list")
    public List<Platform> list() {
        return platformService.list();
    }

    @PostMapping("/save")
    public Result save(@RequestBody Platform platform) {
        List<Platform> list = platformService.lambdaQuery().eq(Platform::getName, platform.getName()).list();
        if (list.size() > 0) {
            return Result.fail("单车平台重复，换一个吧！");
        }
        return platformService.save(platform) ? Result.suc("新增成功") : Result.fail("新增失败");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Platform platform) {
        List<Platform> list = platformService.lambdaQuery().eq(Platform::getName, platform.getName()).ne(Platform::getId, platform.getId()).list();
        if (list.size() > 0) {
            return Result.fail("单车平台重复，换一个吧！");
        }
        return platformService.updateById(platform)? Result.suc("修改成功") : Result.fail("修改失败");

    }
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        Platform platform = platformService.getById(id);
        return Result.suc(platform);
    }
    @Transactional
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Long[] ids) {
        platformService.removeByIds(Arrays.asList(ids));
        return Result.suc("批量删除成功");
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<Platform> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Platform> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Platform::getName, name);
        }
        IPage result = platformService.PageList(page, lambdaQueryWrapper);

        return Result.suc( result.getTotal(),result.getRecords());
    }
}
