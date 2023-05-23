package com.myarxiv.myarxiv.controller;

import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.domain.License;
import com.myarxiv.myarxiv.domain.PaperDetail;
import com.myarxiv.myarxiv.domain.SubmissionPaper;
import com.myarxiv.myarxiv.pojo.PriAndSec;
import com.myarxiv.myarxiv.pojo.SubmissionStep;
import com.myarxiv.myarxiv.service.LicenseService;
import com.myarxiv.myarxiv.service.SubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user/submit")
public class SubmissionController {

    @Resource
    private SubmissionService submissionService;

    @Resource
    private LicenseService licenseService;

    @PostMapping("/step1")
    public Object step1(Integer userId,Integer subjectId,Integer categoryPrimaryId,
                        Integer categorySecondaryId,Integer licenseId,Integer submissionId){

        log.info("userId:{},subjectId:{},categoryPrimaryId:{},categorySecondaryId:{},licenseId:{},submissionId:{}",
                userId,subjectId,categoryPrimaryId,categorySecondaryId,licenseId,submissionId);

        // 判断 submissionId是否为 null
        if(submissionId == null){
            Object obj = submissionService.saveInfoFirst(userId, subjectId, categoryPrimaryId, categorySecondaryId, licenseId);
            return ResponseResult.success(obj);
        }else{
            Object obj = submissionService.saveInfo(userId, subjectId, categoryPrimaryId, categorySecondaryId, licenseId, submissionId);
            return ResponseResult.success(obj);
        }

    }

    @PostMapping("/step2")
    public Object step2(Integer userId,Integer submissionId, @RequestParam("files") MultipartFile[] files){

        if(files.length == 0) return ResponseResult.fail("上传的文件数量不能未0", StatusCode.ERROR.getCode());

        return submissionService.saveFiles(userId,submissionId,files);
    }

    @PostMapping("/step4")
    public Object step4(SubmissionPaper submissionPaper, PaperDetail paperDetail,
                        Integer submissionId,Integer userId){
        log.info("submissionPaper:{},submissionId:{}",submissionPaper,submissionId);
        return submissionService.savePaperInfo(submissionPaper,paperDetail,submissionId,userId);
    }

    @GetMapping("/step5/review")
    public Object step5Review(Integer submissionId){
        return submissionService.searchInfo(submissionId);
    }

    @PostMapping("/step5")
    public Object step5(@RequestBody SubmissionStep submissionStep) {
        log.info("submissionStep5:{}",submissionStep);
        return submissionService.saveLastSubmit(submissionStep);
    }

    @GetMapping("/getLicense")
    public Object getLicense(){
        List<License> list = licenseService.list();
        return ResponseResult.success(list);
    }

}
