package com.myarxiv.myarxiv.service;

import com.myarxiv.myarxiv.domain.Paper;
import com.myarxiv.myarxiv.pojo.PaperRoughly;
import org.springframework.stereotype.Service;

public interface SearchService {

    public Object search(String query,String searchField);
    public Object search(String query,String searchField,Integer pageSize,Integer orderByCode);
    public Object search(String query,String searchField,Integer pageSize,Integer orderByCode,Integer pageNum);

    public Object searchByDetail(String query,String searchField);
    public Object searchByDetail(String query,String searchField,Integer pageSize,Integer orderByCode);
    public Object searchByDetail(String query,String searchField,Integer pageSize,Integer orderByCode,Integer pageNum);

    public Object searchTemplate(String query,String searchField,Integer pageSize,Integer orderByCode,Integer pageNum);
    public Object searchByDetailTemplate(String query,String searchField,Integer pageSize,Integer orderByCode,Integer pageNum);

    public PaperRoughly getPaperRoughly(Paper paper,Integer paperStatus);

}
