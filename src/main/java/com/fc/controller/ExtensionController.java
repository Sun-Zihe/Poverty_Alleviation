package com.fc.controller;

import com.fc.entity.User;
import com.fc.service.ExtensionService;
import com.fc.vo.ReturnMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("extension")
public class ExtensionController {

    @Autowired
    private ExtensionService extensService;

    //登录功能
    @GetMapping("login")
    public ReturnMessageVO login(@RequestBody User user){
        return extensService.login(user);
    }

    //文件上传
    @PostMapping("upload")
    public ReturnMessageVO upload(MultipartFile upload ,@RequestParam String type){
        return extensService.upload(upload,type);
    }



}
