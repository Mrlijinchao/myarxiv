package com.myarxiv.myarxiv.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PaperRoughly {
    private Integer paperId;
    private String subject;
    private String license;
    private String paperTitle;
    private String paperAbstract;
    private String paperIdentifier;
    private String paperAuthors;
    private String paperComments;
    private Date paperCreateTime;
    private Date paperUpdateTime;
    private Integer paperStatus;

    private List<PriAndSecDetail> priAndSecDetailList;

}
