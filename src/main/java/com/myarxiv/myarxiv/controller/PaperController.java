package com.myarxiv.myarxiv.controller;

import com.myarxiv.myarxiv.domain.Paper;
import com.myarxiv.myarxiv.service.PaperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/paper")
@RestController
public class PaperController {

    @Resource
    private PaperService paperService;

    @GetMapping("/getPaperInfoById")
    public Object getPaperInfoById(Integer paperId){
        return paperService.getPaperInfoById(paperId);
    }

}
