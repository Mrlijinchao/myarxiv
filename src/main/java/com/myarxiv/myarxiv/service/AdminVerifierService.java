package com.myarxiv.myarxiv.service;

import com.myarxiv.myarxiv.domain.AdminVerifier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myarxiv.myarxiv.domain.Submission;

/**
* @author 时之始
* @description 针对表【admin_verifier】的数据库操作Service
* @createDate 2023-05-01 13:15:44
*/
public interface AdminVerifierService extends IService<AdminVerifier> {
    public Object checkLogin(AdminVerifier adminVerifier);
    public Object saveVerifierInfo(AdminVerifier adminVerifier);

    public Object savePaperInfo(Submission submission);
    public Object changeStatus(Submission submission);

}
