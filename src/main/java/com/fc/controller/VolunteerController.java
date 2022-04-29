package com.fc.controller;

import com.fc.entity.User;
import com.fc.entity.VolunteerRecruitment;
import com.fc.service.VolunteerService;
import com.fc.vo.ReturnMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @PostMapping("click")
    public ReturnMessageVO click(VolunteerRecruitment volunteerRecruitment){
        return volunteerService.click(volunteerRecruitment.getId(),volunteerRecruitment.getLastClickTime());
    }

    @PostMapping("add")
    public ReturnMessageVO add(@RequestBody VolunteerRecruitment volunteerRecruitment){
        return volunteerService.add(volunteerRecruitment);
    }

    @GetMapping("getlist")
    public ReturnMessageVO getlist(@RequestParam(value = "pageNum"  , defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize" , defaultValue = "3")  Integer pageSize,
                                   Long id) {
        return volunteerService.getList(pageNum, pageSize, id);
    }

    @PostMapping("update")
    public ReturnMessageVO update(@RequestBody VolunteerRecruitment volunteer){

        return volunteerService.update(volunteer);
    }

    @GetMapping("delete")
    public ReturnMessageVO delete(Long id){
        return volunteerService.del(id);
    }
}
