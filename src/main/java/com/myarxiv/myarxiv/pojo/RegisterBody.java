package com.myarxiv.myarxiv.pojo;

import com.myarxiv.myarxiv.domain.User;
import lombok.Data;

@Data
public class RegisterBody {
    private User user;
    private String verificationCode;
}
