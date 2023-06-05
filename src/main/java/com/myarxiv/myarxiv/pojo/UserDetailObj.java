package com.myarxiv.myarxiv.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetailObj {
    private Integer userId;

    private String userName;

    private String userEmail;

    private String userAccount;

    private String userPassword;

    private String userCountry;

    private String userOrganization;

    private String careerStatus;

    private Date userCreateTime;

    private String subject;

    private String defaultCategory;

    private String userHomePage;

    private Integer userStatus;
}
