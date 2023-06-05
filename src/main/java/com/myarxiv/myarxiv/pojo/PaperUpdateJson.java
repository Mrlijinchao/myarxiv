package com.myarxiv.myarxiv.pojo;

import lombok.Data;

@Data
public class PaperUpdateJson {
    private SubmissionStep submissionStep;
    private Integer paperId;
}
