package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.domain.Files;
import com.myarxiv.myarxiv.domain.Paper;
import com.myarxiv.myarxiv.domain.PaperDetail;
import com.myarxiv.myarxiv.domain.User;
import com.myarxiv.myarxiv.domain.relation.PaperFile;
import com.myarxiv.myarxiv.domain.relation.UserPaper;
import com.myarxiv.myarxiv.mapper.FilesMapper;
import com.myarxiv.myarxiv.mapper.PaperDetailMapper;
import com.myarxiv.myarxiv.mapper.UserMapper;
import com.myarxiv.myarxiv.mapper.relation.PaperFileMapper;
import com.myarxiv.myarxiv.mapper.relation.UserPaperMapper;
import com.myarxiv.myarxiv.pojo.PaperInfo;
import com.myarxiv.myarxiv.pojo.PaperRoughly;
import com.myarxiv.myarxiv.service.PaperService;
import com.myarxiv.myarxiv.mapper.PaperMapper;
import com.myarxiv.myarxiv.service.SearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 时之始
* @description 针对表【paper】的数据库操作Service实现
* @createDate 2023-04-29 22:49:03
*/
@Transactional
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper>
    implements PaperService{

    @Resource
    private UserPaperMapper userPaperMapper;

    @Resource
    private PaperMapper paperMapper;

    @Resource
    private SearchService searchService;

    @Resource
    private PaperDetailMapper paperDetailMapper;

    @Resource
    private PaperFileMapper paperFileMapper;

    @Resource
    private FilesMapper filesMapper;

    @Override
    public List<Object> getPaperListByUserId(Integer userId) {

        LambdaQueryWrapper<UserPaper> userPaperLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userPaperLambdaQueryWrapper.eq(UserPaper::getUserId,userId);
        List<UserPaper> userPaperList = userPaperMapper.selectList(userPaperLambdaQueryWrapper);
        ArrayList<Integer> paperIdList = new ArrayList<>();
        for(UserPaper up : userPaperList){
            paperIdList.add(up.getPaperId());
        }

        List<Paper> paperList = paperMapper.selectBatchIds(paperIdList);

        ArrayList<Object> paperInfoList = new ArrayList<>();
        for(Paper p : paperList){
            PaperRoughly paperRoughly = searchService.getPaperRoughly(p, 0);
            paperInfoList.add(paperRoughly);
        }

        return paperInfoList;
    }

    @Override
    public Object getPaperInfoById(Integer paperId) {

        Paper paper = paperMapper.selectById(paperId);

        // paperStatus为0代表查询分类的时候按照paperId查
        PaperRoughly paperRoughly = searchService.getPaperRoughly(paper, 0);

        PaperInfo paperInfo = new PaperInfo();
        // 获取论文详情
        PaperDetail paperDetail = paperDetailMapper.selectById(paper.getPaperDetailId());
        LambdaQueryWrapper<PaperFile> paperFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        paperFileLambdaQueryWrapper.eq(PaperFile::getPaperId,paper.getPaperId());
        List<PaperFile> paperFiles = paperFileMapper.selectList(paperFileLambdaQueryWrapper);
        ArrayList<Integer> fileIDList = new ArrayList<>();
        for(PaperFile f : paperFiles){
            fileIDList.add(f.getFileId());
        }
        // 获取论文关联的文件
        List<Files> files = filesMapper.selectBatchIds(fileIDList);
        paperInfo.setPaperRoughly(paperRoughly);
        paperInfo.setPaperDetail(paperDetail);
        paperInfo.setFileList(files);

        return ResponseResult.success(paperInfo);
    }
}




