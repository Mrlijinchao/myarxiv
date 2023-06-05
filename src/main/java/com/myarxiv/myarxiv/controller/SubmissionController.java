package com.myarxiv.myarxiv.controller;

import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.domain.License;
import com.myarxiv.myarxiv.domain.PaperDetail;
import com.myarxiv.myarxiv.domain.SubmissionPaper;
import com.myarxiv.myarxiv.pojo.PaperUpdateJson;
import com.myarxiv.myarxiv.pojo.PriAndSec;
import com.myarxiv.myarxiv.pojo.SubmissionStep;
import com.myarxiv.myarxiv.pojo.SubmitStep4Info;
import com.myarxiv.myarxiv.service.LicenseService;
import com.myarxiv.myarxiv.service.SubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/user/submit")
public class SubmissionController {

    @Resource
    private SubmissionService submissionService;

    @Resource
    private LicenseService licenseService;

    @PostMapping("/step1")
    public Object step1(@RequestBody Map<String, Integer> submitStep1Info){
//    public Object step1(Integer userId,Integer subjectId,Integer categoryPrimaryId,
//                        Integer categorySecondaryId,Integer licenseId,Integer submissionId){
        Integer userId = submitStep1Info.get("userId");
        Integer subjectId = submitStep1Info.get("subjectId");
        Integer categoryPrimaryId = submitStep1Info.get("categoryPrimaryId");
        Integer categorySecondaryId = submitStep1Info.get("categorySecondaryId");
        Integer licenseId = submitStep1Info.get("licenseId");
        Integer submissionId = submitStep1Info.get("submissionId");
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

        if(files.length == 0) return ResponseResult.fail("上传的文件数量不能为0", StatusCode.ERROR.getCode());

        return submissionService.saveFiles(userId,submissionId,files);
    }

    @PostMapping("/step4")
    public Object step4(@RequestBody SubmitStep4Info submitStep4Info){
        SubmissionPaper submissionPaper = submitStep4Info.getSubmissionPaper();
        PaperDetail paperDetail = submitStep4Info.getPaperDetail();
        Integer submissionId = submitStep4Info.getSubmissionId();
        Integer userId = submitStep4Info.getUserId();
        log.info("submissionPaper:{},paperDetail:{},submissionId:{},userId:{}",submissionPaper,paperDetail,submissionId,userId);
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

    @PostMapping("/upDatePaper")
    public Object upDatePaper(@RequestBody PaperUpdateJson paperUpdateJson){
        log.info("submissionStep: {}  paperId: {}",paperUpdateJson.getSubmissionStep(),paperUpdateJson.getPaperId());
        return submissionService.upDatePaper(paperUpdateJson);
    }

    @GetMapping("/getSubmissionInfoById")
    public Object getSubmissionInfoById(Integer submissionId){
        if(submissionId == null){
            return ResponseResult.fail("submissionId为null",StatusCode.ERROR.getCode());
        }
        return submissionService.getSubmissionInfoById(submissionId);
    }

}
