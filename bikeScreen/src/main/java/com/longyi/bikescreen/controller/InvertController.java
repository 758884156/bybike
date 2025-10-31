package com.longyi.bikescreen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.longyi.bikescreen.common.QueryPageParam;
import com.longyi.bikescreen.common.Result;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.Invert;
import com.longyi.bikescreen.entity.Province;
import com.longyi.bikescreen.service.BikeService;
import com.longyi.bikescreen.service.InvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 单车投放 前端控制器
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@RestController
@RequestMapping("/invert")
public class InvertController {
    @Autowired
    private InvertService invertService;
    @Autowired
    private BikeService bikeService;

    @GetMapping("/provinceList")
    public List<Province> provinceList() {
        List<Invert> list = invertService.list();
        HashSet<String> provinces = new HashSet<>();
        for (Invert invert : list) {
            provinces.add(invert.getProvince());
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
        List<Invert> list = invertService.list();
        for (Invert invert : list) {
            count += invert.getCount();
        }
        return count;
    }
    @GetMapping("/list")
    public List<Invert> list() {
        return invertService.list();
    }

    @Transactional
    @PostMapping("/save")
    public Result save(@RequestBody Invert invert) {
        Bike bike = bikeService.getById(invert.getBike());
        bike.setCount(bike.getCount() + invert.getCount());
        bikeService.updateById(bike);
        return invertService.save(invert) ? Result.suc("新增成功") : Result.fail("新增失败");
    }
    @Transactional
    @PostMapping("/update")
    public Result update(@RequestBody Invert invert) {
        Invert invert1 = invertService.getById(invert.getId());
        Bike bike = bikeService.getById(invert.getBike());
        bike.setCount(bike.getCount() + (invert.getCount() - invert1.getCount()));
        bikeService.updateById(bike);
        return invertService.updateById(invert) ? Result.suc("修改成功") : Result.fail("修改失败");
    }
    @Transactional
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Long[] ids) {
        for (Long id : ids) {
            Invert invert = invertService.getById(id);
            Bike bike = bikeService.getById(invert.getBike());
            bike.setCount(bike.getCount()  -invert.getCount());
            bikeService.updateById(bike);
        }
        invertService.removeByIds(Arrays.asList(ids));
        return Result.suc("批量删除成功");
    }
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        Invert invert = invertService.getById(id);
        return Result.suc(invert);
    }
    @PostMapping("/sub")
    public Result sub(@RequestParam Integer id,Integer count ) {
        Invert invert = invertService.getById(id);
        invert.setCount(invert.getCount() -count);
        return invertService.updateById(invert) ? Result.suc("修改成功") : Result.fail("修改失败");
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String bike = (String) param.get("bike");
        String province = (String) param.get("province");
        String createTime = (String) param.get("createTime");

        Page<Invert> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Invert> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(bike) && !"null".equals(bike)) {
            lambdaQueryWrapper.eq(Invert::getBike, bike);
        }
        if (StringUtils.isNotBlank(province) && !"null".equals(province)) {
            lambdaQueryWrapper.eq(Invert::getProvince, province);
        }
        if (StringUtils.isNotBlank(createTime) && !"null".equals(createTime)) {
            lambdaQueryWrapper.like(Invert::getCreateTime, createTime);
        }
        IPage result = invertService.PageList(page, lambdaQueryWrapper);
        return Result.suc(result.getTotal(), result.getRecords());
    }
}
