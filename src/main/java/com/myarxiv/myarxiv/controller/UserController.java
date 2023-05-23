package com.myarxiv.myarxiv.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.myarxiv.myarxiv.common.RedisUtils;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;

import com.myarxiv.myarxiv.domain.Paper;
import com.myarxiv.myarxiv.domain.Submission;
import com.myarxiv.myarxiv.domain.User;
import com.myarxiv.myarxiv.service.PaperService;
import com.myarxiv.myarxiv.service.SubmissionService;
import com.myarxiv.myarxiv.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserService userService;

    @Resource
    private SubmissionService submissionService;

    @Resource
    private PaperService paperService;

    @PostMapping("/login")
    public Object userLogin(User user){

        log.info("loginUser:{}",user);

        return userService.checkLogin(user);
    }

    @GetMapping("/logout")
    public Object userLogout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        //删除redis的token
        redisUtils.del(token);
        return ResponseResult.success("成功退出！");
    }

    @PostMapping("/register")
    public Object userRegister(User user, String verificationCode){
        log.info("registerUser:{}",user);
        log.info("验证码：{}",verificationCode);

        String code = redisUtils.get(user.getUserEmail()).toString();
        if(!code.equals(verificationCode)){
            return ResponseResult.fail("验证码错误，注册失败！",StatusCode.ERROR.getCode());
        }


        User user1 = userService.getOne(new QueryWrapper<User>().eq("user_account", user.getUserAccount()));

        if(user1 != null){
            return ResponseResult.fail("此账号名已经存在，建议换一个！", StatusCode.ERROR.getCode());
        }

        User user2 = userService.saveUserInfo(user);

        if(user2 == null){
            return ResponseResult.fail("注册失败，请稍后重试！",StatusCode.SUCCESS.getCode());
        }

//        String token = TokenUtil.generateToken(user2);
        String token = "Bearer " + UUID.randomUUID().toString().replaceAll("-", "");
        redisUtils.set(token,user2.getUserId(),1, TimeUnit.HOURS);

        return ResponseResult.returnWithToken(user2.getUserId(), token);
    }

    @GetMapping("/info")
    public Object getUserInfo(User user){
        // 获取用户信息
        user = userService.getById(user.getUserId());
        // 获取提交信息
        LambdaQueryWrapper<Submission> submissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        submissionLambdaQueryWrapper.eq(Submission::getUserId,user.getUserId()).in(Submission::getSubmissionStatus,0,1);
        List<Submission> submissionList = submissionService.list(submissionLambdaQueryWrapper);
        // 获取论文信息
        List<Object> paperList = paperService.getPaperListByUserId(user.getUserId());

        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("user",user);
        hashMap.put("submissionList",submissionList);
        hashMap.put("paperList",paperList);

        return ResponseResult.success(hashMap);
    }

    @Transactional
    @PostMapping("/changeUserInfo")
    public Object changeUserInfo(User user){
        boolean b = userService.updateById(user);
        if(b){
            return ResponseResult.success("修改用户信息成功！");
        }
        return ResponseResult.success("修改用户信息失败！");
    }

    @Transactional
    @PostMapping("/changeUserPassword")
    public Object changeUserPassword(Integer userId,String oldPassword,String newPassword){
        log.info("userId:{} oldPassword:{} newPassword:{}",userId,oldPassword,newPassword);
        if(userId == null){
            return ResponseResult.fail("用户id为空",StatusCode.ERROR.getCode());
        }
        User user1 = userService.getById(userId);

        //MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        if (!md5Password.equals(user1.getUserPassword())) {
            return ResponseResult.fail("原密码错误！",StatusCode.ERROR.getCode());
        }
        String s = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userLambdaUpdateWrapper.eq(User::getUserId,userId).set(User::getUserPassword,s);
        boolean update = userService.update(userLambdaUpdateWrapper);
        if(update){
            return ResponseResult.success("修改密码成功！");
        }

        return ResponseResult.success("修改密码失败！");

    }

    @Transactional
    @PostMapping("/changeUserEmail")
    public Object changeUserEmail(User user){

        if (user.getUserId() == null){
            return ResponseResult.fail("用户id为空",StatusCode.ERROR.getCode());
        }
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userLambdaUpdateWrapper.eq(User::getUserId,user.getUserId()).set(User::getUserEmail,user.getUserEmail());
        boolean update = userService.update(userLambdaUpdateWrapper);
        if(update){
            return ResponseResult.success("修改邮箱成功！");
        }

        return ResponseResult.success("修改邮箱失败！");
    }

}
