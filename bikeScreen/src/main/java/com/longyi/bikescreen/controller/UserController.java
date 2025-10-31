package com.longyi.bikescreen.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.longyi.bikescreen.common.QueryPageParam;
import com.longyi.bikescreen.common.Result;
import com.longyi.bikescreen.entity.Menu;
import com.longyi.bikescreen.entity.User;
import com.longyi.bikescreen.service.MenuService;
import com.longyi.bikescreen.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author longyi
 * @since 2024-07-25
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }
    @GetMapping("/allcount")
    public Result allcount(){
        HashMap<Object, Object> res = new HashMap<>();
        Integer userCount = userService.lambdaQuery().eq(User::getStatus,1).eq(User::getRole,2).count();
        Integer adminCount=userService.lambdaQuery().eq(User::getStatus,1).ne(User::getRole,2).count();
        res.put("userCount",userCount);
        res.put("adminCount",adminCount);
        return Result.suc(res);

    }
    @GetMapping("/count")
    public Integer count() {
        return userService.count();
    }
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        List<User> list=userService.lambdaQuery().eq(User::getName,user.getName()).eq(User::getRole,2).list();
        if(list.size()>0){
            return Result.fail("用户名重复");
        }
        list=userService.lambdaQuery().eq(User::getTelephone,user.getTelephone()).eq(User::getRole,2).list();
        if(list.size()>0){
            return Result.fail("手机号重复");
        }
        return userService.save(user)?Result.suc("新增成功"):Result.fail("新增失败");

    }
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        List<User> list=userService.lambdaQuery().eq(User::getName,user.getName()).eq(User::getRole,2).ne(User::getId,user.getId()).list();
        if(list.size()>0){
            return Result.fail("用户名重复");
        }
        list=userService.lambdaQuery().eq(User::getTelephone,user.getTelephone()).eq(User::getRole,2).ne(User::getId,user.getId()).list();
        if(list.size()>0){
            return Result.fail("手机号重复");
        }
        return userService.updateById(user)?Result.suc("修改成功"):Result.fail("修改失败");

    }
    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        return  userService.removeById(id)?Result.suc("删除成功"):Result.fail("删除失败");
    }
    @PostMapping("/modStatus/{id}")
    public Result modStatus(@PathVariable("id") Long id){
        User user = userService.getById(id);
        if(user.getStatus()==1){
            user.setStatus(0);
        }else  user.setStatus(1);
        return userService.updateById(user)?Result.suc("修改成功"):Result.fail("修改失败");

    }
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id){
        return  Result.suc(userService.getById(id));
    }
    @Transactional
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Long[] id){
        userService.removeByIds(Arrays.asList(id));
        return  Result.suc("批量删除成功");
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name =(String) param.get("name");
        String telephone = (String)param.get("telephone");
        String role =(String) param.get("role");
        String sex= (String) param.get("sex");
        String status = (String) param.get("status");;
        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());;
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)&&!"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }
        if (StringUtils.isNotBlank(telephone)&&!"null".equals(telephone)){
            lambdaQueryWrapper.like(User::getTelephone,telephone);
        }if (StringUtils.isNotBlank(role)&&!"null".equals(role)){
            lambdaQueryWrapper.eq(User::getRole,role);
        }if (StringUtils.isNotBlank(sex)&&!"null".equals(sex)){
            lambdaQueryWrapper.eq(User::getSex,sex);
        }
        if (StringUtils.isNotBlank(status)&&!"null".equals(status)){
            lambdaQueryWrapper.eq(User::getStatus,status);
        }
        IPage result=userService.PageList(page,lambdaQueryWrapper);
        return  Result.suc(result.getTotal(),result.getRecords());
    }
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        if(StpUtil.isLogin()){
            User user1 = userService.lambdaQuery().eq(User::getName, user.getName()).eq(User::getPassword, user.getPassword()).list().get(0);
            StpUtil.login(user1.getId());
            List<Menu> menuList = menuService.lambdaQuery().like(Menu::getMenuright, user1.getId()).list();
            HashMap res=new HashMap<>();
            res.put("user",user1);
            res.put("menu",menuList);
            return  Result.suc(res);
        }else{
            List<User> list = userService.lambdaQuery().eq(User::getName, user.getName()).eq(User::getPassword, user.getPassword()).list();
            if (list.size()>0){
                User user1 = list.get(0);
                if(user1.getStatus()!=1){
                    return  Result.fail("账户已注销");
                }else{
                    StpUtil.login(user1.getId());
                    List<Menu> menuList = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRole()).list();
                    HashMap res=new HashMap<>();
                    res.put("user",user1);
                    res.put("menu",menuList);
                    System.out.println(menuList);
                    return  Result.suc(res);
                }
            }
            list = userService.lambdaQuery().eq(User::getName, user.getTelephone()).eq(User::getPassword, user.getPassword()).list();
            if (list.size()>0){
                User user1 = list.get(0);
                if(user1.getStatus()!=1){
                    return  Result.fail("账户已注销");
                }else{
                    StpUtil.login(user1.getId());
                    List<Menu> menuList = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRole()).list();
                    HashMap res=new HashMap<>();
                    res.put("user",user1);
                    res.put("menu",menuList);
                    return  Result.suc(res);
                }
            }
        }

        return Result.fail("账户密码不存在");
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        List<User> list=userService.lambdaQuery().eq(User::getName,user.getName()).eq(User::getRole,2).list();
        if(list.size()>0){
            return Result.fail("用户名重复");
        }
        list=userService.lambdaQuery().eq(User::getTelephone,user.getTelephone()).eq(User::getRole,2).list();
        if(list.size()>0){
            return Result.fail("手机号重复");
        }
        return userService.save(user)?Result.suc("注册成功"):Result.fail("注册失败");

    }
    @GetMapping("/getAvatar/{name}")
    public Result getAvatar(@PathVariable("name") String name){
        List<User> list=userService.lambdaQuery().eq(User::getName,name).list();
        if(list.size()>0){
            return Result.suc(list.get(0).getAvatar());
        }
        list=userService.lambdaQuery().eq(User::getTelephone,name).list();
        if(list.size()>0){
            return Result.suc(list.get(0).getAvatar());
        }
        return Result.fail("用户不存在");

    }
    @Transactional
    @GetMapping("/modAvatar")
    public Result modAvatar(@RequestParam Integer id, String avatar) {
        User user = userService.getById(id);
        user.setAvatar(avatar);
        userService.updateById(user);
        return Result.suc(userService.getById(id));
    }
    @Transactional
    @GetMapping("/modName")
    public Result modName(@RequestParam Integer id, String name) {
        User user = userService.getById(id);
        user.setName(name);
        List list =
                userService.lambdaQuery().eq(User::getName, name).ne(User::getId, id).list();
        if (list.size() > 0) return Result.fail("账号已被注册，请换一个名字");
        else {
            userService.updateById(user);
            return Result.suc(userService.getById(id));
        }
    }
    @GetMapping("/modPwd")
    public Result modPwd(@RequestParam Integer id, String password) {
        User user = userService.getById(id);
        user.setPassword(password);
        userService.updateById(user);
        return Result.suc(userService.getById(id));
    }
    @PostMapping("/repass/{id}")
    public Result repass(@PathVariable Integer id) {
        User user = userService.getById(id);
        user.setPassword("123456");
        return userService.updateById(user) ? Result.suc() : Result.fail();
    }
    @GetMapping("/logout")
    public SaResult logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
        }
        return SaResult.ok();
    }
}
