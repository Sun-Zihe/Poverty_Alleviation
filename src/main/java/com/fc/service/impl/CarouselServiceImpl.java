package com.fc.service.impl;

import com.fc.dao.CarouselMapper;
import com.fc.entity.Carousel;
import com.fc.entity.CarouselExample;
import com.fc.entity.Collection;
import com.fc.entity.User;
import com.fc.service.CarouselService;
import com.fc.vo.DataVO;
import com.fc.vo.ReturnMessageVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public ReturnMessageVO changeStatus(Integer id) {

        ReturnMessageVO vo;

        Map<String,Object> map = null;

        int affRow = carouselMapper.changeStatus(id);

        if (affRow>0){
            vo = new ReturnMessageVO(2000,"图片状态已修改成功",true,map);
        }else {

            map.put("errMsg","图片信息可能有误,请联系管理员");

            vo = new ReturnMessageVO(2000,"图片状态修改失败",true,map);
        }

        return vo;
    }

    @Override
    public ReturnMessageVO getlist(Integer pageNo, Integer pageSize, Integer id) {


        //返回给前端的结果
        ReturnMessageVO returnMessageVO = null;

        //分页相关的参数
        DataVO<Carousel> userDataVO;


        //使用Mybatis中的插件，传递页数和条数
        PageHelper.startPage(pageNo,pageSize);

        //用来存储用户对象的集合
        List<Carousel> List ;

        //id为空查全部，不为空查单个
        if(id!=null){

            List = new ArrayList<>();

            Carousel carousel = carouselMapper.selectByPrimaryKey(id);



            //如果没有查到用户
            if(carousel == null){
                userDataVO = new DataVO<>(0L,List, pageNo, pageSize);

                returnMessageVO = new ReturnMessageVO(4404,"没有该轮播图！",false,userDataVO);
            }else{

               List.add(carousel);

                userDataVO = new DataVO<>(1L,List, pageNo, pageSize);

                returnMessageVO = new ReturnMessageVO(2200,"成功查到该轮播图！",true,userDataVO);
            }
        }else {
            //查询全部

            //开启分页
            PageHelper.startPage(pageNo,pageSize);

            List = carouselMapper.selectByExample(null);

            //没查到，如果数据库是空的，一个人都没有
            if (List.size() == 0){
                userDataVO = new DataVO<>(0L,List, pageNo, pageSize);

                returnMessageVO = new ReturnMessageVO(4405,"数据库中没有轮播图！",false,userDataVO);
            }else{
                //封装pageINfo,获取数据量
                PageInfo<Carousel> userPageInfo = new PageInfo<>(List);

                userDataVO = new DataVO<>(userPageInfo.getTotal(),List,pageNo,pageSize);

                returnMessageVO = new ReturnMessageVO(2200,"轮播图查询成功！！",true,userDataVO);

            }

        }

        return returnMessageVO;

    }

    @Override
    public ReturnMessageVO add(Carousel carousel) {

        ReturnMessageVO vo;

        if (carousel.getAvailable()==null){
            carousel.setAvailable(false);
        }

        int affRow = carouselMapper.insertSelective(carousel);

        if (affRow>0){

            vo = new ReturnMessageVO(2000,"图片添加成功",true,carousel);
        }else {
            vo = new ReturnMessageVO(4000,"图片添加失败",false,null);
        }

        return vo;
    }

    @Override
    public ReturnMessageVO update(Carousel carousel) {

        int affectedRows = carouselMapper.updateByPrimaryKeySelective(carousel);

        ReturnMessageVO vo;

        if (affectedRows > 0) {
            // 修改完成之后，再重新查询一次，保证返回给前端的是最新最全的数据
            carousel = carouselMapper.selectByPrimaryKey(carousel.getId());

            vo = new ReturnMessageVO(1000, "修改图片成功！！", true, carousel);
        } else {
            vo = new ReturnMessageVO(5000, "修改图片失败！！", false, null);
        }

        return vo;


    }

    @Override
    public ReturnMessageVO del(Integer id) {
        int affectedRows = carouselMapper.deleteByPrimaryKey(id);

        ReturnMessageVO vo;

        if (affectedRows > 0) {
            vo = new ReturnMessageVO(1000, "删除图片成功！！", true, null);
        } else {
            vo = new ReturnMessageVO(5000, "删除图片失败！！", false, null);
        }

        return vo;
    }
}
