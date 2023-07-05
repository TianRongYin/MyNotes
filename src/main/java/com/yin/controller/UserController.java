package com.yin.controller;

import com.yin.domain.Result;
import com.yin.domain.User;
import com.yin.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Value("${head.img}")
    private String basePath;

    @GetMapping("/{username}")
    private Result isEmpty(@PathVariable String username){
        return userService.isEmpty(username);
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userService.save(user) ? Result.success("注册成功"):Result.fail("注册失败");
    }
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        return userService.login(username,password);
    }
    @PostMapping("/isLogin")
    public Result isLogin(@RequestBody String token){
        return userService.isLogin(token);
    }
    @PostMapping("/information")
    public Result information(){return null;}
    @PostMapping("/face")
    public Result setFace(@RequestAttribute("uid")Integer uid, MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+suffix;
        File dir = new File(basePath);
        if(!dir.exists()){dir.mkdirs();}
        try {
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        userService.updateFaceById(uid,fileName);
        return Result.success(fileName,"上传成功");
    }
    @GetMapping("/face")
    public Result getFace(String fileName, HttpServletResponse response){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));//读取本地文件
            ServletOutputStream outputStream = response.getOutputStream();//响应的输出流
            response.setContentType("image/jpeg");//设置响应的内容类型为 "image/jpeg"
            int len = 0;
            byte[] bytes = new byte[1024];
            while((len = fileInputStream.read(bytes))!=-1){//循环读取
                outputStream.write(bytes,0,len);//写入
                outputStream.flush();//在每次写入后，通过 flush() 刷新输出流
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success("下载成功");
    }
}

