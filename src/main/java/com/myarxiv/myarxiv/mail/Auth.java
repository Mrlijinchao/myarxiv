package com.myarxiv.myarxiv.mail;

import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Auth extends Authenticator {
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("2751488527@qq.com","grexmxsovaqndhbe");
    }
}
