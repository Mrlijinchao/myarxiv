package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.domain.CategoryPrimary;
import com.myarxiv.myarxiv.domain.CategorySecondary;
import com.myarxiv.myarxiv.domain.Subject;
import com.myarxiv.myarxiv.domain.relation.CatePriSec;
import com.myarxiv.myarxiv.domain.relation.SubjectCategory;
import com.myarxiv.myarxiv.mapper.CategoryPrimaryMapper;
import com.myarxiv.myarxiv.mapper.CategorySecondaryMapper;
import com.myarxiv.myarxiv.mapper.SubjectMapper;
import com.myarxiv.myarxiv.mapper.relation.CatePriSecMapper;
import com.myarxiv.myarxiv.mapper.relation.SubjectCategoryMapper;
import com.myarxiv.myarxiv.pojo.CategoryAddObj;
import com.myarxiv.myarxiv.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryPrimaryMapper categoryPrimaryMapper;

    @Resource
    private CategorySecondaryMapper categorySecondaryMapper;

    @Resource
    private SubjectCategoryMapper subjectCategoryMapper;

    @Resource
    private CatePriSecMapper catePriSecMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public Object getCategoryBySubjectId(Integer subjectId) {

        LambdaQueryWrapper<SubjectCategory> subjectCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        subjectCategoryLambdaQueryWrapper.eq(SubjectCategory::getSubjectId,subjectId);
        List<SubjectCategory> subjectCategoryList = subjectCategoryMapper.selectList(subjectCategoryLambdaQueryWrapper);
        ArrayList<Object> list = new ArrayList<>();
        for(SubjectCategory sc : subjectCategoryList){
            LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
            CategoryPrimary categoryPrimary = categoryPrimaryMapper.selectById(sc.getCategoryPrimaryId());
            LambdaQueryWrapper<CatePriSec> catePriSecLambdaQueryWrapper = new LambdaQueryWrapper<>();
            catePriSecLambdaQueryWrapper.eq(CatePriSec::getCategoryPrimaryId,categoryPrimary.getCategoryPrimaryId());
            List<CatePriSec> catePriSecList = catePriSecMapper.selectList(catePriSecLambdaQueryWrapper);
            ArrayList<Integer> secondaryIdList = new ArrayList<>();
            for(CatePriSec cp : catePriSecList){
                secondaryIdList.add(cp.getCategorySecondaryId());
            }

            List<CategorySecondary> categorySecondaryList = categorySecondaryMapper.selectBatchIds(secondaryIdList);
            hashMap.put("categoryPrimaryId",categoryPrimary.getCategoryPrimaryId());
            hashMap.put("categoryPrimaryName",categoryPrimary.getCategoryPrimaryName());
            hashMap.put("categorySecondaryList",categorySecondaryList);
            list.add(hashMap);
        }

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("categoryPrimaryList",list);

        return ResponseResult.success(map);
    }

    @Transactional
    @Override
    public Object addCategory(CategoryAddObj categoryAddObj) {
        if(categoryAddObj.getCategoryStr() == null){
            return ResponseResult.fail("收到的数据为null", StatusCode.ERROR.getCode());
        }
        String[] split = categoryAddObj.getCategoryStr().split(",");
        if (split.length == 2){
            LambdaQueryWrapper<CategorySecondary> categorySecondaryLambdaQueryWrapper = new LambdaQueryWrapper<>();
            categorySecondaryLambdaQueryWrapper.eq(CategorySecondary::getCategorySecondaryName,categoryAddObj.getText());
            List<CategorySecondary> categorySecondaries = categorySecondaryMapper.selectList(categorySecondaryLambdaQueryWrapper);
            if (!categorySecondaries.isEmpty()){
                return ResponseResult.fail("这个分类已存在！",StatusCode.ERROR.getCode());
            }
            // 插入一个二级分类
            CategorySecondary categorySecondary = new CategorySecondary();
            categorySecondary.setCategorySecondaryName(categoryAddObj.getText());
            categorySecondaryMapper.insert(categorySecondary);
            // 关联二级分类对应的一级分类
            CatePriSec catePriSec = new CatePriSec();
            catePriSec.setCategoryPrimaryId(Integer.parseInt(split[split.length - 1]));
            catePriSec.setCategorySecondaryId(categorySecondary.getCategorySecondaryId());
            catePriSecMapper.insert(catePriSec);
            return ResponseResult.success(categorySecondary);
        }else{
            LambdaQueryWrapper<CategoryPrimary> categoryPrimaryLambdaQueryWrapper = new LambdaQueryWrapper<>();
            categoryPrimaryLambdaQueryWrapper.eq(CategoryPrimary::getCategoryPrimaryName,categoryAddObj.getText());
            List<CategoryPrimary> categoryPrimaries = categoryPrimaryMapper.selectList(categoryPrimaryLambdaQueryWrapper);
            if (!categoryPrimaries.isEmpty()){
                return ResponseResult.fail("这个分类已存在！",StatusCode.ERROR.getCode());
            }
            // 插入一个一级分类
            CategoryPrimary categoryPrimary = new CategoryPrimary();
            categoryPrimary.setCategoryPrimaryName(categoryAddObj.getText());
            categoryPrimaryMapper.insert(categoryPrimary);
            // 关联一级分类对应的主题
            SubjectCategory subjectCategory = new SubjectCategory();
            subjectCategory.setSubjectId(Integer.parseInt(split[split.length - 1]));
            subjectCategory.setCategoryPrimaryId(categoryPrimary.getCategoryPrimaryId());
            subjectCategoryMapper.insert(subjectCategory);
            return ResponseResult.success(categoryPrimary);
        }
    }

    @Transactional
    @Override
    public Object removeCategory(CategoryAddObj categoryAddObj) {
        if(categoryAddObj.getCategoryStr() == null){
            return ResponseResult.fail("收到的数据为null", StatusCode.ERROR.getCode());
        }
        String[] split = categoryAddObj.getCategoryStr().split(",");
        // 删除一级分类和它所关联的信息
        if (split.length == 2){
            LambdaUpdateWrapper<SubjectCategory> subjectCategoryLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            subjectCategoryLambdaUpdateWrapper.eq(SubjectCategory::getCategoryPrimaryId,Integer.parseInt(split[split.length - 1]));
            subjectCategoryMapper.delete(subjectCategoryLambdaUpdateWrapper);

            categoryPrimaryMapper.deleteById(Integer.parseInt(split[split.length - 1]));

            LambdaQueryWrapper<CatePriSec> categoryServiceLambdaQueryWrapper = new LambdaQueryWrapper<>();
            categoryServiceLambdaQueryWrapper.eq(CatePriSec::getCategoryPrimaryId,Integer.parseInt(split[split.length - 1]));
            List<CatePriSec> catePriSecList = catePriSecMapper.selectList(categoryServiceLambdaQueryWrapper);

            if(catePriSecList.isEmpty()){
                return ResponseResult.success("删除成功！");
            }

            ArrayList<Integer> secondaryIdList = new ArrayList<>();
            for(CatePriSec cp : catePriSecList){
                secondaryIdList.add(cp.getCategorySecondaryId());
            }

            catePriSecMapper.delete(categoryServiceLambdaQueryWrapper);

            categorySecondaryMapper.deleteBatchIds(secondaryIdList);

            return ResponseResult.success("删除成功！");

        }else if(split.length == 3){//删除二级分类和它所关联的信息

            LambdaQueryWrapper<CatePriSec> catePriSecLambdaQueryWrapper = new LambdaQueryWrapper<>();
            catePriSecLambdaQueryWrapper.eq(CatePriSec::getCategorySecondaryId,Integer.parseInt(split[split.length - 1]));
            catePriSecMapper.delete(catePriSecLambdaQueryWrapper);

            categorySecondaryMapper.deleteById(Integer.parseInt(split[split.length - 1]));

            return ResponseResult.success("删除成功！");
        }


        return ResponseResult.fail("参数不对",StatusCode.ERROR.getCode());
    }

    @Transactional
    @Override
    public Object removeSubjectById(Subject subject) {

        int re = subjectMapper.deleteById(subject);
        if(re == 0){
            return ResponseResult.fail("这个ID对应的学科不存在",StatusCode.ERROR.getCode());
        }


        LambdaQueryWrapper<SubjectCategory> subjectCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        subjectCategoryLambdaQueryWrapper.eq(SubjectCategory::getSubjectId,subject.getSubjectId());
        List<SubjectCategory> subjectCategoryList = subjectCategoryMapper.selectList(subjectCategoryLambdaQueryWrapper);

        if(subjectCategoryList.isEmpty()){
            return ResponseResult.success("学科删除成功！");
        }

        subjectCategoryMapper.delete(subjectCategoryLambdaQueryWrapper);

        for(SubjectCategory sc : subjectCategoryList){
            LambdaQueryWrapper<CatePriSec> catePriSecLambdaQueryWrapper = new LambdaQueryWrapper<>();
            catePriSecLambdaQueryWrapper.eq(CatePriSec::getCategoryPrimaryId,sc.getCategoryPrimaryId());
            List<CatePriSec> catePriSecList = catePriSecMapper.selectList(catePriSecLambdaQueryWrapper);
            categoryPrimaryMapper.deleteById(sc.getCategoryPrimaryId());

            ArrayList<Integer> secondaryIdList = new ArrayList<>();
            for(CatePriSec cp : catePriSecList){
                secondaryIdList.add(cp.getCategorySecondaryId());
            }
            categorySecondaryMapper.deleteBatchIds(secondaryIdList);

        }

        return ResponseResult.success("已清除这个学科以及所有与这个学科相关的分类信息");
    }
}
