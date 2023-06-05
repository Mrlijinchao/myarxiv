package com.myarxiv.myarxiv.controller;

import com.myarxiv.myarxiv.service.EndorsementService;
import com.myarxiv.myarxiv.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private EndorsementService endorsementService;

    @PostMapping("/needEndorsement")
    public Object needEndorsement(@RequestBody Map<String,Integer> endorsementReq){
        Integer userId = endorsementReq.get("userId");
        Integer submissionId = endorsementReq.get("submissionId");
        log.info("userId: {}, submissionId: {}",userId, submissionId);
        return endorsementService.endorsementQuest(userId,submissionId);
    }

    @PostMapping("/endorse")
    public Object endorse(@RequestBody Map<String, String> endorsementAuth){
        int userId = Integer.parseInt(endorsementAuth.get("userId"));
        String endorsementCode = endorsementAuth.get("endorsementCode");
        log.info("userId: {}, endorsementCode: {}",userId,endorsementCode);
        return endorsementService.endorse(userId,endorsementCode);
    }

}
