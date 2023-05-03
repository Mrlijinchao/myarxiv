package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myarxiv.myarxiv.domain.CategoryPrimary;
import com.myarxiv.myarxiv.domain.CategorySecondary;
import com.myarxiv.myarxiv.domain.Subject;
import com.myarxiv.myarxiv.domain.relation.CatePriSec;
import com.myarxiv.myarxiv.domain.relation.SubjectCategory;
import com.myarxiv.myarxiv.mapper.CategoryPrimaryMapper;
import com.myarxiv.myarxiv.mapper.CategorySecondaryMapper;
import com.myarxiv.myarxiv.mapper.relation.CatePriSecMapper;
import com.myarxiv.myarxiv.mapper.relation.SubjectCategoryMapper;
import com.myarxiv.myarxiv.service.SubjectService;
import com.myarxiv.myarxiv.mapper.SubjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
* @author 时之始
* @description 针对表【subject】的数据库操作Service实现
* @createDate 2023-04-22 13:09:30
*/
@Transactional
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject>
    implements SubjectService{

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private CategoryPrimaryMapper categoryPrimaryMapper;

    @Resource
    private SubjectCategoryMapper subjectCategoryMapper;

    @Resource
    private CategorySecondaryMapper categorySecondaryMapper;

    @Resource
    private CatePriSecMapper catePriSecMapper;

    @Override
    public Object getAllSubjectAndCategory() {
        // 先查询所有的subject
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        List<Subject> subjectList = subjectMapper.selectList(subjectQueryWrapper);

        List<LinkedHashMap<String, Object>> subjectList1 = new ArrayList<>();

        for(int i = 0; i < subjectList.size(); i++){

            LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();

            QueryWrapper<SubjectCategory> subjectCategoryQueryWrapper = new QueryWrapper<>();
            // 根据 subject_id 从 subjectCategory 表里拿到 categoryPrimary 的所有 id
            subjectCategoryQueryWrapper.eq("subject_id",subjectList.get(i).getSubjectId());

            System.out.println("subjectList.get(i).getSubjectId()======"+subjectList.get(i).getSubjectId());

            List<SubjectCategory> subjectCategoryList = subjectCategoryMapper.selectList(subjectCategoryQueryWrapper);

            List<LinkedHashMap<String, Object>> primaryList = new ArrayList<>();

            System.out.println("subjectCategoryList.size()::"+subjectCategoryList.size());

            for(int j = 0; j < subjectCategoryList.size(); j++){

                LinkedHashMap<String, Object> hashMap1 = new LinkedHashMap<>();

                QueryWrapper<CategoryPrimary> categoryPrimaryQueryWrapper = new QueryWrapper<>();
                // 根据 category_primary_id 从 categoryPrimary 表里拿到 categoryPrimary对象
                categoryPrimaryQueryWrapper.eq("category_primary_id",subjectCategoryList.get(j).getCategoryPrimaryId());
                System.out.println("subjectCategoryList.get(j).getCategoryPrimaryId()====>>>>>"+subjectCategoryList.get(j).getCategoryPrimaryId());
                CategoryPrimary categoryPrimary = categoryPrimaryMapper.selectOne(categoryPrimaryQueryWrapper);
                QueryWrapper<CatePriSec> catePriSecQueryWrapper = new QueryWrapper<>();
                // 根据 category_primary_id 从 cate_pri_sec 表里拿到 cate_pri_sec 对象
                catePriSecQueryWrapper.eq("category_primary_id",subjectCategoryList.get(j).getCategoryPrimaryId());
                List<CatePriSec> catePriSecList = catePriSecMapper.selectList(catePriSecQueryWrapper);
                QueryWrapper<CategorySecondary> categorySecondaryQueryWrapper = new QueryWrapper<>();
                ArrayList<Integer> secondaryIdList = new ArrayList<>();
                for(CatePriSec catePriSec : catePriSecList){
                    secondaryIdList.add(catePriSec.getCategorySecondaryId());
                }
                // 根据 category_secondary_id 数组，从 category_secondary 表里拿到 category_secondary对象
                categorySecondaryQueryWrapper.in("category_secondary_id",secondaryIdList);
                List<CategorySecondary> categorySecondaryList = categorySecondaryMapper.selectList(categorySecondaryQueryWrapper);

                hashMap1.put("categoryPrimaryId",categoryPrimary.getCategoryPrimaryId());
                hashMap1.put("categoryPrimaryName",categoryPrimary.getCategoryPrimaryName());
                hashMap1.put("categorySecondary",categorySecondaryList);

                primaryList.add(hashMap1);

            }

            hashMap.put("subjectId",subjectList.get(i).getSubjectId());
            hashMap.put("subjectName",subjectList.get(i).getSubjectName());
            hashMap.put("subjectDescription",subjectList.get(i).getSubjectDescription());
            hashMap.put("subjectCreateTime",subjectList.get(i).getSubjectCreateTime());
            hashMap.put("categoryPrimary",primaryList);

            subjectList1.add(hashMap);
        }

        System.out.println("subjectList1：" + subjectList1);

        return subjectList1;
    }
}




