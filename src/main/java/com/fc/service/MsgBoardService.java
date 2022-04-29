package com.fc.service;

import com.fc.entity.MessageBoardWithBLOBs;
import com.fc.vo.ReturnMessageVO;

public interface MsgBoardService {
    ReturnMessageVO add(MessageBoardWithBLOBs msgBoard);

    ReturnMessageVO delete(Long id);

    ReturnMessageVO update(MessageBoardWithBLOBs msgBoard);

    ReturnMessageVO getList(Integer pageNo, Integer pageSize, Long id);
}
