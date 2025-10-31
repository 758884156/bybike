package com.longyi.bikescreen.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GetPath {
    @Autowired
    private  ApplicationContext applicationContext;
//    获取项目路径
    public  String getProjectPath() throws IOException {
        ResourceLoader resourceLoader=applicationContext;
        Resource resource = resourceLoader.getResource("classpath:");
//        System.out.println(resource.getFile().getAbsolutePath());
        return resource.getFile().getParentFile().getParentFile().getAbsolutePath()+"\\src\\main\\resources\\static";
    }


}

