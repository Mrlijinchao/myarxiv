package com.myarxiv.myarxiv.pojo;

import com.myarxiv.myarxiv.domain.PaperDetail;
import com.myarxiv.myarxiv.domain.SubmissionPaper;
import lombok.Data;

@Data
public class SubmitStep4Info {

    private SubmissionPaper submissionPaper;

    private PaperDetail paperDetail;

    private Integer submissionId;

    private Integer userId;

}
