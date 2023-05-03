package com.myarxiv.myarxiv.pojo;

import lombok.Data;

@Data
public class GetPaper {

    private Integer categorySecondaryId;

    private Integer pageSize;

    private Integer pageNum;

}
