package com.myarxiv.myarxiv.service;

import com.myarxiv.myarxiv.domain.Subject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 时之始
* @description 针对表【subject】的数据库操作Service
* @createDate 2023-04-22 13:09:30
*/
public interface SubjectService extends IService<Subject> {

    public Object getAllSubjectAndCategory();

}
