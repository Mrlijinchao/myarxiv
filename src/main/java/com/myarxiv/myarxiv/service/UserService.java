package com.myarxiv.myarxiv.service;

import com.myarxiv.myarxiv.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myarxiv.myarxiv.pojo.UserDetailObj;

/**
* @author 时之始
* @description 针对表【user】的数据库操作Service
* @createDate 2023-06-04 10:09:40
*/
public interface UserService extends IService<User> {
    public User  saveUserInfo(User user);
    public Object checkLogin(User user);
    public Object getAllUser();
    public UserDetailObj getUserDetail(User user);
//    public User getUser(User user);
}
