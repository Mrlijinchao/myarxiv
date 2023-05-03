package com.myarxiv.myarxiv.controller;

import com.myarxiv.myarxiv.common.RedisUtils;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.common.Tools;
import com.myarxiv.myarxiv.mail.SendMail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/registerVerifies")
    public Object registerVerifies(String email){
        String code = Tools.randomCode();
        try {
            SendMail.sendMail(email, Tools.getEmailContent(code,"注册验证码邮件"),"注册验证码");
            redisUtils.set(email,code,2, TimeUnit.MINUTES);
            return ResponseResult.success("我们已经向您所填的邮箱发送了注册验证码，请及时查收！验证码在2分钟内有效！");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseResult.fail("发送注册验证码失败", StatusCode.ERROR.getCode());
        }

    }

}
