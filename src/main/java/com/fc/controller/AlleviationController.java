package com.fc.controller;

import com.fc.entity.Alleviation;
import com.fc.service.AlleviationService;
import com.fc.vo.ReturnMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("alleviation")
public class AlleviationController {
    @Autowired
    private AlleviationService alleviationService;

    @PostMapping("click")
    public ReturnMessageVO click(Alleviation alleviation) {
        return alleviationService.click(alleviation.getId(), alleviation.getLastClickTime());
    }

    @GetMapping("getlist")
    public ReturnMessageVO getlist(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "3")  Integer pageSize,
                                    Long id) {
        return alleviationService.getList(pageNum, pageSize, id);
    }

    @PostMapping("add")
    public ReturnMessageVO add(@RequestBody Alleviation alleviation) {
        return alleviationService.add(alleviation);
    }

    @PostMapping("update")
    public ReturnMessageVO update(@RequestBody Alleviation alleviation){
        return alleviationService.update(alleviation);
    }

    @GetMapping("delete")
    public ReturnMessageVO delete(@RequestParam Long id) {
        return alleviationService.delete(id);
    }
}
