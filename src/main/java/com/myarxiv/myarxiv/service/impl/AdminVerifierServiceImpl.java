package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myarxiv.myarxiv.common.RedisUtils;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.domain.*;
import com.myarxiv.myarxiv.domain.relation.PaperCategory;
import com.myarxiv.myarxiv.domain.relation.PaperFile;
import com.myarxiv.myarxiv.domain.relation.UserPaper;
import com.myarxiv.myarxiv.mapper.*;
import com.myarxiv.myarxiv.mapper.relation.PaperCategoryMapper;
import com.myarxiv.myarxiv.mapper.relation.PaperFileMapper;
import com.myarxiv.myarxiv.mapper.relation.UserPaperMapper;
import com.myarxiv.myarxiv.service.AdminVerifierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Override
    public Object checkLogin(AdminVerifier adminVerifier) {

        if(adminVerifier.getAdminVerifierAccount() != null){
            AdminVerifier adminVerifierAccount = adminVerifierMapper.selectOne(new QueryWrapper<AdminVerifier>()
                    .eq("admin_verifier_account", adminVerifier.getAdminVerifierAccount()));
            if(adminVerifierAccount == null){
                return ResponseResult.fail("账号不存在！", StatusCode.PRECONDITION_FAILED.getCode());
            }

            //MD5加密
            String md5Password = DigestUtils.md5DigestAsHex(adminVerifier.getAdminVerifierPassword().getBytes());
            if (!md5Password.equals(adminVerifierAccount.getAdminVerifierPassword())) {
                return ResponseResult.fail("密码错误！",StatusCode.ERROR.getCode());
            }

            adminVerifier = adminVerifierAccount;

        }

//        String token = TokenUtil.generateToken(user);
        String token = "Bearer " + UUID.randomUUID().toString().replaceAll("-", "");
        redisUtils.set(token,adminVerifier.getAdminVerifierId(),1, TimeUnit.HOURS);

        return ResponseResult.returnWithToken(adminVerifier, token);

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
    public Object changeStatus(Submission submission) {

        LambdaUpdateWrapper<Submission> submissionLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        submissionLambdaUpdateWrapper.eq(Submission::getSubmissionId,submission.getSubmissionId())
                .set(Submission::getSubmissionStatus,3);
        submissionMapper.update(null,submissionLambdaUpdateWrapper);

        return ResponseResult.success("成功挂起！");
    }
}




