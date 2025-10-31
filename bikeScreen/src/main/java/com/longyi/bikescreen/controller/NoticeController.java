package com.longyi.bikescreen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.longyi.bikescreen.common.QueryPageParam;
import com.longyi.bikescreen.common.Result;
import com.longyi.bikescreen.entity.Notice;
import com.longyi.bikescreen.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 公告通知 前端控制器
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public List<Notice> list() {
        return noticeService.list();
    }
    @PostMapping("/save")
    public Result save(@RequestBody Notice notice) {

        return noticeService.save(notice) ? Result.suc("新增成功") : Result.fail("新增失败");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Notice notice) {

        return noticeService.updateById(notice)? Result.suc("修改成功") : Result.fail("修改失败");

    }
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        Notice notice = noticeService.getById(id);
        return Result.suc(notice);
    }
    @Transactional
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Long[] ids) {
        noticeService.removeByIds(Arrays.asList(ids));
        return Result.suc("批量删除成功");
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String title = (String) param.get("title");
        String createTime = (String) param.get("createTime");
        Page<Notice> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Notice> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(title) && !"null".equals(title)) {
            lambdaQueryWrapper.like(Notice::getTitle, title);
        }
        if (StringUtils.isNotBlank(createTime) && !"null".equals(createTime)) {
            lambdaQueryWrapper.like(Notice::getCreateTime, createTime);
        }
        IPage result = noticeService.PageList(page, lambdaQueryWrapper);

        return Result.suc( result.getTotal(),result.getRecords());
    }
}
