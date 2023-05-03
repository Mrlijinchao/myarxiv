package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myarxiv.myarxiv.domain.Country;
import com.myarxiv.myarxiv.service.CountryService;
import com.myarxiv.myarxiv.mapper.CountryMapper;
import org.springframework.stereotype.Service;

/**
* @author 时之始
* @description 针对表【country】的数据库操作Service实现
* @createDate 2023-04-22 22:41:46
*/
@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, Country>
    implements CountryService{

}




