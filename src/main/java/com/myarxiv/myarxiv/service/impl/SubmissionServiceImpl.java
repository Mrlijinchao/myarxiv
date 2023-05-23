package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myarxiv.myarxiv.common.DateUtils;
import com.myarxiv.myarxiv.common.FileUtils;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.domain.*;
import com.myarxiv.myarxiv.domain.relation.CatePriSec;

import com.myarxiv.myarxiv.domain.relation.PaperCategory;
import com.myarxiv.myarxiv.domain.relation.PaperFile;
import com.myarxiv.myarxiv.domain.relation.SubjectCategory;
import com.myarxiv.myarxiv.mapper.*;
import com.myarxiv.myarxiv.mapper.relation.CatePriSecMapper;
import com.myarxiv.myarxiv.mapper.relation.PaperCategoryMapper;
import com.myarxiv.myarxiv.mapper.relation.PaperFileMapper;
import com.myarxiv.myarxiv.mapper.relation.SubjectCategoryMapper;
import com.myarxiv.myarxiv.pojo.PriAndSec;
import com.myarxiv.myarxiv.pojo.SubmissionStep;
import com.myarxiv.myarxiv.service.SubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
* @author 时之始
* @description 针对表【submission】的数据库操作Service实现
* @createDate 2023-04-29 09:40:53
*/
@Slf4j
@Service
@Transactional
public class SubmissionServiceImpl extends ServiceImpl<SubmissionMapper, Submission>
    implements SubmissionService{

    @Value("${myConfig.submission.fileBasePath}")
    private String fileBasePath;
    @Value("${myConfig.submission.fileUrlBase}")
    private String fileUrlBase;

    @Resource
    private SubmissionPaperMapper submissionPaperMapper;

    @Resource
    private PaperCategoryMapper paperCategoryMapper;

    @Resource
    private SubmissionMapper submissionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrganizationMailboxMapper organizationMailboxMapper;

    @Resource
    private EndorsementMapper endorsementMapper;

    @Resource
    private PaperFileMapper paperFileMapper;

    @Resource
    private FilesMapper filesMapper;

    @Resource
    private PaperDetailMapper paperDetailMapper;

    @Resource
    private SubjectCategoryMapper subjectCategoryMapper;

    @Resource
    private CategoryPrimaryMapper categoryPrimaryMapper;

    @Resource
    private CatePriSecMapper catePriSecMapper;

    @Resource
    private CategorySecondaryMapper categorySecondaryMapper;

    @Resource
    private PaperCategory paperCategory;

    @Override
    public Object saveInfoFirst(Integer userId, Integer subjectId, Integer categoryPrimaryId,
                                                       Integer categorySecondaryId, Integer licenseId) {
        Date date = new Date();
        // 创建 submission_paper
        SubmissionPaper submissionPaper = new SubmissionPaper();
        submissionPaper.setLicenseId(licenseId);
        submissionPaper.setSubmissionSubjectId(subjectId);
        submissionPaper.setSubmissionPaperCreateTime(date);
        submissionPaperMapper.insert(submissionPaper);

        // 创建 paperCategory
        PaperCategory paperCategory = new PaperCategory();
        paperCategory.setCategoryPrimaryId(categoryPrimaryId);
        paperCategory.setCategorySecondaryId(categorySecondaryId);
        paperCategory.setSubmissionPaperId(submissionPaper.getSubmissionPaperId());
        paperCategoryMapper.insert(paperCategory);


        // 创建 submission
        Submission submission = new Submission();
        submission.setSubmissionCreateTime(date);
        Date expireDate = DateUtils.addDate(date, 15);
        submission.setSubmissionExpires(expireDate);
        submission.setUserId(userId);
        submission.setSubmissionStep(1);
        submission.setSubmissionType("new");
        submission.setSubmissionPaperId(submissionPaper.getSubmissionPaperId());
        submissionMapper.insert(submission);

        submission.setSubmissionTitle("Submission ID:"+submission.getSubmissionId());
        submission.setSubmissionIdentifier("submit/"+submission.getSubmissionId());
        submissionMapper.updateById(submission);

        // 检查用户邮箱是否是高校或者机构邮箱，如果不是就需要背书
        User user = userMapper.selectById(userId);
        String userEmail = user.getUserEmail();
        int index = userEmail.lastIndexOf('@');
        String substring = userEmail.substring(index + 1);
        String[] split = substring.split("\\.");
        String str = split[split.length-2] + "." + split[split.length-1];
        QueryWrapper<OrganizationMailbox> organizationMailboxQueryWrapper = new QueryWrapper<>();
        organizationMailboxQueryWrapper.like("organization_mailbox_email",str);
        List<OrganizationMailbox> organizationMailboxList = organizationMailboxMapper.selectList(organizationMailboxQueryWrapper);

        Endorsement endorsement = new Endorsement();
        endorsement.setUserId(userId);
        endorsement.setSubmissionId(submission.getSubmissionId());
        if(!organizationMailboxList.isEmpty()){
            endorsement.setEndorsementStatus(1);
        }else{
            endorsement.setEndorsementStatus(0);
        }
        endorsementMapper.insert(endorsement);

        ConcurrentHashMap<String, Object> hashMap = new ConcurrentHashMap<String,Object>();
        hashMap.put("submission",submission);
        hashMap.put("endorsementStatus",endorsement.getEndorsementStatus());

        System.out.println(hashMap);
        log.info(String.valueOf(hashMap));

        return hashMap;
    }

    @Override
    public Object saveInfo(Integer userId, Integer subjectId, Integer categoryPrimaryId, Integer categorySecondaryId, Integer licenseId, Integer submissionId) {

        // 查询一条submission 拿到SubmissionPaperId
        Submission submission = submissionMapper.selectById(submissionId);
        // 只更新一个step字段
        LambdaUpdateWrapper<Submission> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Submission::getSubmissionId, submissionId).set(Submission::getSubmissionStep, 1);
        submissionMapper.update(null, lambdaUpdateWrapper);

        // 更新一条 SubmissionPaper
        SubmissionPaper submissionPaper = new SubmissionPaper();
        submissionPaper.setSubmissionPaperId(submission.getSubmissionPaperId());
        submissionPaper.setSubmissionSubjectId(subjectId);
        submissionPaper.setLicenseId(licenseId);
        submissionPaperMapper.updateById(submissionPaper);

        // 更新
        PaperCategory paperCategory = new PaperCategory();
        paperCategory.setCategoryPrimaryId(categoryPrimaryId);
        paperCategory.setCategorySecondaryId(categorySecondaryId);
        QueryWrapper<PaperCategory> paperCategoryQueryWrapper = new QueryWrapper<>();
        paperCategoryQueryWrapper.eq("submission_paper_id",submissionPaper.getSubmissionPaperId());
        paperCategoryMapper.update(paperCategory,paperCategoryQueryWrapper);

        // 查询背书状态
        QueryWrapper<Endorsement> endorsementQueryWrapper = new QueryWrapper<>();
        endorsementQueryWrapper.eq("submission_id",submission.getSubmissionId());
        Endorsement endorsement = endorsementMapper.selectOne(endorsementQueryWrapper);

        ConcurrentHashMap<String, Object> hashMap = new ConcurrentHashMap<>();
        hashMap.put("submission",submission);
        hashMap.put("endorsementStatus",endorsement.getEndorsementStatus());

        return hashMap;
    }

    @Transactional
    @Override
    public Object saveFiles(Integer userId, Integer submissionId, MultipartFile[] files) {

        Submission submission = submissionMapper.selectById(submissionId);
        // 只更新一个step字段
        LambdaUpdateWrapper<Submission> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Submission::getSubmissionId, submissionId).set(Submission::getSubmissionStep, 2);
        submissionMapper.update(null, lambdaUpdateWrapper);

        LambdaQueryWrapper<PaperFile> paperFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        paperFileLambdaQueryWrapper.eq(PaperFile::getSubmissionPaperId,submission.getSubmissionPaperId());
        List<PaperFile> paperFileList = paperFileMapper.selectList(paperFileLambdaQueryWrapper);

        if(!paperFileList.isEmpty()){
            for(PaperFile pa:paperFileList){
                LambdaQueryWrapper<Files> filesLambdaQueryWrapper = new LambdaQueryWrapper<>();
                QueryWrapper<PaperFile> paperFileQueryWrapper = new QueryWrapper<>();
                paperFileQueryWrapper.eq("file_id",pa.getFileId());
                filesLambdaQueryWrapper.eq(Files::getFileId,pa.getFileId());
                Files files1 = filesMapper.selectOne(filesLambdaQueryWrapper);
                String fileUrl = files1.getFileUrl();
                String substring = fileUrl.substring(fileUrl.substring(0, fileUrl.lastIndexOf('/')).lastIndexOf('/')+1);
                String filePath = fileBasePath + substring;
                filesMapper.delete(filesLambdaQueryWrapper);
                paperFileMapper.delete(paperFileQueryWrapper);
                FileUtils.deleteFile(filePath);
            }
        }

        Date date = new Date();
        //获取当前系统时间年月这里获取到月如果要精确到日修改("yyyy-MM-dd")
        String dateForm = new SimpleDateFormat("yyyy-MM").format(date);
        //地址合并 path.getFileimg 是存放在实体类的路径 不会写得同学可以直接写 "D:\\img" 这文件要手动创建
        String casePath = fileBasePath+dateForm;
        //判断文件是否存在
        File f = new File(casePath);
        try {if (!f.exists()){f.mkdirs();}
        }catch (Exception e){
            e.printStackTrace();
        }
        ArrayList<Files> fileList = new ArrayList<>();
        for(MultipartFile file : files){

            Files file1 = new Files();
            PaperFile paperFile = new PaperFile();
            //获取图片后缀
            String originalFilename = file.getOriginalFilename();
            String imgFormat = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //给图片重新随机生成名字
            String imageName= UUID.randomUUID().toString()+imgFormat;
            String fileUrl = fileUrlBase+dateForm+"/"+imageName;

            file1.setFileType(imgFormat);
            file1.setFileName(originalFilename);
            file1.setFileUrl(fileUrl);
            fileList.add(file1);
            filesMapper.insert(file1);
            paperFile.setSubmissionPaperId(submission.getSubmissionPaperId());
            paperFile.setFileId(file1.getFileId());
            paperFileMapper.insert(paperFile);

            try {
                //保存图片
                file.transferTo(new File(casePath+"/"+imageName));
            } catch (IOException e) {
                log.info("存储失败！");
                e.printStackTrace();
            }
        }

        return ResponseResult.success(fileList);
    }

    @Override
    public Object savePaperInfo(SubmissionPaper submissionPaper, PaperDetail paperDetail,
                                Integer submissionId, Integer userId) {
        Submission submission = submissionMapper.selectById(submissionId);
        // 只更新一个step字段
        LambdaUpdateWrapper<Submission> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Submission::getSubmissionId, submissionId).set(Submission::getSubmissionStep, 4);
        submissionMapper.update(null, lambdaUpdateWrapper);

        SubmissionPaper submissionPaper1 = submissionPaperMapper.selectById(submission.getSubmissionPaperId());
        QueryWrapper<SubmissionPaper> submissionPaperQueryWrapper = new QueryWrapper<>();;
        if(submissionPaper1.getSubmissionPaperDetailId() == null){
            // 插入一条论文详情
            paperDetailMapper.insert(paperDetail);
            // 更新 SubmissionPaper
            Date date = new Date();
            //获取当前系统时间年月这里获取到月如果要精确到日修改("yyyy-MM-dd")
            String dateForm = new SimpleDateFormat("yyyy").format(date);
            dateForm = dateForm + "." + new SimpleDateFormat("MM").format(date);
            dateForm = dateForm + new SimpleDateFormat("dd").format(date);
            dateForm = dateForm + new SimpleDateFormat("ss").format(date);
            submissionPaper.setSubmissionPaperDetailId(paperDetail.getPaperDetailId());
            submissionPaper.setSubmissionPaperIdentifier(dateForm);
        }else {
            paperDetail.setPaperDetailId(submissionPaper1.getSubmissionPaperDetailId());
            paperDetailMapper.updateById(paperDetail);
        }

        submissionPaperQueryWrapper.eq("submission_paper_id",submission.getSubmissionPaperId());
        submissionPaperMapper.update(submissionPaper,submissionPaperQueryWrapper);

        return ResponseResult.success("保存成功");
    }

    @Override
    public Object searchInfo(Integer submissionId) {

        // 根据submissionId拿到SubmissionPaperId
        Submission submission = submissionMapper.selectById(submissionId);
        // 查询一条 SubmissionPaper
        SubmissionPaper submissionPaper = submissionPaperMapper.selectById(submission.getSubmissionPaperId());
        // 查询所有关联文件
        LambdaQueryWrapper<PaperFile> paperFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        paperFileLambdaQueryWrapper.eq(PaperFile::getSubmissionPaperId,submission.getSubmissionPaperId());
        List<PaperFile> paperFileList = paperFileMapper.selectList(paperFileLambdaQueryWrapper);
        ArrayList<Integer> fileIdList = new ArrayList<>();
        for(PaperFile pa:paperFileList){
            fileIdList.add(pa.getFileId());
        }
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("submissionPaper",submissionPaper);
        List<Files> fileList = filesMapper.selectBatchIds(fileIdList);
        hashMap.put("fileList",fileList);
        // 查询这篇论文主要的分类
        LambdaQueryWrapper<PaperCategory> paperCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        paperCategoryLambdaQueryWrapper.eq(PaperCategory::getSubmissionPaperId,submission.getSubmissionPaperId())
                .eq(PaperCategory::getPaperCategoryStatus,0);
        PaperCategory paperCategory = paperCategoryMapper.selectOne(paperCategoryLambdaQueryWrapper);
        CategoryPrimary categoryPrimary1 = categoryPrimaryMapper.selectById(paperCategory.getCategoryPrimaryId());
        hashMap.put("primaryCategory",categoryPrimary1.getCategoryPrimaryName());

        // 查询分类字典
        LambdaQueryWrapper<SubjectCategory> subjectCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        subjectCategoryLambdaQueryWrapper.eq(SubjectCategory::getSubjectId,submissionPaper.getSubmissionSubjectId());
        List<SubjectCategory> subjectCategories = subjectCategoryMapper.selectList(subjectCategoryLambdaQueryWrapper);
        ArrayList<Object> categoryList = new ArrayList<>();
        for(SubjectCategory subCat : subjectCategories){
            ArrayList<Object> secCategoryList = new ArrayList<>();
            LinkedHashMap<String, Object> priCategoryMap = new LinkedHashMap<>();
            CategoryPrimary categoryPrimary = categoryPrimaryMapper.selectById(subCat.getCategoryPrimaryId());
            LambdaQueryWrapper<CatePriSec> catePriSecLambdaQueryWrapper = new LambdaQueryWrapper<>();
            catePriSecLambdaQueryWrapper.eq(CatePriSec::getCategoryPrimaryId,subCat.getCategoryPrimaryId());
            List<CatePriSec> catePriSecs = catePriSecMapper.selectList(catePriSecLambdaQueryWrapper);
            for (CatePriSec catePs : catePriSecs){
                LinkedHashMap<String, Object> cateSecMap = new LinkedHashMap<>();
                CategorySecondary categorySecondary = categorySecondaryMapper.selectById(catePs.getCategorySecondaryId());
                cateSecMap.put("categorySecondaryId",categorySecondary.getCategorySecondaryId());
                cateSecMap.put("categorySecondaryName",categorySecondary.getCategorySecondaryName());
                secCategoryList.add(cateSecMap);
            }
            priCategoryMap.put("categoryPrimaryId",categoryPrimary.getCategoryPrimaryId());
            priCategoryMap.put("categoryPrimaryName",categoryPrimary.getCategoryPrimaryName());
            priCategoryMap.put("children",secCategoryList);
            categoryList.add(priCategoryMap);
        }
        hashMap.put("category",categoryList);
        return hashMap;
    }

    @Override
    public Object saveLastSubmit(SubmissionStep submissionStep) {

        Submission submission = submissionMapper.selectById(submissionStep.getSubmissionId());
        // 只更新一个step字段
        LambdaUpdateWrapper<Submission> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Submission::getSubmissionId, submission.getSubmissionId()).set(Submission::getSubmissionStatus, 1).set(Submission::getSubmissionStep,5);
        submissionMapper.update(null, lambdaUpdateWrapper);
        List<PriAndSec> categoryList = submissionStep.getCategoryList();
        if(categoryList != null && !categoryList.isEmpty()){
            for(PriAndSec pas : categoryList){
                PaperCategory paperCategory = new PaperCategory();
                paperCategory.setCategoryPrimaryId(pas.getCategoryPrimaryId());
                paperCategory.setCategorySecondaryId(pas.getCategorySecondaryId());
                paperCategory.setSubmissionPaperId(submission.getSubmissionPaperId());
                paperCategory.setPaperCategoryStatus(1);
                paperCategoryMapper.insert(paperCategory);
            }
        }

        return ResponseResult.success("完成提交");
    }


}




