package com.myarxiv.myarxiv.controller;

import com.myarxiv.myarxiv.common.RedisUtils;
import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.common.Tools;
import com.myarxiv.myarxiv.domain.OrganizationMailbox;
import com.myarxiv.myarxiv.mail.SendMail;
import com.myarxiv.myarxiv.service.OrganizationMailboxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private OrganizationMailboxService organizationMailboxService;

    @PostMapping("/registerVerifies")
    public Object registerVerifies(@RequestBody Map<String,String> emailMap){
        String email = emailMap.get("email");
        log.info("email: {}",email);
        String code = Tools.randomCode();
        try {
            SendMail.sendMail(email, Tools.getEmailContent(code,"注册验证码邮件"),"注册验证码");
            redisUtils.set(email,code,5, TimeUnit.MINUTES);
            return ResponseResult.success("我们已经向您所填的邮箱发送了注册验证码，请及时查收！验证码在2分钟内有效！");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseResult.fail("发送注册验证码失败", StatusCode.ERROR.getCode());
        }

    }

    @PostMapping("/addAuthEmail")
    public Object addAuthEmail(@RequestBody OrganizationMailbox organizationMailbox){
        log.info("organizationMailbox",organizationMailbox);
        if (organizationMailbox == null){
            return ResponseResult.fail("参数为null",StatusCode.ERROR.getCode());
        }
        boolean save = organizationMailboxService.save(organizationMailbox);
        if (save){
            return ResponseResult.success("创建成功！");
        }
        return ResponseResult.success("创建失败！");
    }

    @PostMapping("/removeAuthEmail")
    public Object removeAuthEmail(@RequestBody OrganizationMailbox organizationMailbox){
        log.info("organizationMailbox",organizationMailbox);
        if (organizationMailbox == null){
            return ResponseResult.fail("参数为null",StatusCode.ERROR.getCode());
        }
        organizationMailboxService.removeById(organizationMailbox.getOrganizationMailboxId());
        return ResponseResult.success("删除成功！");
    }

    @GetMapping("/getAuthEmail")
    public Object getAuthEmail(){
        return ResponseResult.success(organizationMailboxService.list());
    }

}
