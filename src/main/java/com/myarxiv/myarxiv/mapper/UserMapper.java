package com.myarxiv.myarxiv.mapper;

import com.myarxiv.myarxiv.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myarxiv.myarxiv.pojo.UserDetailObj;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author 时之始
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-06-04 10:09:40
* @Entity com.myarxiv.myarxiv.domain.User
*/
public interface UserMapper extends BaseMapper<User> {
}




