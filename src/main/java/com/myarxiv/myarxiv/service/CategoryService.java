package com.myarxiv.myarxiv.service;

import com.myarxiv.myarxiv.domain.Subject;
import com.myarxiv.myarxiv.pojo.CategoryAddObj;

public interface CategoryService {
    public Object getCategoryBySubjectId(Integer subjectId);
    public Object addCategory(CategoryAddObj categoryAddObj);
    public Object removeCategory(CategoryAddObj categoryAddObj);
    public Object removeSubjectById(Subject subject);
}
