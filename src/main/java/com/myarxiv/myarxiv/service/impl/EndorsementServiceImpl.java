package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.domain.*;
import com.myarxiv.myarxiv.domain.relation.UserPaper;
import com.myarxiv.myarxiv.mail.SendMail;
import com.myarxiv.myarxiv.mapper.*;
import com.myarxiv.myarxiv.mapper.relation.UserPaperMapper;
import com.myarxiv.myarxiv.service.EndorsementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
* @author 时之始
* @description 针对表【endorsement】的数据库操作Service实现
* @createDate 2023-04-30 20:02:45
*/
@Service
public class EndorsementServiceImpl extends ServiceImpl<EndorsementMapper, Endorsement>
    implements EndorsementService{

    @Resource
    private UserMapper userMapper;

    @Resource
    private EndorsementMapper endorsementMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private UserPaperMapper userPaperMapper;

    @Resource
    private SubmissionMapper submissionMapper;

    @Resource
    private SubmissionPaperMapper submissionPaperMapper;

    @Override
    public Object endorsementQuest(Integer userId, Integer submissionId) {

        String endorsementCode = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        User user = userMapper.selectById(userId);
        LambdaUpdateWrapper<Endorsement> endorsementLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        endorsementLambdaUpdateWrapper.eq(Endorsement::getUserId,userId).eq(Endorsement::getSubmissionId,submissionId)
                .eq(Endorsement::getEndorsementStatus,0).set(Endorsement::getEndorsementCode,endorsementCode);
        endorsementMapper.update(null, endorsementLambdaUpdateWrapper);

        Subject subject = subjectMapper.selectById(user.getSubjectId());
        // 发送背书邮件
        String endorsementContent = SendMail.getEndorsementContent(user.getUserName(),
                subject.getSubjectName() , endorsementCode);
        try {
            SendMail.sendMail(user.getUserEmail(),endorsementContent,"背书验证邮件");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseResult.fail("邮件发送失败！",StatusCode.ERROR.getCode());
        }

        LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("userEmail",user.getUserEmail());
        hashMap.put("endorsementCode",endorsementCode);

        return hashMap;
    }

    @Override
    public Object endorse(Integer userId, String endorsementCode) {

        User user = userMapper.selectById(userId);
        Integer subjectId = user.getSubjectId();
        LambdaQueryWrapper<UserPaper> userPaperLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userPaperLambdaQueryWrapper.eq(UserPaper::getUserId,user.getUserId());
        Integer integer = userPaperMapper.selectCount(userPaperLambdaQueryWrapper);
        System.out.println("count:"+integer);

        LambdaQueryWrapper<Endorsement> endorsementLambdaQueryWrapper = new LambdaQueryWrapper<>();
        endorsementLambdaQueryWrapper.eq(Endorsement::getEndorsementStatus,0).eq(Endorsement::getEndorsementCode,endorsementCode);
        Endorsement endorsement = endorsementMapper.selectOne(endorsementLambdaQueryWrapper);
        if(endorsement == null){
            return ResponseResult.fail("背书码已失效！",StatusCode.ERROR.getCode());
        }

        Submission submission = submissionMapper.selectById(endorsement.getSubmissionId());
        SubmissionPaper submissionPaper = submissionPaperMapper.selectById(submission.getSubmissionPaperId());

        if(subjectId != submissionPaper.getSubmissionSubjectId() || integer < 3){
            return ResponseResult.fail("您不具有背书的资格！",StatusCode.ERROR.getCode());
        }

        LambdaUpdateWrapper<Endorsement> endorsementLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        endorsementLambdaUpdateWrapper.eq(Endorsement::getEndorsementStatus,0).eq(Endorsement::getEndorsementCode,endorsementCode)
                .set(Endorsement::getEndorsementStatus,1);
        endorsementMapper.update(null,endorsementLambdaUpdateWrapper);

        return ResponseResult.success("背书成功！");
    }
}




