package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myarxiv.myarxiv.common.DefaultPageParam;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.SearchOrder;
import com.myarxiv.myarxiv.common.field.Fields;
import com.myarxiv.myarxiv.domain.*;
import com.myarxiv.myarxiv.domain.relation.PaperCategory;
import com.myarxiv.myarxiv.domain.relation.PaperFile;
import com.myarxiv.myarxiv.mapper.*;
import com.myarxiv.myarxiv.mapper.relation.PaperCategoryMapper;
import com.myarxiv.myarxiv.mapper.relation.PaperFileMapper;
import com.myarxiv.myarxiv.pojo.PaperInfo;
import com.myarxiv.myarxiv.pojo.PaperRoughly;
import com.myarxiv.myarxiv.pojo.PriAndSec;
import com.myarxiv.myarxiv.pojo.PriAndSecDetail;
import com.myarxiv.myarxiv.service.SearchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private PaperDetailMapper paperDetailMapper;

    @Resource
    private PaperMapper paperMapper;

    @Resource
    private FilesMapper filesMapper;

    @Resource
    private PaperFileMapper paperFileMapper;

    @Resource
    private LicenseMapper licenseMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private PaperCategoryMapper paperCategoryMapper;

    @Resource
    private CategoryPrimaryMapper categoryPrimaryMapper;

    @Resource
    private CategorySecondaryMapper categorySecondaryMapper;

    @Override
    public Object search(String query, String searchField) {
        return searchTemplate(query,searchField, DefaultPageParam.DEFAULT_PAGE_SIZE.getParam(), SearchOrder.NEW.getCode(), DefaultPageParam.DEFAULT_PAGE_NUM.getParam());
    }

    @Override
    public Object search(String query, String searchField, Integer pageSize, Integer orderByCode) {

        return searchTemplate(query,searchField,pageSize,orderByCode,DefaultPageParam.DEFAULT_PAGE_NUM.getParam());
    }

    @Override
    public Object search(String query, String searchField, Integer pageSize, Integer orderByCode, Integer pageNum) {

        return searchTemplate(query,searchField,pageSize,orderByCode,pageNum);
    }

    @Override
    public Object searchByDetail(String query, String searchField) {
        return searchByDetailTemplate(query,searchField,DefaultPageParam.DEFAULT_PAGE_SIZE.getParam(), SearchOrder.NEW.getCode(), DefaultPageParam.DEFAULT_PAGE_NUM.getParam());
    }

    @Override
    public Object searchByDetail(String query, String searchField, Integer pageSize, Integer orderByCode) {

        return searchByDetailTemplate(query,searchField,pageSize,orderByCode,DefaultPageParam.DEFAULT_PAGE_NUM.getParam());
    }

    @Override
    public Object searchByDetail(String query, String searchField, Integer pageSize, Integer orderByCode, Integer pageNum) {

        return searchByDetailTemplate(query,searchField,pageSize,orderByCode,pageNum);
    }

    @Override
    public Object searchTemplate(String query, String searchField, Integer pageSize, Integer orderByCode, Integer pageNum) {
        Page<Paper> page = new Page<>(pageNum,pageSize);
        Page<Paper> paperPage = null;
        if(searchField.equals(Fields.all.getField())){
            LambdaQueryWrapper<Paper> paperLambdaQueryWrapper = new LambdaQueryWrapper<>();
            if(orderByCode == SearchOrder.NEW.getCode()){
                paperLambdaQueryWrapper.like(Paper::getPaperAbstract,query).or()
                        .like(Paper::getPaperComments,query).or()
                        .like(Paper::getPaperAuthors,query).or()
                        .like(Paper::getPaperTitle,query).or()
                        .like(Paper::getPaperIdentifier,query).orderByDesc(Paper::getPaperCreateTime);
            }else{
                paperLambdaQueryWrapper.like(Paper::getPaperAbstract,query).or()
                        .like(Paper::getPaperComments,query).or()
                        .like(Paper::getPaperAuthors,query).or()
                        .like(Paper::getPaperTitle,query).or()
                        .like(Paper::getPaperIdentifier,query).orderByAsc(Paper::getPaperCreateTime);
            }

            paperPage  = paperMapper.selectPage(page,paperLambdaQueryWrapper);
        }else {
            QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
            if(orderByCode == SearchOrder.NEW.getCode()){
                paperQueryWrapper.like(searchField,query).orderByDesc("paper_create_time");
            }else{
                paperQueryWrapper.like(searchField,query).orderByAsc("paper_create_time");
            }

            paperPage = paperMapper.selectPage(page, paperQueryWrapper);
        }

        List<Paper> paperList = paperPage.getRecords();
        ArrayList<PaperInfo> paperInfoList = new ArrayList<>();
        for(Paper p : paperList){
            PaperInfo paperInfo = new PaperInfo();
            PaperDetail paperDetail = paperDetailMapper.selectById(p.getPaperDetailId());
            LambdaQueryWrapper<PaperFile> paperFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            paperFileLambdaQueryWrapper.eq(PaperFile::getPaperId,p.getPaperId());
            List<PaperFile> paperFiles = paperFileMapper.selectList(paperFileLambdaQueryWrapper);
            ArrayList<Integer> fileIDList = new ArrayList<>();
            for(PaperFile f : paperFiles){
                fileIDList.add(f.getFileId());
            }
            List<Files> files = filesMapper.selectBatchIds(fileIDList);
            // 获取 paperRoughly
            PaperRoughly paperRoughly = getPaperRoughly(p);
            paperInfo.setPaperRoughly(paperRoughly);
            paperInfo.setPaperDetail(paperDetail);
            paperInfo.setFileList(files);
            paperInfoList.add(paperInfo);
        }
        return ResponseResult.success(paperInfoList);
    }

    @Override
    public Object searchByDetailTemplate(String query, String searchField, Integer pageSize, Integer orderByCode, Integer pageNum) {
        Page<PaperDetail> page = new Page<>(pageNum,pageSize);

        QueryWrapper<PaperDetail> paperDetailQueryWrapper = new QueryWrapper<>();
        paperDetailQueryWrapper.like(searchField,query).eq("paper_detail_status",1);
        Page<PaperDetail> paperDetailPage = paperDetailMapper.selectPage(page, paperDetailQueryWrapper);
        List<PaperDetail> records = paperDetailPage.getRecords();
        ArrayList<Integer> paperDetailIdList = new ArrayList<>();
        HashMap<Integer, PaperDetail> paperDetailHashMap = new HashMap<>();
        for(PaperDetail pd : records){
            paperDetailHashMap.put(pd.getPaperDetailId(),pd);
            paperDetailIdList.add(pd.getPaperDetailId());
        }

        LambdaQueryWrapper<Paper> paperLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(orderByCode == SearchOrder.NEW.getCode()){
            paperLambdaQueryWrapper.in(Paper::getPaperDetailId,paperDetailIdList).orderByDesc(Paper::getPaperUpdateTime);
        }else{
            paperLambdaQueryWrapper.in(Paper::getPaperDetailId,paperDetailIdList).orderByAsc(Paper::getPaperUpdateTime);
        }

        List<Paper> paperList = paperMapper.selectList(paperLambdaQueryWrapper);

        ArrayList<PaperInfo> paperInfoList = new ArrayList<>();
        for(Paper pa : paperList){
            PaperInfo paperInfo = new PaperInfo();;
            LambdaQueryWrapper<PaperFile> paperFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            paperFileLambdaQueryWrapper.eq(PaperFile::getPaperId,pa.getPaperId());
            List<PaperFile> paperFiles = paperFileMapper.selectList(paperFileLambdaQueryWrapper);
            ArrayList<Integer> fileIdList = new ArrayList<>();
            for(PaperFile pf : paperFiles){
                fileIdList.add(pf.getFileId());
            }
            List<Files> files = filesMapper.selectBatchIds(fileIdList);
            // 获取paperRoughly
            PaperRoughly paperRoughly = getPaperRoughly(pa);
            paperInfo.setPaperRoughly(paperRoughly);
            paperInfo.setFileList(files);
            paperInfo.setPaperDetail(paperDetailHashMap.get(pa.getPaperDetailId()));
            paperInfoList.add(paperInfo);
        }

        return ResponseResult.success(paperInfoList);
    }

    @Override
    public PaperRoughly getPaperRoughly(Paper paper) {
        PaperRoughly paperRoughly = new PaperRoughly();
        License license = licenseMapper.selectById(paper.getLicenseId());
        Subject subject = subjectMapper.selectById(paper.getSubjectId());

        LambdaQueryWrapper<PaperCategory> paperCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        paperCategoryLambdaQueryWrapper.eq(PaperCategory::getPaperId,paper.getPaperId());
        List<PaperCategory> paperCategoryList = paperCategoryMapper.selectList(paperCategoryLambdaQueryWrapper);
        ArrayList<PriAndSecDetail> priSecList = new ArrayList<>();
        for(PaperCategory pc : paperCategoryList){
            PriAndSecDetail priAndSecDetail = new PriAndSecDetail();
            CategoryPrimary categoryPrimary = categoryPrimaryMapper.selectById(pc.getCategoryPrimaryId());
            CategorySecondary categorySecondary = categorySecondaryMapper.selectById(pc.getCategorySecondaryId());
            priAndSecDetail.setCategoryPrimary(categoryPrimary.getCategoryPrimaryName());
            priAndSecDetail.setCategorySecondary(categorySecondary.getCategorySecondaryName());
            // status为0表示是主要的类型，为1表示次要的交叉类型
            priAndSecDetail.setPaperCategoryStatus(pc.getPaperCategoryStatus());
            priSecList.add(priAndSecDetail);
        }
        paperRoughly.setPaperId(paper.getPaperId());
        paperRoughly.setSubject(subject.getSubjectName());
        paperRoughly.setLicense(license.getLicenseLink());
        paperRoughly.setPaperTitle(paper.getPaperTitle());
        paperRoughly.setPaperAbstract(paper.getPaperAbstract());
        paperRoughly.setPaperIdentifier(paper.getPaperIdentifier());
        paperRoughly.setPaperAuthors(paper.getPaperAuthors());
        paperRoughly.setPaperComments(paper.getPaperComments());
        paperRoughly.setPaperCreateTime(paper.getPaperCreateTime());
        paperRoughly.setPaperUpdateTime(paper.getPaperUpdateTime());
        paperRoughly.setPaperStatus(paper.getPaperStatus());
        paperRoughly.setPriAndSecDetailList(priSecList);

        return paperRoughly;
    }


}
