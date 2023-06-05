package com.myarxiv.myarxiv.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myarxiv.myarxiv.common.DefaultPageParam;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.domain.Paper;
import com.myarxiv.myarxiv.domain.relation.PaperCategory;
import com.myarxiv.myarxiv.mapper.PaperMapper;
import com.myarxiv.myarxiv.mapper.relation.PaperCategoryMapper;
import com.myarxiv.myarxiv.pojo.GetPaper;
import com.myarxiv.myarxiv.service.HomeService;
import com.myarxiv.myarxiv.service.PaperService;
import com.myarxiv.myarxiv.service.SubjectService;
import com.myarxiv.myarxiv.service.relation.PaperCategoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private SubjectService subjectService;

    @Resource
    private HomeService homeService;

    @GetMapping("/getHomeInfo")
    public Object getHomeInfo(){

        Object allSubjectAndCategory = subjectService.getAllSubjectAndCategory();

        return ResponseResult.success(allSubjectAndCategory);
    }

    @GetMapping("/getPapers")
    public Object getPapers(GetPaper getPaper){
        if(getPaper.getCategorySecondaryId() == null){
            return ResponseResult.fail("请带上CategorySecondaryId",StatusCode.ERROR.getCode());
        }
        if(getPaper.getPageNum() == null && getPaper.getPageSize() == null){
            return homeService.getPapersOfCategoryTemplate(getPaper.getCategorySecondaryId(), DefaultPageParam.DEFAULT_PAGE_SIZE.getParam(),
                    DefaultPageParam.DEFAULT_PAGE_NUM.getParam());
        }
        if (getPaper.getPageSize() != null && getPaper.getPageNum() == null){
            return homeService.getPapersOfCategoryTemplate(getPaper.getCategorySecondaryId(),
                    getPaper.getPageSize(),DefaultPageParam.DEFAULT_PAGE_NUM.getParam());
        }

        if(getPaper.getPageNum() != null && getPaper.getPageSize() == null){
            return homeService.getPapersOfCategoryTemplate(getPaper.getCategorySecondaryId(),
                    DefaultPageParam.DEFAULT_PAGE_SIZE.getParam(), getPaper.getPageNum());
        }

        return homeService.getPapersOfCategoryTemplate(getPaper.getCategorySecondaryId(),getPaper.getPageSize(),getPaper.getPageNum());

    }




}
