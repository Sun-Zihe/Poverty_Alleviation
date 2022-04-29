package com.fc.service.impl;

import com.fc.dao.PoorMapper;
import com.fc.entity.PoorWithBLOBs;
import com.fc.service.PoorService;
import com.fc.vo.DataVO;
import com.fc.vo.ReturnMessageVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PoorServiceImpl implements PoorService {

    @Autowired
    private PoorMapper poorMapper;

    @Override
    public ReturnMessageVO click(Long id, Date lastClickTime) {
        ReturnMessageVO resultVo;

        if (lastClickTime == null) {
            lastClickTime = new Date();
        }

        Integer affectedRows = poorMapper.click(id, lastClickTime);

        if (affectedRows > 0) {
            resultVo = new ReturnMessageVO(200, "贫困户成功加1点击量", true, null);
        } else {
            resultVo = new ReturnMessageVO(4000, "贫困户加1点击量失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ReturnMessageVO getList(Integer pageNum, Integer pageSize, Long id) {
        // 返回给前端的结果
        ReturnMessageVO resultVO;

        // 分页相关的参数
        DataVO<PoorWithBLOBs> dataVO;

        // 结果中data对应的用户数组
        List<PoorWithBLOBs> poor;

        // 说明传递了id，那就是findById
        if (id != null) {
            poor = new ArrayList<>();

            // 查询
            PoorWithBLOBs poorWithBLOBs = poorMapper.selectByPrimaryKey(id);

            // 没有查到用户的情况
            if (poorWithBLOBs == null) {
                dataVO = new DataVO<>(0L, poor, pageNum, pageSize);

                resultVO = new ReturnMessageVO(4000, "没有这个贫困户!", false, dataVO);
            } else {
                // 如果是查询单个，那么点击量应该加1
                click(poorWithBLOBs.getId(), null);

                // 更新点击的次数
                poorWithBLOBs.setClickNum(poorWithBLOBs.getClickNum() + 1);

                // 查到了用户扔到集合中
                poor.add(poorWithBLOBs);

                dataVO = new DataVO<>(1L, poor, pageNum, pageSize);

                resultVO = new ReturnMessageVO(200, "查到了该贫困户!", true, dataVO);
            }
        } else {
            // 开启分页
            PageHelper.startPage(pageNum, pageSize);

            poor = poorMapper.selectByExampleWithBLOBs(null);

            // 如果数据库是空的，一个人都没查到
            if (poor.size() == 0) {
                dataVO = new DataVO<>(0L, poor, pageNum, pageSize);

                resultVO = new ReturnMessageVO(4100, "数据库中没有贫困户了!", false, dataVO);
                // 查到了
            } else {
                // 封装pageInfo，为了获取总数据量
                PageInfo<PoorWithBLOBs> pageInfo = new PageInfo<>(poor);

                dataVO = new DataVO<>(pageInfo.getTotal(), poor, pageNum, pageSize);

                resultVO = new ReturnMessageVO(200, "贫困户查询成功！！！!", true, dataVO);

            }
        }

        return resultVO;
    }

    @Override
    public ReturnMessageVO add(PoorWithBLOBs poor) {
        ReturnMessageVO resultVo;

        if (poor.getCreateTime() == null) {
            poor.setCreateTime(new Date());
        }

        poor.setClickNum(0);
        poor.setLastClickTime(null);

        int affectedRows = poorMapper.insert(poor);

        if (affectedRows > 0) {
            resultVo = new ReturnMessageVO(200, "贫困户添加成功", true, poor);
        } else {
            resultVo = new ReturnMessageVO(4000, "贫困户添加失败！！", false, null);
        }

        return resultVo;
    }

    @Override
    public ReturnMessageVO update(PoorWithBLOBs poor) {
        ReturnMessageVO resultVo;

        int affectedRows = poorMapper.updateByPrimaryKeyWithBLOBs(poor);

        if (affectedRows > 0) {
            poor  = poorMapper.selectByPrimaryKey(poor.getId());

            resultVo = new ReturnMessageVO(200, "贫困户修改成功", true, poor);
        } else {
            resultVo = new ReturnMessageVO(4000, "贫困户修改失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ReturnMessageVO delete(Long id) {
        ReturnMessageVO resultVo;

        int affectedRows = poorMapper.deleteByPrimaryKey(id);

        if (affectedRows > 0) {
            resultVo = new ReturnMessageVO(200, "贫困户删除成功", true, null);
        } else {
            resultVo = new ReturnMessageVO(4000, "贫困户删除失败！！", false, null);
        }

        return resultVo;
    }
}
