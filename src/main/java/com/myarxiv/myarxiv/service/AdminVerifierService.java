package com.myarxiv.myarxiv.service;

import com.myarxiv.myarxiv.domain.AdminVerifier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myarxiv.myarxiv.domain.Submission;
import com.myarxiv.myarxiv.domain.relation.VerifierSubmission;
/**
 * @author 时之始
 * @description 针对表【admin_verifier】的数据库操作Service
 * @createDate 2023-05-01 13:15:44
 */
public interface AdminVerifierService extends IService<AdminVerifier> {
    public Object checkLogin(AdminVerifier adminVerifier);
    public Object saveVerifierInfo(AdminVerifier adminVerifier);

    public Object savePaperInfo(Submission submission);
    public Object changeStatus(VerifierSubmission verifierSubmission);

    public Object getUnreviewedSubmission(Integer pageSize,Integer orderByCode,Integer pageNum);

    public Object getReviewedSubmission(Integer pageSize,Integer orderByCode,Integer pageNum);

    public Object getOnHoldSubmission(Integer pageSize,Integer orderByCode,Integer pageNum);

    public Object saveVerifierAndSubmissionInfo(VerifierSubmission verifierSubmission);

    public Object getSubmissionByVerifierIdAndStatus(Integer pageSize,Integer orderByCode,
                                                     Integer pageNum, Integer verifierId, Integer status);

    public Object getVerifierList();

    public Object getUnreviewedSubmissionBySubjectId(Integer pageSize,Integer pageNum, Integer subjectId);
}
