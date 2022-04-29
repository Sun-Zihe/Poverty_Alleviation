package com.fc.service;

import com.fc.entity.User;
import com.fc.vo.ReturnMessageVO;

import java.util.Map;

public interface UserService {
    ReturnMessageVO add(User user);

    ReturnMessageVO getlist(Integer pageNo, Integer pageSize, Long id);

    ReturnMessageVO update(User user);

    ReturnMessageVO del(Long id);

    ReturnMessageVO search(String info, int pageNum, int pageSize);
}
