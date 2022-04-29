package com.fc.controller;

import com.fc.entity.Carousel;
import com.fc.entity.VolunteerRecruitment;
import com.fc.service.CarouselService;
import com.fc.vo.ReturnMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;

    @PostMapping("state")
    public ReturnMessageVO changeStatus(@RequestParam Integer id) {

        return carouselService.changeStatus(id);
    }

    @GetMapping("getlist")
    public ReturnMessageVO getList(@RequestParam(value = "pageNum" , defaultValue = "1") Integer pageNo,
                                   @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize,
                                   @RequestParam(value = "id",required = false) Integer id){
        return carouselService.getlist(pageNo,pageSize,id);
    }

    @PostMapping("add")
    public ReturnMessageVO add(@RequestBody Carousel carousel){
        return carouselService.add(carousel);
    }

    @PostMapping("update")
    public ReturnMessageVO update(@RequestBody Carousel carousel){

        return carouselService.update(carousel);
    }

    @GetMapping("del")
    public ReturnMessageVO update(Integer id){

        return carouselService.del(id);
    }

}
