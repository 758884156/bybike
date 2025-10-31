package com.longyi.bikescreen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.longyi.bikescreen.common.QueryPageParam;
import com.longyi.bikescreen.common.Result;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.Province;
import com.longyi.bikescreen.entity.Recovery;
import com.longyi.bikescreen.service.BikeService;
import com.longyi.bikescreen.service.RecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 回收 前端控制器
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@RestController
@RequestMapping("/recovery")
public class RecoveryController {
    @Autowired
    private RecoveryService recoveryService;
    @Autowired
    private BikeService bikeService;

    @GetMapping("/provinceList")
    public List<Province> provinceList() {
        List<Recovery> list = recoveryService.list();
        HashSet<String> provinces = new HashSet<>();
        for (Recovery recovery : list) {
            provinces.add(recovery.getProvince());
        }
        List<Province> result = new ArrayList();
        int id=0;
        for (String provinceName : provinces) {
            Province province = new Province();
            province.setId(id++);
            province.setName(provinceName);
            result.add(province);

        }
        return result;
    }
    @GetMapping("/count")
    public Integer count() {
        Integer count = 0;
        List<Recovery> list = recoveryService.list();
        for (Recovery recovery : list) {
            count += recovery.getCount();
        }
        return count;
    }
    @GetMapping("/list")
    public List<Recovery> list() {
        return recoveryService.list();
    }

    @Transactional
    @PostMapping("/save")
    public Result save(@RequestBody Recovery recovery) {
        Bike bike = bikeService.getById(recovery.getBike());
        bike.setCount(bike.getCount() - recovery.getCount());
        bikeService.updateById(bike);
        return recoveryService.save(recovery) ? Result.suc("新增成功") : Result.fail("新增失败");
    }
    @Transactional
    @PostMapping("/update")
    public Result update(@RequestBody Recovery recovery) {
        Recovery recovery1 = recoveryService.getById(recovery.getId());
        Bike bike = bikeService.getById(recovery.getBike());
        bike.setCount(bike.getCount() -(recovery.getCount() - recovery1.getCount()));
        bikeService.updateById(bike);
        return recoveryService.updateById(recovery) ? Result.suc("修改成功") : Result.fail("修改失败");
    }
    @Transactional
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Long[] ids) {
        for (Long id : ids) {
            Recovery recovery = recoveryService.getById(id);
            Bike bike = bikeService.getById(recovery.getBike());
            bike.setCount(bike.getCount()  +recovery.getCount());
            bikeService.updateById(bike);
        }
        recoveryService.removeByIds(Arrays.asList(ids));
        return Result.suc("批量删除成功");
    }
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        Recovery recovery = recoveryService.getById(id);
        return Result.suc(recovery);
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String bike = (String) param.get("bike");
        String province = (String) param.get("province");
        String createTime = (String) param.get("createTime");

        Page<Recovery> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Recovery> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(bike) && !"null".equals(bike)) {
            lambdaQueryWrapper.eq(Recovery::getBike, bike);
        }
        if (StringUtils.isNotBlank(province) && !"null".equals(province)) {
            lambdaQueryWrapper.eq(Recovery::getProvince, province);
        }
        if (StringUtils.isNotBlank(createTime) && !"null".equals(createTime)) {
            lambdaQueryWrapper.like(Recovery::getCreateTime, createTime);
        }
        IPage result = recoveryService.PageList(page, lambdaQueryWrapper);
        return Result.suc(result.getTotal(), result.getRecords());
    }
}
