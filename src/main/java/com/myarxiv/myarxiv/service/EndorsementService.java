package com.myarxiv.myarxiv.service;

import com.myarxiv.myarxiv.domain.Endorsement;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 时之始
* @description 针对表【endorsement】的数据库操作Service
* @createDate 2023-04-30 20:02:45
*/
public interface EndorsementService extends IService<Endorsement> {
    public Object endorsementQuest(Integer userId,Integer submissionId);
    public Object endorse(Integer userId,String endorsementCode);
}
