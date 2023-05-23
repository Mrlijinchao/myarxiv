package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myarxiv.myarxiv.common.RedisUtils;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.SearchOrder;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.domain.*;
import com.myarxiv.myarxiv.domain.relation.PaperCategory;
import com.myarxiv.myarxiv.domain.relation.PaperFile;
import com.myarxiv.myarxiv.domain.relation.UserPaper;
import com.myarxiv.myarxiv.domain.relation.VerifierSubmission;
import com.myarxiv.myarxiv.mapper.*;
import com.myarxiv.myarxiv.mapper.relation.PaperCategoryMapper;
import com.myarxiv.myarxiv.mapper.relation.PaperFileMapper;
import com.myarxiv.myarxiv.mapper.relation.UserPaperMapper;
import com.myarxiv.myarxiv.mapper.relation.VerifierSubmissionMapper;
import com.myarxiv.myarxiv.pojo.PaperInfo;
import com.myarxiv.myarxiv.pojo.PaperRoughly;
import com.myarxiv.myarxiv.pojo.SubmissionInfo;
import com.myarxiv.myarxiv.service.AdminVerifierService;
import com.myarxiv.myarxiv.service.SearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 时之始
 * @description 针对表【admin_verifier】的数据库操作Service实现
 * @createDate 2023-05-01 13:15:44
 */
