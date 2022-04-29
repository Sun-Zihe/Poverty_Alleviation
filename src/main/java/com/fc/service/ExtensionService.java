package com.fc.service;

import com.fc.entity.User;
import com.fc.vo.ReturnMessageVO;
import org.springframework.web.multipart.MultipartFile;

public interface ExtensionService {

    ReturnMessageVO login(User user);

    ReturnMessageVO upload(MultipartFile upload, String type);

}
