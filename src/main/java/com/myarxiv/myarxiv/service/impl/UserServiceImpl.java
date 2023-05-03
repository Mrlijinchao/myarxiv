package com.myarxiv.myarxiv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myarxiv.myarxiv.common.RedisUtils;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.common.TokenUtil;
import com.myarxiv.myarxiv.domain.User;
import com.myarxiv.myarxiv.service.UserService;
import com.myarxiv.myarxiv.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
* @author 时之始
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-04-22 22:43:39
*/

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserMapper userMapper;

    @Transactional
    @Override
    public User saveUserInfo(User user) {

        user.setUserCreateTime(new Date());
        //MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes());

        user.setUserPassword(md5Password);

        int i = userMapper.insert(user);

        if(i == 0){
            return null;
        }

        return user;
    }

    @Override
    public Object checkLogin(User user) {
        if(user.getUserAccount() != null){
            User user_account = userMapper.selectOne(new QueryWrapper<User>().eq("user_account", user.getUserAccount()));
            if(user_account == null){
                return ResponseResult.fail("账号不存在！", StatusCode.PRECONDITION_FAILED.getCode());
            }

            //MD5加密
            String md5Password = DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes());
            if (!md5Password.equals(user_account.getUserPassword())) {
                return ResponseResult.fail("密码错误！",StatusCode.ERROR.getCode());
            }

            user = user_account;

        }else if(user.getUserEmail() != null){
            User user_email = userMapper.selectOne(new QueryWrapper<User>().eq("user_email", user.getUserEmail()));
            if(user_email == null){
                return ResponseResult.fail("邮箱号不存在！",StatusCode.PRECONDITION_FAILED.getCode());
            }

            //MD5加密
            String md5Password = DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes());
            if (!md5Password.equals(user_email.getUserPassword())) {
                return ResponseResult.fail("密码错误！",StatusCode.ERROR.getCode());
            }

            user = user_email;

        } else if(user.getUserEmail() == null && user.getUserAccount() == null){
            return ResponseResult.fail("你的账号名或者邮箱号为空！",StatusCode.PRECONDITION_FAILED.getCode());
        }

//        String token = TokenUtil.generateToken(user);
        String token = "Bearer " + UUID.randomUUID().toString().replaceAll("-", "");
        redisUtils.set(token,user.getUserId(),1, TimeUnit.HOURS);

        return ResponseResult.returnWithToken(user.getUserId(), token);

    }

//    @Override
//    public User getUser(User user) {
//
//        user = userMapper.selectById(user.getUserId());
//
//        return user;
//    }

}




