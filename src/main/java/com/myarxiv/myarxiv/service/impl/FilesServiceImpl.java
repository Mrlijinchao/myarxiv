package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myarxiv.myarxiv.domain.Files;
import com.myarxiv.myarxiv.service.FilesService;
import com.myarxiv.myarxiv.mapper.FilesMapper;
import org.springframework.stereotype.Service;

/**
* @author 时之始
* @description 针对表【files】的数据库操作Service实现
* @createDate 2023-04-29 19:48:18
*/
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files>
    implements FilesService{

}




