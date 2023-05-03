package com.myarxiv.myarxiv.service.impl.relation;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myarxiv.myarxiv.domain.relation.PaperFile;
import com.myarxiv.myarxiv.service.relation.PaperFileService;
import com.myarxiv.myarxiv.mapper.relation.PaperFileMapper;
import org.springframework.stereotype.Service;

/**
* @author 时之始
* @description 针对表【paper_file】的数据库操作Service实现
* @createDate 2023-04-29 19:40:34
*/
@Service
public class PaperFileServiceImpl extends ServiceImpl<PaperFileMapper, PaperFile>
    implements PaperFileService{

}




