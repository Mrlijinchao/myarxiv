package com.myarxiv.myarxiv.pojo;

import com.myarxiv.myarxiv.domain.Files;
import com.myarxiv.myarxiv.domain.Paper;
import com.myarxiv.myarxiv.domain.PaperDetail;
import lombok.Data;

import java.util.List;

@Data
public class PaperInfo {
    private PaperRoughly paperRoughly;
    private PaperDetail paperDetail;
    private List<Files> fileList;
}
