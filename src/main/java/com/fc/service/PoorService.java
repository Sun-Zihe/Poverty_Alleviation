package com.fc.service;

import com.fc.entity.PoorWithBLOBs;
import com.fc.vo.ReturnMessageVO;

import java.util.Date;

public interface PoorService {
    ReturnMessageVO click(Long id, Date lastClickTime);

    ReturnMessageVO getList(Integer pageNum, Integer pageSize, Long id);

    ReturnMessageVO add(PoorWithBLOBs poor);

    ReturnMessageVO update(PoorWithBLOBs poor);

    ReturnMessageVO delete(Long id);
}
