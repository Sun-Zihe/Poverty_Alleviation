package com.fc.controller;

import com.fc.entity.MessageBoardWithBLOBs;
import com.fc.service.MsgBoardService;
import com.fc.vo.ReturnMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("msgboard")
public class MsgBoardController {
    @Autowired
    private MsgBoardService msgBoardService;

    @PostMapping("add")
    public ReturnMessageVO add(@RequestBody MessageBoardWithBLOBs msgBoard) {
        return msgBoardService.add(msgBoard);
    }

    @GetMapping("delete")
    public ReturnMessageVO delete(@RequestParam Long id) {
        return msgBoardService.delete(id);
    }

    @PostMapping("update")
    public ReturnMessageVO update(@RequestBody MessageBoardWithBLOBs msgBoard) {
        return msgBoardService.update(msgBoard);
    }

    @GetMapping("getlist")
    public ReturnMessageVO getList(@RequestParam(value = "pageNo"   , defaultValue = "1")Integer pageNo,
                                   @RequestParam(value = "pageSize" , defaultValue = "3")Integer pageSize,
                            Long id) {
        return msgBoardService.getList(pageNo, pageSize, id);
    }
}
