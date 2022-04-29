package com.fc.service;

import com.fc.entity.Carousel;
import com.fc.vo.ReturnMessageVO;
import org.springframework.web.bind.annotation.RequestMapping;

public interface CarouselService {
    

    ReturnMessageVO changeStatus(Integer id);

    ReturnMessageVO getlist(Integer pageNo, Integer pageSize, Integer id);

    ReturnMessageVO add(Carousel carousel);

    ReturnMessageVO update(Carousel carousel);

    ReturnMessageVO del(Integer id);
}
