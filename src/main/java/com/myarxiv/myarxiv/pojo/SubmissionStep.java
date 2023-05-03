package com.myarxiv.myarxiv.pojo;

import lombok.Data;

import java.util.List;

@Data
public class SubmissionStep {

    private Integer userId;
    private Integer submissionId;
    private List<PriAndSec> categoryList;


}

