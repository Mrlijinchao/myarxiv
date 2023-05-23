package com.myarxiv.myarxiv.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.SearchOrder;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.domain.AdminVerifier;
import com.myarxiv.myarxiv.domain.Submission;
import com.myarxiv.myarxiv.domain.relation.VerifierSubmission;
import com.myarxiv.myarxiv.service.AdminVerifierService;
import com.myarxiv.myarxiv.service.SubmissionService;
import com.myarxiv.myarxiv.service.relation.VerifierSubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/adminVerifier")
public class AdminVerifierController {

    @Resource
    private AdminVerifierService adminVerifierService;

    @Resource
    private SubmissionService submissionService;

    @Resource
    private VerifierSubmissionService verifierSubmissionService;

    @PostMapping("/login")
    public Object login(@RequestBody AdminVerifier adminVerifier){
        log.info("AdminVerifier: {}",adminVerifier);
        return adminVerifierService.checkLogin(adminVerifier);
    }


    @PostMapping("/addVerifier")
    public Object addVerifier(@RequestBody  AdminVerifier adminVerifier){
        log.info("adminVerifier: {}",adminVerifier);
        AdminVerifier av = adminVerifierService.getOne(new QueryWrapper<AdminVerifier>()
                .eq("admin_verifier_account", adminVerifier.getAdminVerifierAccount()));

        if(av != null){
            return ResponseResult.fail("此账号已存在！建议换一个！", StatusCode.ERROR.getCode());
        }

        return adminVerifierService.saveVerifierInfo(adminVerifier);
    }

    @Transactional
    @PostMapping("/removeVerifier")
    public Object removeVerifier(@RequestBody AdminVerifier adminVerifier){
        if (adminVerifier.getAdminVerifierId() == null){
            return ResponseResult.fail("审核员Id为null",StatusCode.ERROR.getCode());
        }
        boolean b = adminVerifierService.removeById(adminVerifier.getAdminVerifierId());
        if (!b){
            ResponseResult.fail("删除失败！",StatusCode.ERROR.getCode());
        }
        return ResponseResult.success("删除成功！");
    }

    @PostMapping("/changeUserInfo")
    public Object changeUserInfo(@RequestBody AdminVerifier adminVerifier){
        log.info("adminVerifier: {}",adminVerifier);
        adminVerifierService.updateById(adminVerifier);
        return ResponseResult.success("修改密码成功！");
    }

    @Transactional
    @PostMapping("/editVerifier")
    public Object editVerifier(@RequestBody  AdminVerifier adminVerifier){
        log.info("adminVerifier: {}", adminVerifier);
        boolean b = adminVerifierService.updateById(adminVerifier);
        if (b){
            return ResponseResult.success("修改审核员信息成功！");
        }
        return ResponseResult.fail("修改审核员信息失败！",StatusCode.ERROR.getCode());
    }

    @GetMapping("/getVerifierList")
    public Object getVerifierList(){
        adminVerifierService.getVerifierList();
        return adminVerifierService.getVerifierList();
    }

    @Transactional
    @PostMapping("/approve")
    public Object approve(@RequestBody VerifierSubmission verifierSubmission){
        if(verifierSubmission.getSubmissionId() == null){
            return ResponseResult.fail("submissionId为null",StatusCode.ERROR.getCode());
        }
        Submission submission = new Submission();
        submission.setSubmissionId(verifierSubmission.getSubmissionId());
        log.info("submission: {}",submission);
        adminVerifierService.saveVerifierAndSubmissionInfo(verifierSubmission);
        return adminVerifierService.savePaperInfo(submission);
    }

    @PostMapping("/onHold")
    public Object onHold(@RequestBody VerifierSubmission verifierSubmission){
        if(verifierSubmission.getSubmissionId() == null){
            return ResponseResult.fail("submissionId为null",StatusCode.ERROR.getCode());
        }
        log.info("VerifierId: {}",verifierSubmission.getVerifierId());
        return adminVerifierService.changeStatus(verifierSubmission);
    }

    @Transactional
    @PostMapping("/annulOnHold")
    public Object annulOnHold(@RequestBody Submission submission){
        log.info("submissionId: {}",submission.getSubmissionId());
        if(submission.getSubmissionId() == null){
            return ResponseResult.fail("submissionId为null",StatusCode.ERROR.getCode());
        }
        LambdaUpdateWrapper<VerifierSubmission> verifierSubmissionLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        verifierSubmissionLambdaUpdateWrapper.eq(VerifierSubmission::getSubmissionId,submission.getSubmissionId());
        verifierSubmissionService.remove(verifierSubmissionLambdaUpdateWrapper);
        LambdaUpdateWrapper<Submission> submissionLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        submissionLambdaUpdateWrapper.eq(Submission::getSubmissionId,submission.getSubmissionId())
                .set(Submission::getSubmissionStatus,1);
        boolean update = submissionService.update(submissionLambdaUpdateWrapper);
        if(update){
            return ResponseResult.success("取消onHold成功");
        }
        return ResponseResult.success("取消onHold失败");
    }

    @GetMapping("/getUnreviewedSubmission")
    public Object getUnreviewedSubmission(Integer pageSize,Integer orderByCode,Integer pageNum){
        if(pageSize == null || pageSize < 1){
            pageSize = 10;
        }
        if (orderByCode == null || (orderByCode != SearchOrder.NEW.getCode() && orderByCode != SearchOrder.OLD.getCode())){
            orderByCode = SearchOrder.OLD.getCode();
        }

        return adminVerifierService.getUnreviewedSubmission(pageSize,orderByCode,pageNum);
    }

    @GetMapping("/getReviewedSubmission")
    public Object getReviewedSubmission(Integer pageSize,Integer orderByCode,Integer pageNum){
        if(pageSize == null || pageSize < 1){
            pageSize = 10;
        }
        if (orderByCode == null || (orderByCode != SearchOrder.NEW.getCode() && orderByCode != SearchOrder.OLD.getCode())){
            orderByCode = SearchOrder.OLD.getCode();
        }

        return adminVerifierService.getReviewedSubmission(pageSize,orderByCode,pageNum);
    }

    @GetMapping("/getOnHoldSubmission")
    public Object getOnHoldSubmission(Integer pageSize,Integer orderByCode,Integer pageNum){
        if(pageSize == null || pageSize < 1){
            pageSize = 10;
        }
        if (orderByCode == null || (orderByCode != SearchOrder.NEW.getCode() && orderByCode != SearchOrder.OLD.getCode())){
            orderByCode = SearchOrder.OLD.getCode();
        }

        return adminVerifierService.getOnHoldSubmission(pageSize,orderByCode,pageNum);
    }

    @GetMapping("/getReviewedSubmissionByVerifierId")
    public Object getReviewedSubmissionByVerifierId(Integer pageSize,Integer orderByCode,Integer pageNum, Integer verifierId){
        log.info("verifierId: {}",verifierId);
        log.info("pageSize:{} pageNum:{}",pageSize,pageNum);
        return adminVerifierService.getSubmissionByVerifierIdAndStatus(pageSize,orderByCode,pageNum,verifierId,0);
    }

    @GetMapping("/getOnHoldSubmissionByVerifierId")
    public Object getOnHoldSubmissionByVerifierId(Integer pageSize,Integer orderByCode,Integer pageNum, Integer verifierId){
        log.info("verifierId: {}",verifierId);
        return adminVerifierService.getSubmissionByVerifierIdAndStatus(pageSize,orderByCode,pageNum,verifierId,1);
    }




}
