package com.myarxiv.myarxiv.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.domain.AdminVerifier;
import com.myarxiv.myarxiv.domain.Submission;
import com.myarxiv.myarxiv.domain.User;
import com.myarxiv.myarxiv.service.AdminVerifierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/adminVerifier")
public class AdminVerifierController {

    @Resource
    private AdminVerifierService adminVerifierService;

    @PostMapping("/login")
    public Object login(AdminVerifier adminVerifier){
        return adminVerifierService.checkLogin(adminVerifier);
    }


    @PostMapping("/addVerifier")
    public Object addVerifier(AdminVerifier adminVerifier){
        AdminVerifier av = adminVerifierService.getOne(new QueryWrapper<AdminVerifier>()
                .eq("admin_verifier_account", adminVerifier.getAdminVerifierAccount()));

        if(av != null){
            return ResponseResult.fail("此账号已存在！建议换一个！", StatusCode.ERROR.getCode());
        }

        return adminVerifierService.saveVerifierInfo(adminVerifier);
    }


    @PostMapping("/approve")
    public Object approve(Submission submission){
        return adminVerifierService.savePaperInfo(submission);
    }

    @PostMapping("/onHold")
    public Object onHold(Submission submission){
        return adminVerifierService.changeStatus(submission);
    }



}
