package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.domain.Files;
import com.myarxiv.myarxiv.domain.Paper;
import com.myarxiv.myarxiv.domain.PaperDetail;
import com.myarxiv.myarxiv.domain.relation.PaperCategory;
import com.myarxiv.myarxiv.domain.relation.PaperFile;
import com.myarxiv.myarxiv.mapper.FilesMapper;
import com.myarxiv.myarxiv.mapper.LicenseMapper;
import com.myarxiv.myarxiv.mapper.PaperDetailMapper;
import com.myarxiv.myarxiv.mapper.PaperMapper;
import com.myarxiv.myarxiv.mapper.relation.PaperCategoryMapper;
import com.myarxiv.myarxiv.mapper.relation.PaperFileMapper;
import com.myarxiv.myarxiv.pojo.PaperInfo;
import com.myarxiv.myarxiv.pojo.PaperRoughly;
import com.myarxiv.myarxiv.service.HomeService;
import com.myarxiv.myarxiv.service.SearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class HomeServiceImpl implements HomeService {

    @Resource
    private PaperCategoryMapper paperCategoryMapper;

    @Resource
    private PaperMapper paperMapper;

    @Resource
    private PaperFileMapper paperFileMapper;

    @Resource
    private FilesMapper filesMapper;

    @Resource
    private SearchService searchService;

    @Resource
    private PaperDetailMapper paperDetailMapper;

    @Override
    public Object getPapersOfCategoryTemplate(Integer categorySecondaryId,Integer pageSize, Integer pageNum) {
        Page<PaperCategory> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<PaperCategory> paperCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        paperCategoryLambdaQueryWrapper.eq(PaperCategory::getCategorySecondaryId,categorySecondaryId).isNotNull(PaperCategory::getPaperId);
        Page<PaperCategory> paperCategoryPage = paperCategoryMapper.selectPage(page, paperCategoryLambdaQueryWrapper);
        List<PaperCategory> records = paperCategoryPage.getRecords();
        ArrayList<Integer> paperIdList = new ArrayList<>();
        for(PaperCategory pc : records){
            paperIdList.add(pc.getPaperId());
        }

        LambdaQueryWrapper<Paper> paperLambdaQueryWrapper = new LambdaQueryWrapper<>();
        paperLambdaQueryWrapper.in(Paper::getPaperId,paperIdList).orderByDesc(Paper::getPaperCreateTime);
        List<Paper> paperList = paperMapper.selectList(paperLambdaQueryWrapper);
        ArrayList<PaperInfo> paperInfoList = new ArrayList<>();
        for(Paper p : paperList){

            PaperDetail paperDetail = paperDetailMapper.selectById(p.getPaperDetailId());
            LambdaQueryWrapper<PaperFile> paperFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            paperFileLambdaQueryWrapper.eq(PaperFile::getPaperId,p.getPaperId());
            List<PaperFile> paperFileList = paperFileMapper.selectList(paperFileLambdaQueryWrapper);
            ArrayList<Integer> fileIdList = new ArrayList<>();
            for(PaperFile pf : paperFileList){
                fileIdList.add(pf.getFileId());
            }
            List<Files> fileList = filesMapper.selectBatchIds(fileIdList);
            PaperRoughly paperRoughly = searchService.getPaperRoughly(p);
            PaperInfo paperInfo = new PaperInfo();
            paperInfo.setPaperRoughly(paperRoughly);
            paperInfo.setPaperDetail(paperDetail);
            paperInfo.setFileList(fileList);
            paperInfoList.add(paperInfo);
        }

        return ResponseResult.success(paperInfoList);
    }
}
