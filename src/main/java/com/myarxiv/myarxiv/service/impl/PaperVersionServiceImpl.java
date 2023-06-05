package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myarxiv.myarxiv.domain.PaperVersion;
import com.myarxiv.myarxiv.service.PaperVersionService;
import com.myarxiv.myarxiv.mapper.PaperVersionMapper;
import org.springframework.stereotype.Service;

/**
* @author 时之始
* @description 针对表【paper_version】的数据库操作Service实现
* @createDate 2023-06-02 15:05:09
*/
@Service
public class PaperVersionServiceImpl extends ServiceImpl<PaperVersionMapper, PaperVersion>
    implements PaperVersionService{

}




