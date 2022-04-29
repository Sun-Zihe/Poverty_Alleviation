package com.fc.controller;

import com.fc.entity.Poor;
import com.fc.entity.PoorWithBLOBs;
import com.fc.service.PoorService;
import com.fc.vo.ReturnMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("poor")
public class PoorController {
    @Autowired
    private PoorService poorService;

    @PostMapping("click")
    public ReturnMessageVO click(Poor poor) {
        return poorService.click(poor.getId(), poor.getLastClickTime());
    }

    @GetMapping("getlist")
    public ReturnMessageVO getlist(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "3")  Integer pageSize,
                            Long id) {
        return poorService.getList(pageNum, pageSize, id);
    }

    @PostMapping("add")
    public ReturnMessageVO add(@RequestBody PoorWithBLOBs poor) {
        return poorService.add(poor);
    }

    @PostMapping("update")
    public ReturnMessageVO update(@RequestBody PoorWithBLOBs poor){
        return poorService.update(poor);
    }

    @GetMapping("delete")
    public ReturnMessageVO delete(@RequestParam Long id) {
        return poorService.delete(id);
    }
}
