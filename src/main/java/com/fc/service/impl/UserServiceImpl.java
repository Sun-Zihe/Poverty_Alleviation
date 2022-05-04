package com.fc.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fc.dao.UserMapper;
import com.fc.entity.User;
import com.fc.entity.UserExample;
import com.fc.entity.com.fc.error.Data;
import com.fc.service.UserService;
import com.fc.vo.DataVO;
import com.fc.vo.ReturnMessageVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ReturnMessageVO add(User user) {

        ReturnMessageVO vo;
        // 判断是否存在创建时间，没有就自己加上
        if (user.getCreateTime() == null) {
            user.setCreateTime(new Date());
        }

        int affectedRows = userMapper.insertSelective(user);

        Map<String, Object> map = new HashMap<>();

        if (affectedRows > 0) {
            vo = new ReturnMessageVO(200, "添加用户成功！！", true, user);
        } else {
            vo = new ReturnMessageVO(4404, "添加用户失败！！", false, null);
        }

        return vo;
    }

    @Override
    public ReturnMessageVO getlist(Integer pageNo, Integer pageSize, Long id) {

        //返回给前端的结果
        ReturnMessageVO returnMessageVO = null;

        //分页相关的参数
        DataVO<User> userDataVO;


        //使用Mybatis中的插件，传递页数和条数
        PageHelper.startPage(pageNo,pageSize);

        //用来存储用户对象的集合
        List<User> userList ;

        //id为空查全部，不为空查单个
        if(id!=null){

            userList = new ArrayList<>();

            User user = userMapper.selectByPrimaryKey(id);

            //如果没有查到用户
            if(user == null){
                userDataVO = new DataVO<>(0L,userList, pageNo, pageSize);

                returnMessageVO = new ReturnMessageVO(4404,"没有该用户！",false,userDataVO);
            }else{

                userList.add(user);

                userDataVO = new DataVO<>(1L,userList, pageNo, pageSize);

                returnMessageVO = new ReturnMessageVO(200,"成功查到该用户！",true,userDataVO);
            }
        }else {
            //查询全部

            //开启分页
            PageHelper.startPage(pageNo,pageSize);

            userList = userMapper.selectByExample(null);

            //没查到，如果数据库是空的，一个人都没有
            if (userList.size() == 0){
                userDataVO = new DataVO<>(0L,userList, pageNo, pageSize);

                returnMessageVO = new ReturnMessageVO(4405,"数据库中没有用户！",false,userDataVO);
            }else{
                //封装pageINfo,获取数据量
                PageInfo<User> userPageInfo = new PageInfo<>(userList);

                userDataVO = new DataVO<>(userPageInfo.getTotal(),userList,pageNo,pageSize);

                returnMessageVO = new ReturnMessageVO(200,"用户查询成功！！",true,userDataVO);

            }

        }

        return returnMessageVO;

    }

    @Override
    public ReturnMessageVO update(User user) {
        int affectedRows = userMapper.updateByPrimaryKeySelective(user);

        ReturnMessageVO returnMessageVO;

        if (affectedRows>0){

            User users = userMapper.selectByPrimaryKey(user.getId());

            returnMessageVO=new ReturnMessageVO(200,"用户修改成功！！！",true,users);
        }else {
            returnMessageVO=new ReturnMessageVO(4000,"用户修改失败！！！",false,null);
        }

        return returnMessageVO;
    }

    @Override
    public ReturnMessageVO del(Long id) {

        ReturnMessageVO vo;

        int affectedRows = userMapper.deleteByPrimaryKey(id);

        if (affectedRows>0){
            vo=new ReturnMessageVO(200,"用户删除成功！！",true,null);
        }else {

            String  errMsg = "用户id参数错误，请核对后再次删除，如还有疑问请联系管理员！！";

            vo=new ReturnMessageVO(4000,"用户删除失败！！",false,errMsg);
        }


        return vo;
    }

    @Override
    public ReturnMessageVO search(String name, int pageNum, int pageSize) {

        ReturnMessageVO vo = null;

        List<User> userList;
        //分页相关的参数
        DataVO<User> userDataVO;

        //开启分页
        PageHelper.startPage(pageNum,pageSize);

        if (name==null){
            userList = userMapper.selectByExample(null);
            //没查到，如果数据库是空的，一个人都没有
            if (userList.size() == 0){
                userDataVO = new DataVO<>(0L,userList, pageNum, pageSize);

                vo = new ReturnMessageVO(4405,"数据库中没有用户！",false,userDataVO);
            }else{
                //封装pageINfo,获取数据量
                PageInfo<User> userPageInfo = new PageInfo<>(userList);

                userDataVO = new DataVO<>(userPageInfo.getTotal(),userList,pageNum,pageSize);

                vo = new ReturnMessageVO(200,"用户查询成功！！",true,userDataVO);

            }

        }else {
            userList = userMapper.selectByUserName("%"+name+"%");

            if (userList.size() == 0){
                userDataVO = new DataVO<>(0L,userList, pageNum, pageSize);

                vo = new ReturnMessageVO(4405,"数据库中没有用户！",false,userDataVO);
            }else{
                //封装pageINfo,获取数据量
                PageInfo<User> userPageInfo = new PageInfo<>(userList);

                userDataVO = new DataVO<>(userPageInfo.getTotal(),userList,pageNum,pageSize);

                vo = new ReturnMessageVO(200,"用户查询成功！！",true,userDataVO);

            }
        }

        return vo;
    }

    @Override
    public ReturnMessageVO login(String username, String password) {

       ReturnMessageVO vo = new ReturnMessageVO();

        vo.setCode(-1);
        vo.setMessage("登录失败，当前用户名不存在");
        vo.setSuccess(false);
        vo.setData(null);

        UserExample example = new UserExample();

        UserExample.Criteria criteria = example.createCriteria();

        criteria.andUsernameEqualTo(username);

        List<User> users = userMapper.selectByExample(example);

        //如果能查出来说明密码相同
        if (users.size()>0){

            User user = users.get(0);

            // 如果密码相同
            if (user.getPassword().equals(password)) {
            vo.setSuccess(true);
            vo.setMessage("登录成功！");
            vo.setCode(200);

            // 密码不要传给前端
            user.setPassword(null);

            // token - json web token

            // 盐值的存储应该存到缓存服务器中-Redis
            // 可以把盐值作为载荷直接发送给前端，每次我们获取到token后对其进行解析
            // 其实这是很不安全的，但是没办法，暂时这样用
            // 盐值
            String salt = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

                Map<String, Object> headers = new HashMap<>();

                headers.put("alg", "HS256");
                headers.put("typ", "JWT");

                // 获取token
                String token = JWT.create()
                        .withHeader(headers)
                        // 主题
                        .withSubject("登录权限验证")
                        // 签发人
                        .withIssuer("admin")
                        // 签发日期
                        .withIssuedAt(new Date())
                        // 过期时间
                        // 设置稍微长一点，后面有机会讲token续签的时候再改
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                        .withClaim("id", user.getId())
                        .withClaim("username", username)
                        .withClaim("salt", salt) //权限.withClaim("role", user.getRole())
                        // 使用盐值进行签发生成jwt
                        .sign(Algorithm.HMAC256(salt));


                Map<String, Object> result = new HashMap<>();

                result.put("user", user);
                result.put("token", token);

                vo.setData(result);
            } else {
                vo.setCode(-2);
                vo.setMessage("登录失败，请输入正确的密码");
            }
        }

        return vo;
    }
}
