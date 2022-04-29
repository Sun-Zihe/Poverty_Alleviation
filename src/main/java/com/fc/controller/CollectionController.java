package com.fc.controller;

import com.fc.entity.Collection;
import com.fc.service.CollectionService;
import com.fc.vo.ReturnMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @PostMapping("add")
    public ReturnMessageVO add(@RequestBody Collection collection) {
        return collectionService.add(collection);
    }

    @PostMapping("update")
    public ReturnMessageVO update(@RequestBody Collection collection) {
        return collectionService.update(collection);
    }

    @GetMapping("delete")
    public ReturnMessageVO delete(@RequestParam Long id) {
        return collectionService.delete(id);
    }

    @GetMapping("getlist")
    public ReturnMessageVO getList(@RequestParam(value = "pageNo" , defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize,
                            Long id) {
        return collectionService.getList(pageNo,pageSize,id);
    }
}