@Service
@Transactional
public class AdminVerifierServiceImpl extends ServiceImpl<AdminVerifierMapper, AdminVerifier>
        implements AdminVerifierService{

    @Resource
    private AdminVerifierMapper adminVerifierMapper;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private SubmissionMapper submissionMapper;

    @Resource
    private SubmissionPaperMapper submissionPaperMapper;

    @Resource
    private PaperMapper paperMapper;

    @Resource
    private UserPaperMapper userPaperMapper;

    @Resource
    private PaperFileMapper paperFileMapper;

    @Resource
    private PaperDetailMapper paperDetailMapper;

    @Resource
    private PaperCategoryMapper paperCategoryMapper;

    @Resource
    private FilesMapper filesMapper;

    @Resource
    private SearchService searchService;

    @Resource
    private VerifierSubmissionMapper verifierSubmissionMapper;

    @Override
    public Object checkLogin(AdminVerifier adminVerifier) {

        if(adminVerifier.getAdminVerifierAccount() != null){
            AdminVerifier adminVerifierAccount = adminVerifierMapper.selectOne(new QueryWrapper<AdminVerifier>()
                    .eq("admin_verifier_account", adminVerifier.getAdminVerifierAccount()));
            if(adminVerifierAccount == null){
                return ResponseResult.fail("账号不存在！", StatusCode.PRECONDITION_FAILED.getCode());
            }

            //MD5加密
//            String md5Password = DigestUtils.md5DigestAsHex(adminVerifier.getAdminVerifierPassword().getBytes());
            String md5Password = adminVerifier.getAdminVerifierPassword();
            if (!md5Password.equals(adminVerifierAccount.getAdminVerifierPassword())) {
                return ResponseResult.fail("密码错误！",StatusCode.ERROR.getCode());
            }

            adminVerifier = adminVerifierAccount;
            //        String token = TokenUtil.generateToken(user);
            String token = "Bearer " + UUID.randomUUID().toString().replaceAll("-", "");
//        redisUtils.set(token,adminVerifier.getAdminVerifierId(),1, TimeUnit.HOURS);

            return ResponseResult.returnWithToken(adminVerifier, token);

        }

        return ResponseResult.fail("账号为空",StatusCode.ERROR.getCode());

    }

    @Override
    public Object saveVerifierInfo(AdminVerifier adminVerifier) {
        //MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(adminVerifier.getAdminVerifierPassword().getBytes());

        adminVerifier.setAdminVerifierPassword(md5Password);

        int i = adminVerifierMapper.insert(adminVerifier);

        if(i == 0){
            return ResponseResult.fail("创建账户失败",StatusCode.ERROR.getCode());
        }

        return ResponseResult.success("创建成功！");
    }

    @Override
    public Object savePaperInfo(Submission submission) {

        // 改为审核通过的状态 0未完成提交 1已提交未审核 2审核通过 3 on hold
        LambdaUpdateWrapper<Submission> submissionLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        submissionLambdaUpdateWrapper.eq(Submission::getSubmissionId,submission.getSubmissionId())
                .set(Submission::getSubmissionStatus,2);
        submissionMapper.update(null,submissionLambdaUpdateWrapper);
        submission = submissionMapper.selectById(submission.getSubmissionId());

        // 把提交论文改为已审核通过状态 0未审核 1审核通过
        LambdaUpdateWrapper<SubmissionPaper> submissionPaperLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        submissionPaperLambdaUpdateWrapper.eq(SubmissionPaper::getSubmissionPaperId,submission.getSubmissionPaperId())
                .set(SubmissionPaper::getSubmissionPaperStatus,1);
        submissionPaperMapper.update(null,submissionPaperLambdaUpdateWrapper);
        SubmissionPaper submissionPaper = submissionPaperMapper.selectById(submission.getSubmissionPaperId());

        Date date = new Date();
        //获取当前系统时间年月这里获取到月如果要精确到日修改("yyyy-MM-dd")
        String dateForm = new SimpleDateFormat("yyyy").format(date);
        dateForm = dateForm + "." + new SimpleDateFormat("MM").format(date);
        dateForm = dateForm + new SimpleDateFormat("dd").format(date);
        dateForm = dateForm + new SimpleDateFormat("ss").format(date);

        // 插入一条论文信息
        Paper paper = new Paper();
        paper.setPaperTitle(submissionPaper.getSubmissionPaperTitle());
        paper.setPaperAbstract(submissionPaper.getSubmissionPaperAbstract());
        paper.setPaperAuthors(submissionPaper.getSubmissionPaperAuthors());
        paper.setPaperComments(submissionPaper.getSubmissionPaperComments());
        paper.setLicenseId(submissionPaper.getLicenseId());
        paper.setSubjectId(submissionPaper.getSubmissionSubjectId());
        paper.setPaperCreateTime(date);
        paper.setPaperDetailId(submissionPaper.getSubmissionPaperDetailId());
        paper.setPaperIdentifier(dateForm);
        paperMapper.insert(paper);

        // 关联用户和论文
        UserPaper userPaper = new UserPaper();
        userPaper.setUserId(submission.getUserId());
        userPaper.setPaperId(paper.getPaperId());
        userPaperMapper.insert(userPaper);

        // 把论文和文件关联起来
        LambdaUpdateWrapper<PaperFile> paperFileLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        paperFileLambdaUpdateWrapper.eq(PaperFile::getSubmissionPaperId,submissionPaper.getSubmissionPaperId())
                .set(PaperFile::getPaperId,paper.getPaperId());
        paperFileMapper.update(null,paperFileLambdaUpdateWrapper);

        // 把论文和类型关联起来(注意类型里面的status字段未0表示是这篇论文的主要类型，未1的全部是交叉类型)
        LambdaUpdateWrapper<PaperCategory> paperCategoryLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        paperCategoryLambdaUpdateWrapper.eq(PaperCategory::getSubmissionPaperId,submissionPaper.getSubmissionPaperId())
                .set(PaperCategory::getPaperId,paper.getPaperId());
        paperCategoryMapper.update(null,paperCategoryLambdaUpdateWrapper);

        // 把论文详情改为已提交状态 0未审核 1审核通过
        LambdaUpdateWrapper<PaperDetail> paperDetailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        paperDetailLambdaUpdateWrapper.eq(PaperDetail::getPaperDetailId,submissionPaper.getSubmissionPaperDetailId())
                .set(PaperDetail::getPaperDetailStatus,1);
        paperDetailMapper.update(null,paperDetailLambdaUpdateWrapper);

        return ResponseResult.success("审核成功！");
    }

    @Override
    public Object changeStatus(VerifierSubmission verifierSubmission) {

        LambdaUpdateWrapper<Submission> submissionLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        submissionLambdaUpdateWrapper.eq(Submission::getSubmissionId,verifierSubmission.getSubmissionId())
                .set(Submission::getSubmissionStatus,3);
        submissionMapper.update(null,submissionLambdaUpdateWrapper);
        verifierSubmission.setVerifierSubmissionDate(new Date());
        verifierSubmission.setVerifierSubmissionStatus(1);
        verifierSubmissionMapper.insert(verifierSubmission);
        return ResponseResult.success("成功挂起！");
    }

    @Override
    public Object getUnreviewedSubmission(Integer pageSize, Integer orderByCode, Integer pageNum) {
        if(pageNum <= 0){
            return ResponseResult.fail("页码必须大于0",StatusCode.ERROR.getCode());
        }
        if(pageSize < 0){
            return ResponseResult.fail("页面大小必须大于0",StatusCode.ERROR.getCode());
        }
        Page<Submission> page = new Page<>(pageNum,pageSize);

        LambdaQueryWrapper<Submission> submissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(orderByCode == SearchOrder.OLD.getCode()){
            submissionLambdaQueryWrapper.eq(Submission::getSubmissionStatus,1).orderByAsc(Submission::getSubmissionCreateTime);
        }else {
            submissionLambdaQueryWrapper.eq(Submission::getSubmissionStatus,1).orderByDesc(Submission::getSubmissionCreateTime);
        }

        // 分页查询 submission
        Page<Submission> submissionPage = submissionMapper.selectPage(page, submissionLambdaQueryWrapper);

        if(submissionPage.getTotal() < ((pageNum - 1) * pageSize + 1)){
            return ResponseResult.fail("页码过大，没有那么多页面", StatusCode.ERROR.getCode());
        }

        List<Submission> submissionList = submissionPage.getRecords();
        ArrayList<SubmissionInfo> submissionInfoList = new ArrayList<>();
        for(Submission s : submissionList){
            // 查询一条submissionPaper
            SubmissionPaper submissionPaper = submissionPaperMapper.selectById(s.getSubmissionPaperId());
            // 查询论文详细
            PaperDetail paperDetail = paperDetailMapper.selectById(submissionPaper.getSubmissionPaperDetailId());
            LambdaQueryWrapper<PaperFile> paperFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            paperFileLambdaQueryWrapper.eq(PaperFile::getSubmissionPaperId,s.getSubmissionPaperId());
            // 查询论文关联的文件
            List<PaperFile> paperFileList = paperFileMapper.selectList(paperFileLambdaQueryWrapper);
            ArrayList<Integer> fileIdList = new ArrayList<>();
            for(PaperFile pf : paperFileList){
                fileIdList.add(pf.getFileId());
            }

            Paper paper = new Paper();
            paper.setPaperId(submissionPaper.getSubmissionPaperId());
            paper.setPaperComments(submissionPaper.getSubmissionPaperComments());
            paper.setPaperDetailId(submissionPaper.getSubmissionPaperDetailId());
            paper.setPaperStatus(submissionPaper.getSubmissionPaperStatus());
            paper.setPaperIdentifier(submissionPaper.getSubmissionPaperIdentifier());
            paper.setPaperCreateTime(submissionPaper.getSubmissionPaperCreateTime());
            paper.setSubjectId(submissionPaper.getSubmissionSubjectId());
            paper.setLicenseId(submissionPaper.getLicenseId());
            paper.setPaperUpdateTime(submissionPaper.getSubmissionPaperUpdateTime());
            paper.setPaperTitle(submissionPaper.getSubmissionPaperTitle());
            paper.setPaperAuthors(submissionPaper.getSubmissionPaperAuthors());
            paper.setPaperAbstract(submissionPaper.getSubmissionPaperAbstract());

            PaperRoughly paperRoughly = searchService.getPaperRoughly(paper,1);

            List<Files> fileList = filesMapper.selectBatchIds(fileIdList);
            SubmissionInfo submissionInfo = new SubmissionInfo();
            submissionInfo.setSubmission(s);
            submissionInfo.setPaperRoughly(paperRoughly);
            submissionInfo.setPaperDetail(paperDetail);
            submissionInfo.setFileList(fileList);

            submissionInfoList.add(submissionInfo);
        }
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("currentPage",submissionPage.getCurrent());
        hashMap.put("pageSize",submissionPage.getSize());
        hashMap.put("pageTotal",submissionPage.getTotal());
        hashMap.put("submissionInfo",submissionInfoList);
        return ResponseResult.success(hashMap);
    }

    @Override
    public Object getReviewedSubmission(Integer pageSize, Integer orderByCode, Integer pageNum) {
        if(pageNum <= 0){
            return ResponseResult.fail("页码必须大于0",StatusCode.ERROR.getCode());
        }
        if(pageSize < 0){
            return ResponseResult.fail("页面大小必须大于0",StatusCode.ERROR.getCode());
        }
        Page<Paper> page = new Page<>(pageNum,pageSize);

        LambdaQueryWrapper<Paper> paperLambdaQueryWrapper = new LambdaQueryWrapper<>();

        if(orderByCode == SearchOrder.OLD.getCode()){
            paperLambdaQueryWrapper.eq(Paper::getPaperStatus,0).orderByAsc(Paper::getPaperCreateTime);
        }else {
            paperLambdaQueryWrapper.eq(Paper::getPaperStatus,0).orderByDesc(Paper::getPaperCreateTime);
        }

        Page<Paper> paperPage = paperMapper.selectPage(page, paperLambdaQueryWrapper);
        if(paperPage.getTotal() < ((pageNum - 1) * pageSize + 1)){
            return ResponseResult.fail("页码过大，没有那么多页面", StatusCode.ERROR.getCode());
        }

        List<Paper> paperList = paperPage.getRecords();

        ArrayList<PaperInfo> paperInfoList = new ArrayList<>();
        for(Paper pa : paperList){
            PaperDetail paperDetail = paperDetailMapper.selectById(pa.getPaperDetailId());
            PaperInfo paperInfo = new PaperInfo();;
            LambdaQueryWrapper<PaperFile> paperFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            paperFileLambdaQueryWrapper.eq(PaperFile::getPaperId,pa.getPaperId());
            List<PaperFile> paperFiles = paperFileMapper.selectList(paperFileLambdaQueryWrapper);
            ArrayList<Integer> fileIdList = new ArrayList<>();
            for(PaperFile pf : paperFiles){
                fileIdList.add(pf.getFileId());
            }
            List<Files> files = filesMapper.selectBatchIds(fileIdList);
            // 获取paperRoughly  paperStatus为0 表示论文已审核，为1表示未审核
            PaperRoughly paperRoughly = searchService.getPaperRoughly(pa,0);
            paperInfo.setPaperRoughly(paperRoughly);
            paperInfo.setFileList(files);
            paperInfo.setPaperDetail(paperDetail);
            paperInfoList.add(paperInfo);
        }

        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("currentPage",paperPage.getCurrent());
        hashMap.put("pageSize",paperPage.getSize());
        hashMap.put("pageTotal",paperPage.getTotal());
        hashMap.put("paperInfo",paperInfoList);
        return ResponseResult.success(hashMap);


    }

    @Override
    public Object getOnHoldSubmission(Integer pageSize, Integer orderByCode, Integer pageNum) {
        if(pageNum <= 0){
            return ResponseResult.fail("页码必须大于0",StatusCode.ERROR.getCode());
        }
        if(pageSize < 0){
            return ResponseResult.fail("页面大小必须大于0",StatusCode.ERROR.getCode());
        }
        Page<Submission> page = new Page<>(pageNum,pageSize);

        LambdaQueryWrapper<Submission> submissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(orderByCode == SearchOrder.OLD.getCode()){
            submissionLambdaQueryWrapper.eq(Submission::getSubmissionStatus,3).orderByAsc(Submission::getSubmissionCreateTime);
        }else {
            submissionLambdaQueryWrapper.eq(Submission::getSubmissionStatus,3).orderByDesc(Submission::getSubmissionCreateTime);
        }

        // 分页查询 submission
        Page<Submission> submissionPage = submissionMapper.selectPage(page, submissionLambdaQueryWrapper);

        if(submissionPage.getTotal() < ((pageNum - 1) * pageSize + 1)){
            return ResponseResult.fail("页码过大，没有那么多页面", StatusCode.ERROR.getCode());
        }

        List<Submission> submissionList = submissionPage.getRecords();
        ArrayList<SubmissionInfo> submissionInfoList = new ArrayList<>();
        for(Submission s : submissionList){
            // 查询一条submissionPaper
            SubmissionPaper submissionPaper = submissionPaperMapper.selectById(s.getSubmissionPaperId());
            // 查询论文详细
            PaperDetail paperDetail = paperDetailMapper.selectById(submissionPaper.getSubmissionPaperDetailId());
            LambdaQueryWrapper<PaperFile> paperFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            paperFileLambdaQueryWrapper.eq(PaperFile::getSubmissionPaperId,s.getSubmissionPaperId());
            // 查询论文关联的文件
            List<PaperFile> paperFileList = paperFileMapper.selectList(paperFileLambdaQueryWrapper);
            ArrayList<Integer> fileIdList = new ArrayList<>();
            for(PaperFile pf : paperFileList){
                fileIdList.add(pf.getFileId());
            }

            Paper paper = new Paper();
            paper.setPaperId(submissionPaper.getSubmissionPaperId());
            paper.setPaperComments(submissionPaper.getSubmissionPaperComments());
            paper.setPaperDetailId(submissionPaper.getSubmissionPaperDetailId());
            paper.setPaperStatus(submissionPaper.getSubmissionPaperStatus());
            paper.setPaperIdentifier(submissionPaper.getSubmissionPaperIdentifier());
            paper.setPaperCreateTime(submissionPaper.getSubmissionPaperCreateTime());
            paper.setSubjectId(submissionPaper.getSubmissionSubjectId());
            paper.setLicenseId(submissionPaper.getLicenseId());
            paper.setPaperUpdateTime(submissionPaper.getSubmissionPaperUpdateTime());
            paper.setPaperTitle(submissionPaper.getSubmissionPaperTitle());
            paper.setPaperAuthors(submissionPaper.getSubmissionPaperAuthors());
            paper.setPaperAbstract(submissionPaper.getSubmissionPaperAbstract());

            PaperRoughly paperRoughly = searchService.getPaperRoughly(paper,1);

            List<Files> fileList = filesMapper.selectBatchIds(fileIdList);
            SubmissionInfo submissionInfo = new SubmissionInfo();
            submissionInfo.setSubmission(s);
            submissionInfo.setPaperRoughly(paperRoughly);
            submissionInfo.setPaperDetail(paperDetail);
            submissionInfo.setFileList(fileList);

            submissionInfoList.add(submissionInfo);
        }
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("currentPage",submissionPage.getCurrent());
        hashMap.put("pageSize",submissionPage.getSize());
        hashMap.put("pageTotal",submissionPage.getTotal());
        hashMap.put("submissionInfo",submissionInfoList);
        return ResponseResult.success(hashMap);
    }

    @Override
    public Object saveVerifierAndSubmissionInfo(VerifierSubmission verifierSubmission) {
        verifierSubmission.setVerifierSubmissionDate(new Date());
        return verifierSubmissionMapper.insert(verifierSubmission);
    }

    @Override
    public Object getSubmissionByVerifierIdAndStatus(Integer pageSize,Integer orderByCode,
                                                     Integer pageNum, Integer verifierId, Integer status) {
        if(pageNum <= 0){
            return ResponseResult.fail("页码必须大于0",StatusCode.ERROR.getCode());
        }
        if(pageSize < 0){
            return ResponseResult.fail("页面大小必须大于0",StatusCode.ERROR.getCode());
        }
        Page<Submission> page = new Page<>(pageNum,pageSize);

        LambdaQueryWrapper<VerifierSubmission> verifierSubmissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Submission> submissionLambdaQueryWrapper = null;
        if(verifierId != null){
            verifierSubmissionLambdaQueryWrapper.eq(VerifierSubmission::getVerifierId,verifierId).eq(VerifierSubmission::getVerifierSubmissionStatus,status);
            List<VerifierSubmission> verifierSubmissionList = verifierSubmissionMapper.selectList(verifierSubmissionLambdaQueryWrapper);
            if(verifierSubmissionList.isEmpty()){
                return ResponseResult.fail("您还没有审核过任何论文",StatusCode.ERROR.getCode());
            }
            ArrayList<Integer> submissionIdList = new ArrayList<>();
            for(VerifierSubmission vs : verifierSubmissionList){
                submissionIdList.add(vs.getSubmissionId());
            }
            submissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
            if(orderByCode == SearchOrder.OLD.getCode()){
                submissionLambdaQueryWrapper.in(Submission::getSubmissionId,submissionIdList).orderByAsc(Submission::getSubmissionCreateTime);
            }else {
                submissionLambdaQueryWrapper.in(Submission::getSubmissionId,submissionIdList).orderByDesc(Submission::getSubmissionCreateTime);
            }
        }else{
            if (status == 0){
                status = 2;
            }else{
                status = 3;
            }
            submissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
            if(orderByCode == SearchOrder.OLD.getCode()){
                submissionLambdaQueryWrapper.eq(Submission::getSubmissionStatus,status).orderByAsc(Submission::getSubmissionCreateTime);
            }else {
                submissionLambdaQueryWrapper.eq(Submission::getSubmissionStatus,status).orderByDesc(Submission::getSubmissionCreateTime);
            }
        }

        // 分页查询 submission
        Page<Submission> submissionPage = submissionMapper.selectPage(page, submissionLambdaQueryWrapper);

        if(submissionPage.getTotal() < ((pageNum - 1) * pageSize + 1)){
            return ResponseResult.fail("页码过大，没有那么多页面", StatusCode.ERROR.getCode());
        }

        List<Submission> submissionList = submissionPage.getRecords();
        ArrayList<SubmissionInfo> submissionInfoList = new ArrayList<>();
        for(Submission s : submissionList){
            // 查询一条submissionPaper
            SubmissionPaper submissionPaper = submissionPaperMapper.selectById(s.getSubmissionPaperId());
            // 查询论文详细
            PaperDetail paperDetail = paperDetailMapper.selectById(submissionPaper.getSubmissionPaperDetailId());
            LambdaQueryWrapper<PaperFile> paperFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            paperFileLambdaQueryWrapper.eq(PaperFile::getSubmissionPaperId,s.getSubmissionPaperId());
            // 查询论文关联的文件
            List<PaperFile> paperFileList = paperFileMapper.selectList(paperFileLambdaQueryWrapper);
            ArrayList<Integer> fileIdList = new ArrayList<>();
            for(PaperFile pf : paperFileList){
                fileIdList.add(pf.getFileId());
            }

            Paper paper = new Paper();
            paper.setPaperId(submissionPaper.getSubmissionPaperId());
            paper.setPaperComments(submissionPaper.getSubmissionPaperComments());
            paper.setPaperDetailId(submissionPaper.getSubmissionPaperDetailId());
            paper.setPaperStatus(submissionPaper.getSubmissionPaperStatus());
            paper.setPaperIdentifier(submissionPaper.getSubmissionPaperIdentifier());
            paper.setPaperCreateTime(submissionPaper.getSubmissionPaperCreateTime());
            paper.setSubjectId(submissionPaper.getSubmissionSubjectId());
            paper.setLicenseId(submissionPaper.getLicenseId());
            paper.setPaperUpdateTime(submissionPaper.getSubmissionPaperUpdateTime());
            paper.setPaperTitle(submissionPaper.getSubmissionPaperTitle());
            paper.setPaperAuthors(submissionPaper.getSubmissionPaperAuthors());
            paper.setPaperAbstract(submissionPaper.getSubmissionPaperAbstract());

            PaperRoughly paperRoughly = searchService.getPaperRoughly(paper,1);

            List<Files> fileList = filesMapper.selectBatchIds(fileIdList);
            SubmissionInfo submissionInfo = new SubmissionInfo();
            submissionInfo.setSubmission(s);
            submissionInfo.setPaperRoughly(paperRoughly);
            submissionInfo.setPaperDetail(paperDetail);
            submissionInfo.setFileList(fileList);

            submissionInfoList.add(submissionInfo);
        }
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("currentPage",submissionPage.getCurrent());
        hashMap.put("pageSize",submissionPage.getSize());
        hashMap.put("pageTotal",submissionPage.getTotal());
        hashMap.put("submissionInfo",submissionInfoList);
        return ResponseResult.success(hashMap);

    }

    @Override
    public Object getVerifierList() {
        LambdaQueryWrapper<AdminVerifier> adminVerifierLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminVerifierLambdaQueryWrapper.eq(AdminVerifier::getIsAdmin,0);
        List<AdminVerifier> adminVerifierList = adminVerifierMapper.selectList(adminVerifierLambdaQueryWrapper);
        return ResponseResult.success(adminVerifierList);
    }

}




