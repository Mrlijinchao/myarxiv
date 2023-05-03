package com.myarxiv.myarxiv.service;

import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 时之始
* @description 针对表【user】的数据库操作Service
* @createDate 2023-04-22 22:43:39
*/
public interface UserService extends IService<User> {
    public User  saveUserInfo(User user);
    public Object checkLogin(User user);
//    public User getUser(User user);
}
