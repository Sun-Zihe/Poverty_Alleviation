package com.fc.service.impl;

import com.fc.dao.UserMapper;
import com.fc.entity.User;
import com.fc.service.ExtensionService;
import com.fc.vo.ReturnMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExtensionServiceImpl implements ExtensionService {

    @Autowired
    UserMapper userMapper;

    @Override
    public ReturnMessageVO login(User user) {

        ReturnMessageVO vo;

        String username = user.getUsername(),password = user.getPassword();

        int affRow = userMapper.selectByUserParam(username,password);

        //如果没有查到用户
        if(affRow<1){

            vo= new ReturnMessageVO(4404,"登录失败，没有该用户！",false,null);
        }else{
            //如果查到用户

            vo = new ReturnMessageVO(200,"登陆成功！",true,null);
        }

        return vo;
    }

    @Override
    public ReturnMessageVO upload(MultipartFile upload, String type) {

        ReturnMessageVO vo;

        Map<String,Object> map = new HashMap<>();

        //不能用网络路径
        String path = "D:/server/apache-tomcat-8.5.37/webapps/upload/Poverty-Alleviation/"+type;

        //创建文件对象
        File file = new File(path);

        //如果路径不存在
        if (!file.exists()) {
            //创建多级 路径
            file.mkdirs();
        }

        //获取文件名
        String filename = upload.getOriginalFilename();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        filename = dateFormat.format(new Date())+filename.substring(filename.lastIndexOf("."));

        try {

            String imgurl = "http://localhost:8081/upload/Poverty-Alleviation/"+type+"/"+filename;
                map.put("imgurl",imgurl);
                //上传
                upload.transferTo(new File(path, filename));
                vo = new ReturnMessageVO(200,"图片上传成功",true,map);

            //  将图片地址存到数据库中


        } catch (IOException e) {
            e.printStackTrace();

            vo = new ReturnMessageVO(-1,"图片上传失败",false,null);
        }

        return vo;
    }

}
