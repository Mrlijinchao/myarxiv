package com.myarxiv.myarxiv.controller;

import com.myarxiv.myarxiv.service.EndorsementService;
import com.myarxiv.myarxiv.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private EndorsementService endorsementService;

    @PostMapping("/needEndorsement")
    public Object needEndorsement(Integer userId,Integer submissionId){
        return endorsementService.endorsementQuest(userId,submissionId);
    }

    @PostMapping("/endorse")
    public Object endorse(Integer userId,String endorsementCode){
        return endorsementService.endorse(userId,endorsementCode);
    }

}
