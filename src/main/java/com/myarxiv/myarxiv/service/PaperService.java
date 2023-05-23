package com.myarxiv.myarxiv.service;

import com.myarxiv.myarxiv.domain.Paper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 时之始
* @description 针对表【paper】的数据库操作Service
* @createDate 2023-04-29 22:49:03
*/
public interface PaperService extends IService<Paper> {
    public List<Object> getPaperListByUserId(Integer userId);
}
