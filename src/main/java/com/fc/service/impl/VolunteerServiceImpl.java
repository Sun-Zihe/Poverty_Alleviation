package com.fc.service.impl;

import com.fc.dao.VolunteerRecruitmentMapper;
import com.fc.entity.User;
import com.fc.entity.VolunteerRecruitment;
import com.fc.service.VolunteerService;
import com.fc.vo.DataVO;
import com.fc.vo.ReturnMessageVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    VolunteerRecruitmentMapper volunteerMapper;

    @Override
    public ReturnMessageVO click(Long id, Date getLastClickTime) {

        ReturnMessageVO vo;

        Map<String,Object> map=null;

        if (getLastClickTime == null){
            getLastClickTime = new Date();
        }

        int affRow =  volunteerMapper.click(id,getLastClickTime);

        if (affRow>0){
            vo=new ReturnMessageVO(200,"志愿者点击量增加！",true,null);
        }else {
            map.put("errMsg","志愿者id参数错误，请核对后再次点击，如还有疑问请联系管理员！！");

            vo=new ReturnMessageVO(4000,"志愿者点击量增加失败！！",false,map);
        }

        return vo;
    }

    @Override
    public ReturnMessageVO add(VolunteerRecruitment volunteerRecruitment) {
        ReturnMessageVO vo;

        Map<String,Object> map=null;

        int affRow = volunteerMapper.insertSelective(volunteerRecruitment);

        if (affRow>0){
            vo = new ReturnMessageVO(200,"志愿者添加成功！！",true,null);
        }else {
            map.put("errMsg" , "用户的必填参数未添加，请添加后再次尝试");

            vo = new ReturnMessageVO(4000,"志愿者添加失败！！",false,map);
        }

        return vo;
    }

    @Override
    public ReturnMessageVO getList(Integer pageNo, Integer pageSize, Long id) {

        //返回给前端的结果
        ReturnMessageVO returnMessageVO = null;

        //分页相关的参数
        DataVO<VolunteerRecruitment> userDataVO;


        //使用Mybatis中的插件，传递页数和条数
        PageHelper.startPage(pageNo,pageSize);

        //用来存储用户对象的集合
        List<VolunteerRecruitment> volunteers ;

        //id为空查全部，不为空查单个
        if(id!=null){

            volunteers = new ArrayList<>();

            VolunteerRecruitment volunteer = volunteerMapper.selectByPrimaryKey(id);

            //如果没有查到用户
            if(volunteer == null){
                userDataVO = new DataVO<>(0L,volunteers, pageNo, pageSize);

                returnMessageVO = new ReturnMessageVO(4404,"没有该用户！",false,userDataVO);
            }else{

                volunteers.add(volunteer);

                userDataVO = new DataVO<>(1L,volunteers, pageNo, pageSize);

                returnMessageVO = new ReturnMessageVO(200,"成功查到该用户！",true,userDataVO);
            }
        }else {
            //查询全部

            //开启分页
            PageHelper.startPage(pageNo,pageSize);

            volunteers = volunteerMapper.selectByExampleWithBLOBs(null);

            //没查到，如果数据库是空的，一个人都没有
            if (volunteers.size() == 0){
                userDataVO = new DataVO<>(0L,volunteers, pageNo, pageSize);

                returnMessageVO = new ReturnMessageVO(4405,"数据库中没有任何志愿者！",false,userDataVO);
            }else{
                //封装pageINfo,获取数据量
                PageInfo<VolunteerRecruitment> userPageInfo = new PageInfo<>(volunteers);

                userDataVO = new DataVO<>(userPageInfo.getTotal(),volunteers,pageNo,pageSize);

                returnMessageVO = new ReturnMessageVO(200,"志愿者查询成功！！",true,userDataVO);

            }

        }

        return returnMessageVO;
    }

    @Override
    public ReturnMessageVO update(VolunteerRecruitment volunteer) {

        ReturnMessageVO vo;

        Map<String,Object> map = null;

        int affRow =  volunteerMapper.updateByPrimaryKeySelective(volunteer);

        if (affRow>0){

            // 修改完成之后，再重新查询一次，保证返回给前端的是最新最全的数据
            volunteer = volunteerMapper.selectByPrimaryKey( volunteer.getId());

            vo = new ReturnMessageVO(200,"用户更新成功",true,volunteer);

        }else{
            map.put("errMsg", "必要参数为空");

            vo = new ReturnMessageVO(4000,"用户更新失败",false,map);

        }


        return vo;
    }

    @Override
    public ReturnMessageVO del(Long id) {

        ReturnMessageVO vo;

        Map<String,Object> map=null;

        int affRow = 0 ;

        try{
            affRow = volunteerMapper.deleteByPrimaryKey(id);
        }catch (NullPointerException e) {
            map.put("Error", e);
        }



        if (affRow>0){
            vo = new ReturnMessageVO(200,"志愿者删除成功",true,map);
        }else {
            map.put("errMsg","缺少必要参数或数据库中没有该志愿者的信息");
            vo = new ReturnMessageVO(4000,"志愿者删除失败",false,map);
        }

        return vo;
    }
}
