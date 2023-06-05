package com.myarxiv.myarxiv.pojo;

import com.myarxiv.myarxiv.domain.Submission;
import com.myarxiv.myarxiv.domain.SubmissionPaper;
import lombok.Data;

@Data
public class SubmissionMapById {
    private Submission submission;
    private SubmissionPaper submissionPaper;
}
