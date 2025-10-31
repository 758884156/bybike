package com.longyi.bikescreen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.longyi.bikescreen.common.CodeUtils;
import com.longyi.bikescreen.common.QueryPageParam;
import com.longyi.bikescreen.common.Result;
import com.longyi.bikescreen.entity.Bike;
import com.longyi.bikescreen.entity.Bikeorder;
import com.longyi.bikescreen.entity.Income;
import com.longyi.bikescreen.service.BikeService;
import com.longyi.bikescreen.service.BikeorderService;
import com.longyi.bikescreen.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 单车订单 前端控制器
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@RestController
@RequestMapping("/bikeorder")
public class BikeorderController {
    @Autowired
    private BikeorderService bikeorderService;
    @Autowired
    private BikeService bikeService;
    @Autowired
    private IncomeService incomeService;
    @GetMapping("/list")
    public List<Bikeorder> list() {
        return bikeorderService.list();
    }
    @GetMapping("/count/{userId}")
    public Integer count(@PathVariable Long userId) {
        return   bikeorderService.moneyByUser(userId);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Bikeorder bikeorder) {
        List<Bikeorder> list = bikeorderService.lambdaQuery().eq(Bikeorder::getUser, bikeorder.getUser()).eq(Bikeorder::getBike, bikeorder.getBike()).ne(Bikeorder::getStatus, 2).list();
        if (list.size() > 0) {
            return Result.fail("您还有未支付的订单，无法再次下单！");
        }
        Bike bike = bikeService.getById(bikeorder.getBike());
        bikeorder.setMoney(bike.getFirstPrice());
        bikeorder.setCreateTime(LocalDateTime.now());
        bikeorder.setOrderCode(CodeUtils.orderCode());
        return bikeorderService.save(bikeorder) ? Result.suc("新增成功") : Result.fail("新增失败");

    }
    @GetMapping("/ensure/{id}")
    public Result ensure(@PathVariable("id") Integer id) {
        Bikeorder bikeorder = bikeorderService.getById(id);
        bikeorder.setFinishTime(LocalDateTime.now());
        Bike bike = bikeService.getById(bikeorder.getBike());
        bikeorder.setUseTime(CodeUtils.calculateAndSetUseTime(bikeorder.getFinishTime(),bikeorder.getCreateTime()));
        bikeorder.setMoney(CodeUtils.calacMoney(bikeorder.getUseTime(),bike));
        bikeorder.setStatus(1);
        return bikeorderService.updateById(bikeorder)? Result.suc("结单成功") : Result.fail("结单失败");
    }
    @Transactional
    @GetMapping("/pay/{id}")
    public Result pay(@PathVariable("id") Integer id) {
        Bikeorder bikeorder = bikeorderService.getById(id);
        bikeorder.setPayTime(LocalDateTime.now());  bikeorder.setStatus(2);
        Income income = new Income();
        income.setBike(bikeorder.getBike());
        income.setMoney(bikeorder.getMoney());
        income.setUser(bikeorder.getUser());
        income.setBikeorder(id);
        income.setCreateTime(LocalDateTime.now());
        incomeService.save(income);

        return bikeorderService.updateById(bikeorder)? Result.suc("支付成功") : Result.fail("支付失败");
    }
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        Bikeorder bikeorder = bikeorderService.getById(id);
        return Result.suc(bikeorder);
    }
    @Transactional
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Long[] ids) {
        for(Long id :ids){
            incomeService.removeById(incomeService.lambdaQuery().eq(Income::getBikeorder,id).list().get(0).getId());
        }
        bikeorderService.removeByIds(Arrays.asList(ids));
        return Result.suc("批量删除成功");
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String user= (String) param.get("user");
        String bike= (String) param.get("bike");

        String status = (String) param.get("status");
        Page<Bikeorder> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Bikeorder> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(bike) && !"null".equals(bike)) {
            lambdaQueryWrapper.eq(Bikeorder::getBike, bike);
        }
        if (StringUtils.isNotBlank(user) && !"null".equals(user)) {
            lambdaQueryWrapper.eq(Bikeorder::getUser, user);
        }
        if (StringUtils.isNotBlank(status) && !"null".equals(status)) {
            lambdaQueryWrapper.eq(Bikeorder::getStatus, status);
        }
        lambdaQueryWrapper.orderByDesc(Bikeorder::getCreateTime);

        IPage result = bikeorderService.PageList(page, lambdaQueryWrapper);

        return Result.suc(result.getTotal(), result.getRecords());
    }
}
