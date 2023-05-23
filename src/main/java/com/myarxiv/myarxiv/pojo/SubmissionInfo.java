package com.myarxiv.myarxiv.pojo;

import com.myarxiv.myarxiv.domain.Files;
import com.myarxiv.myarxiv.domain.PaperDetail;
import com.myarxiv.myarxiv.domain.Submission;
import com.myarxiv.myarxiv.domain.SubmissionPaper;
import lombok.Data;

import java.util.List;

@Data
public class SubmissionInfo {

    private Submission submission;
    private PaperRoughly paperRoughly;
    private PaperDetail paperDetail;
    private List<Files> fileList;

}
