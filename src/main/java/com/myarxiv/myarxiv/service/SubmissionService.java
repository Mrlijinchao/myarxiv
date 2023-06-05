package com.myarxiv.myarxiv.service;

import com.myarxiv.myarxiv.domain.PaperDetail;
import com.myarxiv.myarxiv.domain.Submission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myarxiv.myarxiv.domain.SubmissionPaper;
import com.myarxiv.myarxiv.pojo.PaperUpdateJson;
import com.myarxiv.myarxiv.pojo.SubmissionStep;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

/**
* @author 时之始
* @description 针对表【submission】的数据库操作Service
* @createDate 2023-04-29 09:40:53
*/
public interface SubmissionService extends IService<Submission> {
    public Object saveInfoFirst(Integer userId,Integer subjectId,Integer categoryPrimaryId,
                                                      Integer categorySecondaryId,Integer licenseId);

    public Object saveInfo(Integer userId,Integer subjectId,Integer categoryPrimaryId,
                           Integer categorySecondaryId,Integer licenseId,Integer submissionId);

    public Object saveFiles(Integer userId,Integer submissionId, MultipartFile[] files);

    public Object savePaperInfo(SubmissionPaper submissionPaper, PaperDetail paperDetail,
                                Integer submissionId, Integer userId);

    public Object searchInfo(Integer submissionId);

    public Object saveLastSubmit(SubmissionStep submissionStep);

    public Object upDatePaper(PaperUpdateJson paperUpdateJson);

    public Object getSubmissionInfoById(Integer submissionId);

}
