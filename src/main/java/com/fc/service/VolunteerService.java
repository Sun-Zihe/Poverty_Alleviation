package com.fc.service;

import com.fc.entity.VolunteerRecruitment;
import com.fc.vo.ReturnMessageVO;

import java.util.Date;

public interface VolunteerService {
    ReturnMessageVO click(Long id, Date getLastClickTime);

    ReturnMessageVO add(VolunteerRecruitment volunteerRecruitment);

    ReturnMessageVO getList(Integer pageNum, Integer pageSize, Long id);

    ReturnMessageVO update(VolunteerRecruitment volunteer);

    ReturnMessageVO del(Long id);
}
