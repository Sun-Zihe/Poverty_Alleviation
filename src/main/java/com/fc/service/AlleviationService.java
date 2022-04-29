package com.fc.service;

import com.fc.entity.Alleviation;
import com.fc.vo.ReturnMessageVO;

import java.util.Date;

public interface AlleviationService {
    ReturnMessageVO click(Long id, Date lastClickTime);

    ReturnMessageVO getList(Integer pageNum, Integer pageSize, Long id);

    ReturnMessageVO add(Alleviation alleviation);

    ReturnMessageVO update(Alleviation alleviation);

    ReturnMessageVO delete(Long id);
}
