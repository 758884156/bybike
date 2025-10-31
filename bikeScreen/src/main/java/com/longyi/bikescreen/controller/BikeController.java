package com.longyi.bikescreen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.longyi.bikescreen.common.QueryPageParam;
import com.longyi.bikescreen.common.Result;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 单车 前端控制器
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@RestController
@RequestMapping("/bike")
public class BikeController {
    @Autowired
    BikeService bikeService;

    @GetMapping("/count")
    public Integer count() {
        Integer count = 0;
        List<Bike> list = bikeService.list();
        for (Bike bike : list) {
            count += bike.getCount();
        }
        return count;
    }
    @GetMapping("/list")
    public List<Bike> list() {
        return bikeService.list();
    }

    @PostMapping("/save")
    public Result save(@RequestBody Bike bike) {
        List<Bike> list = bikeService.lambdaQuery().eq(Bike::getName, bike.getName()).list();
        if (list.size() > 0) {
            return Result.fail("单车名称重复，换一个吧！");
        }
        return bikeService.save(bike) ? Result.suc("新增成功") : Result.fail("新增失败");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Bike bike) {
        List<Bike> list = bikeService.lambdaQuery().eq(Bike::getName, bike.getName()).ne(Bike::getId, bike.getId()).list();
        if (list.size() > 0) {
            return Result.fail("单车名称重复，换一个吧！");
        }
        return bikeService.updateById(bike)? Result.suc("修改成功") : Result.fail("修改失败");

    }
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        Bike bike = bikeService.getById(id);
        return Result.suc(bike);
    }
    @Transactional
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Long[] ids) {
        bikeService.removeByIds(Arrays.asList(ids));
        return Result.suc("批量删除成功");
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String platform = (String) param.get("platform");

        Page<Bike> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Bike> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Bike::getName, name);
        }
        if (StringUtils.isNotBlank(platform) && !"null".equals(platform)) {
            lambdaQueryWrapper.like(Bike::getPlatform, platform);
        }
        IPage result = bikeService.PageList(page, lambdaQueryWrapper);

        return Result.suc( result.getTotal(),result.getRecords());
    }
}
