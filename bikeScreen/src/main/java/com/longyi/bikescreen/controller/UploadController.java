package com.longyi.bikescreen.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import com.longyi.bikescreen.common.GetPath;
import com.longyi.bikescreen.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Resource
    private GetPath getPath;
//    获取文件扩展名
    private String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i>0&&i<fileName.length()-1) {
            return fileName.substring(i+1);
        }
        return "";
    }
//    获取文件名
    private String getFileName(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i>0&&i<fileName.length()-1) {
            return fileName.substring(0,i);
        }
        return fileName;
    }
//    上传
    @PostMapping("/avatar")
    public Result uploadAvatar(String fileName,@RequestParam("file") MultipartFile file) {

        String flag = getFileName(fileName);
        String ext = getFileExtension(fileName);
        try{
            if(!FileUtil.isDirectory(getPath.getProjectPath()+"/avatar/")){
                FileUtil.mkdir(getPath.getProjectPath()+"/avatar/");
            }
            FileUtil.writeBytes(file.getBytes(),getPath.getProjectPath()+"/avatar/"+flag+"."+ext);
            return Result.suc(flag);
        } catch (IOException e) {
            return  Result.fail(e.getMessage());
        }
    }
    @GetMapping("/avatar/{flag}")
    public void getAvatar(@PathVariable("flag") String flag, HttpServletResponse response) throws IOException {
     String filePath=   getPath.getProjectPath()+"/avatar/";
//        System.out.println(getPath.getProjectPath());
        if(!FileUtil.isDirectory(filePath)){
            FileUtil.mkdir(filePath);
        }
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(filePath);
        String targetFileName=fileNames.stream().filter(name->name.contains(flag)).findAny().orElse("");
        try{
            if (StrUtil.isNotEmpty(targetFileName)){
                response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(targetFileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filePath + targetFileName);
                os=response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
                System.out.println("下载成功");

            }
        } catch (IORuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    @PostMapping("/image")
    public Result uploadImage(String fileName,@RequestParam("file") MultipartFile file) {

        String flag = getFileName(fileName);
        String ext = getFileExtension(fileName);
        try{
            if(!FileUtil.isDirectory(getPath.getProjectPath()+"/image/")){
                FileUtil.mkdir(getPath.getProjectPath()+"/image/");
            }
            FileUtil.writeBytes(file.getBytes(),getPath.getProjectPath()+"/image/"+flag+"."+ext);
            return Result.suc(flag);
        } catch (IOException e) {
            return  Result.fail(e.getMessage());
        }
    }
    @GetMapping("/image/{flag}")
    public void getImage(@PathVariable("flag") String flag, HttpServletResponse response) throws IOException {
        String filePath=   getPath.getProjectPath()+"/image/";
//        System.out.println(getPath.getProjectPath());
        if(!FileUtil.isDirectory(filePath)){
            FileUtil.mkdir(filePath);
        }
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(filePath);
        String targetFileName=fileNames.stream().filter(name->name.contains(flag)).findAny().orElse("");
        try{
            if (StrUtil.isNotEmpty(targetFileName)){
                response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(targetFileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filePath + targetFileName);
                os=response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
                System.out.println("下载成功");

            }
        } catch (IORuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    @GetMapping("/file/{flag}")
    public void getFile(@PathVariable("flag") String flag, HttpServletResponse response) throws IOException {
        String filePath=   getPath.getProjectPath()+"/file/";
//        System.out.println(getPath.getProjectPath());
        if(!FileUtil.isDirectory(filePath)){
            FileUtil.mkdir(filePath);
        }
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(filePath);
        String targetFileName=fileNames.stream().filter(name->name.contains(flag)).findAny().orElse("");
        try{
            if (StrUtil.isNotEmpty(targetFileName)){
                response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(targetFileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filePath + targetFileName);
                os=response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
                System.out.println("下载成功");

            }
        } catch (IORuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}

