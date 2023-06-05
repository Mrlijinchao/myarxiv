package com.myarxiv.myarxiv.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.domain.Subject;
import com.myarxiv.myarxiv.pojo.CategoryAddObj;

import com.myarxiv.myarxiv.service.CategoryService;

import com.myarxiv.myarxiv.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private SubjectService subjectService;

    @GetMapping("/getCategoryBySubjectId")
    public Object getCategoryBySubjectId(Integer subjectId){

        return categoryService.getCategoryBySubjectId(subjectId);
    }

    @PostMapping("/addCategory")
    public Object addCategory(@RequestBody CategoryAddObj categoryAddObj){
        log.info("categoryAddObj: {}",categoryAddObj);

        return categoryService.addCategory(categoryAddObj);
    }

    @PostMapping("/removeCategory")
    public Object removeCategory(@RequestBody CategoryAddObj categoryAddObj){
        log.info("categoryAddObj: {}",categoryAddObj);
        if (categoryAddObj.getCategoryStr().length() == 1){
            return ResponseResult.fail("不能在这个接口删除学科", StatusCode.ERROR.getCode());
        }
        return categoryService.removeCategory(categoryAddObj);
    }

    @PostMapping("/addSubject")
    public Object addSubject(@RequestBody Subject subject){
        log.info("subject: {}", subject);

        if(subject == null){
            return ResponseResult.fail("参数不对",StatusCode.ERROR.getCode());
        }

        LambdaQueryWrapper<Subject> subjectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        subjectLambdaQueryWrapper.eq(Subject::getSubjectName, subject.getSubjectName());
        List<Subject> list = subjectService.list(subjectLambdaQueryWrapper);
        if(!list.isEmpty()){
            return ResponseResult.fail("这个学科的名称已存在，请重新换一个！",StatusCode.ERROR.getCode());
        }
        subject.setSubjectCreateTime(new Date());
        boolean save = subjectService.save(subject);
        if (save){
            return ResponseResult.success("创建学科成功！");
        }

        return ResponseResult.success("创建学科失败！");
    }

    @PostMapping("/removeSubject")
    public Object removeSubject(@RequestBody Subject subject){
        if(subject.getSubjectId() == null){
            return ResponseResult.fail("参数为null",StatusCode.ERROR.getCode());
        }
        return ResponseResult.fail("暂不开放该接口！",StatusCode.ERROR.getCode());
//        return categoryService.removeSubjectById(subject);
    }

    @GetMapping("/getSubjectList")
    public Object getSubjectList(){
        return ResponseResult.success(subjectService.list());
    }

    @PostMapping("/editSubjectInfo")
    public Object editSubjectInfo(@RequestBody Subject subject){
        if(subject.getSubjectId() == null){
            return ResponseResult.fail("参数为null",StatusCode.ERROR.getCode());
        }
        subjectService.updateById(subject);
        return ResponseResult.success("修改学科信息成功");
    }

}
