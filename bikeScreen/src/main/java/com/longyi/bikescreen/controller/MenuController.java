package com.longyi.bikescreen.controller;


import com.longyi.bikescreen.common.Result;
import com.longyi.bikescreen.entity.Menu;
import com.longyi.bikescreen.entity.User;
import com.longyi.bikescreen.service.MenuService;
import com.longyi.bikescreen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author longyi
 * @since 2024-08-29
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    private List<Menu> list() {
        return menuService.list();
    }
    @GetMapping("/role/{role}")
    private Result roleMenu(@PathVariable Integer role){
        return  Result.suc(menuService.lambdaQuery().like(Menu::getMenuright,role).list());
    }
    @GetMapping("/{id}")
    private Result userMenu(@PathVariable Integer id){
        User user = userService.getById(id);
        return  Result.suc(menuService.lambdaQuery().like(Menu::getMenuright,user.getRole()).list());
    }
}
