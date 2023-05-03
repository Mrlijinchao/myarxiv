package com.myarxiv.myarxiv.controller;

import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.common.field.FieldMap;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/search")
public class SearchController {

    private LinkedHashMap<String, String> fieldMap = FieldMap.getFieldMap();
    private LinkedHashMap<String,String> detailFieldMap = FieldMap.getDetailFieldMap();

    @Resource
    private SearchService searchService;

    @GetMapping("/search1")
    public Object search(String query,String searchType){
        String field = detailFieldMap.get(searchType);
        if(field != null){
            return searchService.searchByDetail(query, field);
        }
        field = fieldMap.get(searchType);
        if(field == null){
            return ResponseResult.fail("不支持该类型的查询！", StatusCode.ERROR.getCode());
        }
        return searchService.search(query,field);
    }

    @GetMapping("/search2")
    public Object search(String query,String searchType,Integer pageSize,Integer orderByCode){
        String field = detailFieldMap.get(searchType);
        if(field != null){
            return searchService.searchByDetail(query, field,pageSize,orderByCode);
        }
        field = fieldMap.get(searchType);
        if(field == null){
            return ResponseResult.fail("不支持该类型的查询！", StatusCode.ERROR.getCode());
        }
        return searchService.search(query,field,pageSize,orderByCode);
    }

    @GetMapping("/search3")
    public Object search(String query,String searchType,Integer pageSize,Integer orderByCode,Integer pageNum){
        String field = detailFieldMap.get(searchType);
        if(field != null){
            return searchService.searchByDetail(query, field,pageSize,orderByCode,pageNum);
        }
        field = fieldMap.get(searchType);
        if(field == null){
            return ResponseResult.fail("不支持该类型的查询！", StatusCode.ERROR.getCode());
        }
        return searchService.search(query,field,pageSize,orderByCode,pageNum);
    }


}
