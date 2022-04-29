package com.fc.service;

import com.fc.entity.Collection;
import com.fc.vo.ReturnMessageVO;

public interface CollectionService {
    ReturnMessageVO add(Collection collection);

    ReturnMessageVO update(Collection collection);

    ReturnMessageVO delete(Long id);

    ReturnMessageVO getList(Integer pageNo, Integer pageSize, Long id);
}
