package com.longyi.bikescreen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.longyi.bikescreen.common.QueryPageParam;
import com.longyi.bikescreen.common.Result;
import com.longyi.bikescreen.entity.Income;
import com.longyi.bikescreen.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;

/**
 * <p>
 * 收益 前端控制器
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@RestController
@RequestMapping("/income")
public class IncomeController {
    @Autowired
    private IncomeService incomeService;
    @GetMapping("/count/{userId}")
    public Integer count(@PathVariable Long userId) {
        return   incomeService.moneyByUser(userId);
    }
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        Income income = incomeService.getById(id);
        return Result.suc(income);
    }
    @Transactional
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Long[] ids) {
        incomeService.removeByIds(Arrays.asList(ids));
        return Result.suc("批量删除成功");
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String user= (String) param.get("user");
        String bike= (String) param.get("bike");

        String bikeorder = (String) param.get("bikeorder");
        Page<Income> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Income> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(bike) && !"null".equals(bike)) {
            lambdaQueryWrapper.eq(Income::getBike, bike);
        }
        if (StringUtils.isNotBlank(user) && !"null".equals(user)) {
            lambdaQueryWrapper.eq(Income::getUser, user);
        }
        if (StringUtils.isNotBlank(bikeorder) && !"null".equals(bikeorder)) {
            lambdaQueryWrapper.eq(Income::getBikeorder, bikeorder);
        }
        lambdaQueryWrapper.orderByDesc(Income::getCreateTime);

        IPage result = incomeService.PageList(page, lambdaQueryWrapper);

        return Result.suc(result.getTotal(), result.getRecords());
    }
}
